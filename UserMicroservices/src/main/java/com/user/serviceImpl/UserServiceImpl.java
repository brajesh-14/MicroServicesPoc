package com.user.serviceImpl;

import com.user.client.RatingService;
import com.user.dto.RatingsDto;
import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.repository.UserRepo;
import com.user.service.UserService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RatingService ratingService;

    @Override
    public User getUser(String userId) {

       return userRepo.findById(userId)
             .orElseThrow(() -> new ResourceNotFoundException("User with userId " + userId + " does not exist"));
    }

    @Override
    public List<User> getAllUser() {
        log.info("Fetching all the records from database");
        return userRepo.findAll();
    }

    @Override
    @CircuitBreaker(name = "ratingServiceCB", fallbackMethod = "ratingFallback")
    public User getUserWithDetails(String userId) {

        User user = getUser(userId);

        List<RatingsDto> ratingsByUserId = new ArrayList<>();
        try {
            ratingsByUserId = ratingService.getRatingsByUserId(userId);
        }catch (FeignException.NotFound e) {
            log.warn("No ratings found for userId = {}", userId);
        }
        user.setRatings(ratingsByUserId);
        return user;
    }

    public User ratingFallback(String userId, Throwable ex){
        log.error("### USER SERVICE FALLBACK HIT ### userId={}", userId, ex);
        User user = getUser(userId);
        user.setRatings(Collections.emptyList());
        return user;
    }

    @Override
    public List<User> getAllUserWithDetails(){

        List<User> allUsers = getAllUser();
        return allUsers.stream().map(user -> getUserWithDetails(user.getUserId())).toList();
    }

    @Override
    public User saveUser(User user) {
        String id = UUID.randomUUID().toString();
        user.setUserId(id);
        return userRepo.save(user);
    }
}


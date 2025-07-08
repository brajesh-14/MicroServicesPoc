package com.user.serviceImpl;

import com.user.config.HotelService;
import com.user.config.RatingService;
import com.user.dto.RatingsDto;
import com.user.entity.Hotel;
import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.repository.UserRepo;
import com.user.service.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

    @Override
    public User getUser(String userId) {

       return userRepo.findById(userId)
             .orElseThrow(() -> new ResourceNotFoundException("User with userId " + userId + " does not exist"));

    }

    @Override
    public User getUserWithDetails(String userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with userId " + userId + " does not exist"));

        List<RatingsDto> ratingsByUserId = ratingService.getRatingsByUserId(userId);
        logger.info("Ratings: {}", ratingsByUserId);

        List<RatingsDto> enrichedRatings = ratingsByUserId.stream()
                .map(rating -> {
                    Hotel hotel = hotelService.getHotels(rating.getHotelId());
                    rating.setHotel(hotel);
                    return rating;
                })
                .toList();

        user.setRatings(enrichedRatings);
        return user;
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public User saveUser(User user) {
        String id = UUID.randomUUID().toString();
        user.setUserId(id);
        return userRepo.save(user);
    }
}


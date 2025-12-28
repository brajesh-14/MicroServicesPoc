package com.ratings.serviceImpl;

import com.ratings.client.HotelService;
import com.ratings.dto.HotelDTO;
import com.ratings.dto.RatingResponseDTO;
import com.ratings.entity.Ratings;
import com.ratings.exceptions.ResourceNotFoundException;
import com.ratings.repository.RatingRepo;
import com.ratings.service.RatingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingRepo ratingRepo;
    private final HotelService hotelService;

    @Override
    public Ratings saveRatings(Ratings ratings) {
        String rateId = UUID.randomUUID().toString();
        ratings.setRatingId(rateId);
        return ratingRepo.save(ratings);
    }

    public Ratings getRating(String ratingId){
        return ratingRepo.findById(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException("User with userId " + ratingId + " does not exist"));
    }

    @Override
    public List<Ratings> getAllRatings() {
        return ratingRepo.findAll();
    }

    @Override
    @CircuitBreaker(name = "hotelServiceCB", fallbackMethod = "hotelFallback")
    public List<RatingResponseDTO> getRatingByUserId(String userId) {
        List<Ratings> ratings = ratingRepo.findByUserId(userId);
        if (ratings.isEmpty()) {
            throw new ResourceNotFoundException("No ratings found for userId: " + userId);
        }
        return ratings.stream().map(r ->{
            HotelDTO hotel = hotelService.getHotels(r.getHotelId());

            return RatingResponseDTO.builder()
                    .ratingId(r.getRatingId())
                    .userId(r.getUserId())
                    .hotelId(r.getHotelId())
                    .rating(r.getRating())
                    .feedback(r.getFeedback())
                    .hotel(hotel)
                    .build();
        }).toList();
    }

    public List<RatingResponseDTO> hotelFallback(String userId, Throwable ex) {
        log.error("### HOTEL SERVICE DOWN - FALLBACK IN RATING SERVICE ### userId={}", userId, ex);
        List<Ratings> ratings = ratingRepo.findByUserId(userId);
        if (ratings.isEmpty()) {
            throw new ResourceNotFoundException("No ratings found for userId: " + userId);
        }
        return ratings.stream().map(r ->
                RatingResponseDTO.builder()
                    .ratingId(r.getRatingId())
                    .userId(r.getUserId())
                    .hotelId(r.getHotelId())
                    .rating(r.getRating())
                    .feedback(r.getFeedback())
                    .hotel(null)
                    .build()
        ).toList();
    }

    @Override
    public List<Ratings> getRatingByHotelId(String hotelId) {
        List<Ratings> ratings = ratingRepo.findByHotelId(hotelId);
        if (ratings.isEmpty()) {
            throw new ResourceNotFoundException("No ratings found for hotelId: " + hotelId);
        }
        return ratings;
    }
}

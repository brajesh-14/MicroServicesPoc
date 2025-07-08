package com.ratings.serviceImpl;

import com.ratings.entity.Ratings;
import com.ratings.exceptions.ResourceNotFoundException;
import com.ratings.repository.RatingRepo;
import com.ratings.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepo ratingRepo;

    @Override
    public Ratings saveRatings(Ratings ratings) {
        String rateId = UUID.randomUUID().toString();
        ratings.setRatingId(rateId);
        return ratingRepo.save(ratings);
    }

    @Override
    public List<Ratings> getAllRatings() {
        return ratingRepo.findAll();
    }

    @Override
    public List<Ratings> getRatingByUserId(String userId) {
        List<Ratings> ratings = ratingRepo.findByUserId(userId);
        if (ratings.isEmpty()) {
            throw new ResourceNotFoundException("No ratings found for userId: " + userId);
        }
        return ratings;
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

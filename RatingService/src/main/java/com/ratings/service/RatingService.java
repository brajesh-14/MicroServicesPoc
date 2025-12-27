package com.ratings.service;

import com.ratings.dto.RatingResponseDTO;
import com.ratings.entity.Ratings;

import java.util.List;

public interface RatingService {

    // save ratings
    public Ratings saveRatings(Ratings ratings);

    // get one rating by ratingId
    public Ratings getRating(String ratingId);

    // getAll ratings
    public List<Ratings> getAllRatings();

    // get ratings by userId
    public List<RatingResponseDTO> getRatingByUserId(String userId);

    // get ratings by hotelId
    public List<Ratings> getRatingByHotelId(String hotelId);
}

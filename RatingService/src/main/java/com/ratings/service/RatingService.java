package com.ratings.service;

import com.ratings.entity.Ratings;

import java.util.List;

public interface RatingService {

    // save ratings
    public Ratings saveRatings(Ratings ratings);

    // getAll ratings
    public List<Ratings> getAllRatings();

    // get ratings by userId
    public List<Ratings> getRatingByUserId(String userId);

    // get ratings by hotelId
    public List<Ratings> getRatingByHotelId(String hotelId);
}

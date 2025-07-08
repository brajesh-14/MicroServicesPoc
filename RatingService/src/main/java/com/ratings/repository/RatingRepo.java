package com.ratings.repository;

import com.ratings.entity.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepo extends JpaRepository<Ratings, String> {

    public List<Ratings> findByUserId(String userId);

    public List<Ratings> findByHotelId(String hotelId);
}

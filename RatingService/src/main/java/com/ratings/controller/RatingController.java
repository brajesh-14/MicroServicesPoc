package com.ratings.controller;

import com.ratings.entity.Ratings;
import com.ratings.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/save")
    public ResponseEntity<Ratings> saveRatings(@RequestBody Ratings ratings){
        Ratings ratings1 = ratingService.saveRatings(ratings);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratings1);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Ratings>> getAllRatings(){
        List<Ratings> allRatings = ratingService.getAllRatings();
        return ResponseEntity.status(HttpStatus.OK).body(allRatings);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Ratings>> getRatingByUserId(@PathVariable  String userId){
        List<Ratings> ratingByUserId = ratingService.getRatingByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ratingByUserId);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Ratings>> getRatingByHotelId(@PathVariable  String hotelId){
        List<Ratings> ratingByHotelId = ratingService.getRatingByHotelId(hotelId);
        return ResponseEntity.status(HttpStatus.OK).body(ratingByHotelId);
    }
}

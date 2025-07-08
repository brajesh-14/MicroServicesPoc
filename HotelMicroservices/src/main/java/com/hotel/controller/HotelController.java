package com.hotel.controller;

import com.hotel.entity.Hotel;
import com.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/save")
    ResponseEntity<Hotel> saveHotelName(@RequestBody Hotel hotel){
        Hotel hotel1 = hotelService.saveHotelName(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotel1);
    }

    @GetMapping("/getAll")
    ResponseEntity<List<Hotel>> getAllHotels(){
        List<Hotel> allHotels = hotelService.getAllHotels();
        return ResponseEntity.status(HttpStatus.OK).body(allHotels);
    }

    @GetMapping("/{hotelId}")
    ResponseEntity<Hotel> getHotel(@PathVariable String hotelId){
        Hotel hotel = hotelService.getHotel(hotelId);
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }

    @GetMapping("/user/{userId}")
    ResponseEntity<Hotel> getHotelByUserId(@PathVariable String userId){
        Hotel hotelByUserId = hotelService.getHotelByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(hotelByUserId);
    }
}

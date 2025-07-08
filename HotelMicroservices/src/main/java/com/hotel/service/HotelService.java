package com.hotel.service;

import com.hotel.entity.Hotel;

import java.util.List;

public interface HotelService {

    // save hotel name
    Hotel saveHotelName(Hotel hotel);

    // getAll hotels
    List<Hotel> getAllHotels();

    // getHotels by userId
    Hotel getHotelByUserId(String userId);

    // getOneHotel
    Hotel getHotel(String hotelId);
}

package com.hotel.serviceImpl;

import com.hotel.entity.Hotel;
import com.hotel.exception.ResourceNotFoundException;
import com.hotel.repository.HotelRepo;
import com.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepo hotelRepo;

    @Override
    public Hotel saveHotelName(Hotel hotel) {
        String randomId = UUID.randomUUID().toString();
        hotel.setId(randomId);
        return hotelRepo.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }

    @Override
    public Hotel getHotel(String hotelId) {
        return hotelRepo.findById(hotelId).orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id : "+ hotelId));
    }

    @Override
    public Hotel getHotelByUserId(String userId) {
        Hotel hotels = hotelRepo.findByUserId(userId);

        if (hotels == null) {
            throw new ResourceNotFoundException("No hotels found for userId: " + userId);
        }
        return hotels;
    }
}

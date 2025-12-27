package com.ratings.client;

import com.ratings.dto.HotelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-MICROSERVICES")
public interface HotelService {

    @GetMapping("/hotel/{hotelId}")
    HotelDTO getHotels(@PathVariable String hotelId);
}

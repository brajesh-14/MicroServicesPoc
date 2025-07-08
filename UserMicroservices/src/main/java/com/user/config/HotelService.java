package com.user.config;

import com.user.entity.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-MICROSERVICES")
public interface HotelService {

    @GetMapping("/user/{userId}")
    Hotel getHotels(@PathVariable String userId);

}

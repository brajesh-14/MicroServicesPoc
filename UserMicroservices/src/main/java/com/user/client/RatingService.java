package com.user.client;

import com.user.dto.RatingsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name= "RATING-SERVICE")
public interface RatingService {

    @GetMapping("/rating/user/{userId}")
    List<RatingsDto> getRatingsByUserId(@PathVariable String userId);
}


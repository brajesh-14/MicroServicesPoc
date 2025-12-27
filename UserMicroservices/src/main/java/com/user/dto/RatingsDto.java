package com.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingsDto {
    private String ratingId;
    private String userId;
    private String hotelId;
    private String rating;
    private String feedback;
    private HotelDTO hotel;
}

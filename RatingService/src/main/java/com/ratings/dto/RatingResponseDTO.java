package com.ratings.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingResponseDTO {

    private String ratingId;
    private String userId;
    private String hotelId;
    private String rating;
    private String feedback;
    private HotelDTO hotel;
}

package com.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ratings {

    private String ratingId;
    private String userId;
    private String hotelId;
    private String rating;
    private String feedback;

}

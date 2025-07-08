package com.ratings.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ratings {

    @Id
    private String ratingId;
    private String hotelId;
    private String userId;
    private String rating;
    private String feedback;
}

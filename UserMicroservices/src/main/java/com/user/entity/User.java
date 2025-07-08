package com.user.entity;

import com.user.dto.RatingsDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    private String userId;
    private String name;
    private String dept;
    private String gender;
    private String about;

    @Transient
    private List<RatingsDto> ratings = new ArrayList<>();
}

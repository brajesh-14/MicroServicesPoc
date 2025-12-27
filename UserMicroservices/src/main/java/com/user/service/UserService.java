package com.user.service;

import com.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    // get one user
    User getUser(String userId);

    // get User with details
    User getUserWithDetails(String userId);

    // get all user with all detials
    List<User> getAllUserWithDetails();

    // get all users
    List<User> getAllUser();

    // save users
    User saveUser(User user);
}

package com.user.controller;

import com.user.entity.User;
import com.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId){
        User user = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(allUser);
    }

    @GetMapping("/getAllUserDetails")
    public ResponseEntity<List<User>> getAllUserDetails(){
        List<User> allUserWithDetails = userService.getAllUserWithDetails();

        return ResponseEntity.status(HttpStatus.OK).body(allUserWithDetails);
    }

    @GetMapping("/getOneUserDetails/{userId}")
    public ResponseEntity<User> getOneUserDetails(@PathVariable String userId){
        User userWithDetails = userService.getUserWithDetails(userId);

        return ResponseEntity.status(HttpStatus.OK).body(userWithDetails);
    }
}

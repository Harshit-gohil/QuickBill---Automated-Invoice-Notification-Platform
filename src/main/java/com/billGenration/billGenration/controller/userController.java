package com.billGenration.billGenration.controller;

import com.billGenration.billGenration.model.DTO.CreateUserDTO;
import com.billGenration.billGenration.model.users;
import com.billGenration.billGenration.service.UserService;
import com.billGenration.billGenration.service.productService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flipmart")
public class userController {

   private final UserService userService;

    public userController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/createUser")
    public ResponseEntity<users> createUser(@RequestBody CreateUserDTO dto) {

        users savedUser = userService.createUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}

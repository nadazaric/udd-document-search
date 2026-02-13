package com.udd.back.feature_auth.controller;

import com.udd.back.feature_auth.dto.LoginUserDTO;
import com.udd.back.feature_auth.dto.RegisterUserDTO;
import com.udd.back.feature_auth.dto.UserCredentialsDTO;
import com.udd.back.feature_auth.dto.UserDetailsDTO;
import com.udd.back.feature_auth.model.User;
import com.udd.back.feature_auth.service.interf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginUserDTO> login(@RequestBody UserCredentialsDTO dto) {
        return new ResponseEntity<>(userService.login(dto), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDetailsDTO> register(@RequestBody RegisterUserDTO dto) throws ResourceNotFoundException {
        User user = this.userService.register(dto);
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO(user.getName(), user.getUsername());
        return new ResponseEntity<>(userDetailsDTO, HttpStatus.CREATED);
    }

}

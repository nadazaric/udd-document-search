package com.udd.back.feature_auth.controller;

import javax.validation.Valid;
import com.udd.back.feature_auth.dto.LoginUserDTO;
import com.udd.back.feature_auth.dto.RegisterUserDTO;
import com.udd.back.feature_auth.dto.UserCredentialsDTO;
import com.udd.back.feature_auth.dto.UserDetailsDTO;
import com.udd.back.feature_auth.model.User;
import com.udd.back.feature_auth.service.interf.UserService;
import com.udd.back.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public LoginUserDTO login(@RequestBody @Valid UserCredentialsDTO userCredentials) throws Exception {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        User userEntity = userService.getByUsername(userCredentials.getUsername());

        String token = jwtTokenUtil.generateToken(userEntity.getUsername(), sc.getAuthentication().getAuthorities().toArray()[0].toString());
        String refreshToken = jwtTokenUtil.generateRefreshToken(userEntity.getUsername(), sc.getAuthentication().getAuthorities().toArray()[0].toString());

        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setAccessToken(token);
        loginUserDTO.setRefreshToken(refreshToken);

        return loginUserDTO;
    }

    @PostMapping(value = "/refresh")
    public LoginUserDTO refreshToken(@Valid @RequestBody LoginUserDTO dto) {
        if (dto.getRefreshToken() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Refresh token missing");
        }
        if (jwtTokenUtil.isTokenExpired(dto.getRefreshToken()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your refresh token has expired, please log in again");

        String newJwt = jwtTokenUtil.generateToken(jwtTokenUtil.getUsernameFromToken(dto.getRefreshToken()), jwtTokenUtil.getRoleFromToken(dto.getRefreshToken()));
        dto.setAccessToken(newJwt);
        String newRefreshToken = jwtTokenUtil.generateRefreshToken(jwtTokenUtil.getUsernameFromToken(dto.getRefreshToken()), jwtTokenUtil.getRoleFromToken(dto.getRefreshToken()));
        dto.setRefreshToken(newRefreshToken);
        return dto;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDetailsDTO> register(@RequestBody RegisterUserDTO dto) throws ResourceNotFoundException {
        User user = this.userService.register(dto);
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO(user.getName(), user.getUsername());
        return new ResponseEntity<>(userDetailsDTO, HttpStatus.CREATED);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return new ResponseEntity<>("Hello world!", HttpStatus.OK);
    }

}

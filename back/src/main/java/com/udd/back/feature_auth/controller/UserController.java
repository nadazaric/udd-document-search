package com.udd.back.feature_auth.controller;

import javax.validation.Valid;
import com.udd.back.feature_auth.dto.LoginUserDTO;
import com.udd.back.feature_auth.dto.UserCredentialsDTO;
import com.udd.back.feature_auth.model.User;
import com.udd.back.feature_auth.service.interf.IUserService;
import com.udd.back.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    IUserService userService;

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

}

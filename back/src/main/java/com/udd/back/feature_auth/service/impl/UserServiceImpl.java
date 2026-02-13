package com.udd.back.feature_auth.service.impl;

import com.udd.back.feature_auth.dto.LoginUserDTO;
import com.udd.back.feature_auth.dto.RegisterUserDTO;
import com.udd.back.feature_auth.dto.UserCredentialsDTO;
import com.udd.back.feature_auth.model.User;
import com.udd.back.feature_auth.repository.RoleRepository;
import com.udd.back.feature_auth.repository.UserRepository;
import com.udd.back.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.AuthenticationException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements com.udd.back.feature_auth.service.interf.UserService {

    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired RoleRepository roleRepository;
    @Autowired AuthenticationManager authenticationManager;
    @Autowired JwtTokenUtil jwtService;

    @Override
    public User getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, username);
        return user.get();
    }

    @Override
    public User register(RegisterUserDTO dto) throws ResourceNotFoundException {
        boolean alreadyExist = alreadyExistWithUsername(dto.getUsername(), dto.getEmail());
        if (alreadyExist) throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("User with username %s already exist!", dto.getUsername()));
        User user = new User(
                dto.getName(),
                dto.getUsername(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                roleRepository.findByName("USER")
        );
        return userRepository.save(user);
    }

    @Override
    public LoginUserDTO login(UserCredentialsDTO dto) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("User not found: %s", username)));

            String token = jwtService.generateToken();
            return new LoginUserDTO(user.getId(), user.getUsername(), token);
        } catch (AuthenticationException e) {
            System.err.printf("Login failed - Username: %s, Reason: %s%n", dto.getUsername(), e.getClass().getSimpleName());
            throw e;
        }
    }

    public boolean alreadyExistWithUsername(String username, String email) {
        List<User> userEntity = userRepository.findByUsernameOrEmail(username, email);
        return !userEntity.isEmpty();
    }

}
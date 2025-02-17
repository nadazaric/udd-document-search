package com.udd.back.feature_auth.service.impl;

import com.udd.back.feature_auth.dto.RegisterUserDTO;
import com.udd.back.feature_auth.model.User;
import com.udd.back.feature_auth.repository.RoleRepository;
import com.udd.back.feature_auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements com.udd.back.feature_auth.service.interf.UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public User getByUsername(String username) throws Exception {
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

    public boolean alreadyExistWithUsername(String username, String email) {
        List<User> userEntity = userRepository.findByUsernameOrEmail(username, email);
        return !userEntity.isEmpty();
    }

}
package com.udd.back.feature_auth.service.impl;

import com.udd.back.feature_auth.model.User;
import com.udd.back.feature_auth.repository.UserRepository;
import com.udd.back.feature_auth.service.interf.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getByUsername(String username) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, username);
        return user.get();
    }

}

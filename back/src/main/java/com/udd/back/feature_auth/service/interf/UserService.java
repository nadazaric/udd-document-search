package com.udd.back.feature_auth.service.interf;

import com.udd.back.feature_auth.model.User;
import com.udd.back.feature_auth.dto.RegisterUserDTO;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public interface UserService {

    User getByUsername(String username) throws Exception;

    User register(RegisterUserDTO dto) throws ResourceNotFoundException;

}

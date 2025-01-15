package com.udd.back.feature_auth.service.interf;

import com.udd.back.feature_auth.model.User;

public interface IUserService {
    User getByUsername(String username) throws Exception;
}

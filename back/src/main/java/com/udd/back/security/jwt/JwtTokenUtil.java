package com.udd.back.security.jwt;

import com.udd.back.feature_auth.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenUtil {

    String generateToken(String role);

    String getUsernameFromJWT(String token);

    boolean validateToken(String token);

    UserDetails getAuthenticatedUser();

    String getJWTFromRequest(HttpServletRequest request);

}

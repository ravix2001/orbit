package com.ravi.orbit.service;

import com.ravi.orbit.dto.AuthDTO;
import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.enums.ERole;

import java.util.Map;
import java.util.Set;

public interface IAuthService {

    AuthDTO userSignup(UserDTO userDTO);

    AuthDTO sellerSignup(UserDTO userDTO);

    AuthDTO login(String username, String password, Set<ERole> requiredRoles);

    Map<String, String> refreshToken(String authHeader);

    Map<String, String> logout(String authHeader);

}

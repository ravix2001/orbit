package com.ravi.orbit.service;

import com.ravi.orbit.dto.AuthDTO;
import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.enums.ERole;

import java.util.Map;
import java.util.Set;

public interface IAuthService {

    AuthDTO userSignup(UserDTO userDTO);

    AuthDTO sellerSignup(UserDTO userDTO);

    AuthDTO userLogin(AuthDTO authDTO);

    AuthDTO sellerLogin(AuthDTO authDTO);

    AuthDTO adminLogin(AuthDTO authDTO);

    Map<String, String> refreshToken(String authHeader);

    Map<String, String> logout(String authHeader);

}

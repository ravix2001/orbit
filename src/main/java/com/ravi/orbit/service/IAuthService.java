package com.ravi.orbit.service;

import java.util.Map;

public interface IAuthService {

    Map<String, String> refreshToken(String authHeader);

    Map<String, String> logout(String authHeader);

}

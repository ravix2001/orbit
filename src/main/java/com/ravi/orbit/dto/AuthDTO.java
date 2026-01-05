package com.ravi.orbit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDTO {

    // for login request
    private String username;

    private String password;

    // for login and signup response
    private UserDTO userDTO;

    private String accessToken;

    private String refreshToken;

}

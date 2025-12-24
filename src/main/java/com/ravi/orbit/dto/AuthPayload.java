package com.ravi.orbit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthPayload {

    private UserDTO userDTO;

    private SellerDTO sellerDTO;

    private String accessToken;

    private String refreshToken;

}

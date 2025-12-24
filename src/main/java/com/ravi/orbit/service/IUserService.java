package com.ravi.orbit.service;

import com.ravi.orbit.dto.AuthPayload;
import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.entity.User;

import java.util.List;

public interface IUserService {

    UserDTO handleUser(UserDTO userDTO);

    AuthPayload userSignup(UserDTO userDTO);

    AuthPayload userLoginNew(UserDTO userDTO);

    AuthPayload userLoginOld(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserDTOById(Long id);

    UserDTO getUserDTOByUsername(String username);

    UserDTO getUserAuthByUsername(String username);

    void deleteUser(String username);

    void deleteUserHard(Long userId);

    User getUserById(Long id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

}

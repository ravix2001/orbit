package com.ravi.orbit.service;

import com.ravi.orbit.dto.AuthPayload;
import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {

    UserDTO handleUser(UserDTO userDTO);

    AuthPayload userSignup(UserDTO userDTO);

    AuthPayload userLoginNew(UserDTO userDTO);

    AuthPayload userLoginOld(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO, String username);

    Page<UserDTO> getAllUsers(Pageable pageable);

    UserDTO getUserDTOById(Long id);

    UserDTO getUserDTOByUsername(String username);

    UserDTO getUserAuthByUsername(String username);

    void deleteUser(String username);

    void deleteUserHard(Long userId);

    User getUserById(Long id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

}

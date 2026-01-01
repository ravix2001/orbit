package com.ravi.orbit.service;

import com.ravi.orbit.dto.AuthDTO;
import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

    UserDTO handleUser(UserDTO userDTO);

    AuthDTO userSignup(UserDTO userDTO);

    AuthDTO userLogin(UserDTO userDTO);

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

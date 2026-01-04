package com.ravi.orbit.service;

import com.ravi.orbit.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAdminService {

    String createAdmin(Long id);

    String deleteAdmin(Long id);

    Page<UserDTO> getAllUsers(Pageable pageable);

    Page<UserDTO> getAllSellers(Pageable pageable);

    Page<UserDTO> getAllAdmins(Pageable pageable);

}

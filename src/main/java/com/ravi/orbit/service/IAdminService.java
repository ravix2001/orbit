package com.ravi.orbit.service;

import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.enums.ERole;
import com.ravi.orbit.enums.EStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAdminService {

    String createAdmin(Long id);

    String deleteAdmin(Long id);

    Page<UserDTO> getUsersByRoleAndStatus(ERole role, EStatus status, Pageable pageable);

}

package com.management.studentmanagement.service;

import com.management.studentmanagement.dto.UserDto;

public interface UserService {
    void saveUser(UserDto dto);

    boolean ifUserEmailExists(String email);
}

package com.management.studentmanagement.service;

import com.management.studentmanagement.dto.UserDto;
import com.management.studentmanagement.entity.Role;
import com.management.studentmanagement.entity.User;
import com.management.studentmanagement.repository.RoleRepository;
import com.management.studentmanagement.repository.UserRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void saveUser(UserDto dto) {
        User user = new User();
        user.setFname(dto.getFname());
        user.setLname(dto.getLname());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Role role = roleRepository.findByName("USER");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public boolean ifUserEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

}

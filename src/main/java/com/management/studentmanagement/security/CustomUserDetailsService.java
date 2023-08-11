package com.management.studentmanagement.security;

import com.management.studentmanagement.entity.User;
import com.management.studentmanagement.repository.UserRepository;
import com.management.studentmanagement.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user!=null)
        {
            return new org.springframework.security.core.userdetails.User(user.getEmail()
            ,user.getPassword()
            ,user.getRoles().stream()
                    .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
        }
        else {
            throw new UsernameNotFoundException("Invalid Username/Password");
        }
    }
}

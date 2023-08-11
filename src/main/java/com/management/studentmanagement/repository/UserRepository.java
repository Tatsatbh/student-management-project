package com.management.studentmanagement.repository;

import com.management.studentmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String Email);
    boolean existsByEmail(String Email);
}

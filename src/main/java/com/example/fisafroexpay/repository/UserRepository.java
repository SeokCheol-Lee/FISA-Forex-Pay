package com.example.fisafroexpay.repository;

import com.example.fisafroexpay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

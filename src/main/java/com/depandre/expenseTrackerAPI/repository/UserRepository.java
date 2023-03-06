package com.depandre.expenseTrackerAPI.repository;

import com.depandre.expenseTrackerAPI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    public Boolean existsByEmail(String email);

    public Optional<User> findByEmail(String email);
}

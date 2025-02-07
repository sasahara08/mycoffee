package com.example.my_coffee_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_coffee_list.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
}

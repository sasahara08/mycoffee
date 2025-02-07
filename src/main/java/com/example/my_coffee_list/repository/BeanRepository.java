package com.example.my_coffee_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.my_coffee_list.entity.Bean;

@Repository
public interface BeanRepository extends JpaRepository <Bean, Integer> {
  public Bean findByName(String name);
}

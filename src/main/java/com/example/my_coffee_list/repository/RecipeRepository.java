package com.example.my_coffee_list.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.my_coffee_list.entity.Bean;
import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.security.UserDetailsImpl;

@Repository
public interface RecipeRepository extends JpaRepository <Recipe, Integer> {
  public List<Recipe> findByBean(Bean bean);
  public List<Recipe> findByUser(User user);
  public User findByUser(UserDetailsImpl userDetailsImpl);
}

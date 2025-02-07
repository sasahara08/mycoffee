package com.example.my_coffee_list.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_coffee_list.entity.Favorite;
import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;
import java.util.List;


public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
  public Boolean existsByUserAndRecipe(User user, Recipe recipe);
  public Favorite findByUserAndRecipe(User user, Recipe recipe);
  public Optional<Favorite> findByRecipe(Recipe recipe);
  public List<Favorite> findByRecipeId(Integer id);
  public List<Favorite> findByUser(User user);
}

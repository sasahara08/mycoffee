package com.example.my_coffee_list.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_coffee_list.entity.Comment;
import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;

public interface CommentRepository extends JpaRepository <Comment, Integer> {
  public List<Comment> findByUser(User user);
  public List<Comment> findByRecipe(Recipe recipe);
  public List<Comment> findByRecipeId(Integer recipeId);

}

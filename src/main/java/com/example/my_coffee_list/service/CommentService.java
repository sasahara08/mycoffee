package com.example.my_coffee_list.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.my_coffee_list.entity.Comment;
import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.repository.CommentRepository;
import com.example.my_coffee_list.repository.RecipeRepository;
import com.example.my_coffee_list.security.UserDetailsImpl;

@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final RecipeRepository recipeRepository;

  public CommentService(CommentRepository commentRepository, RecipeRepository recipeRepository) {
    this.commentRepository = commentRepository;
    this.recipeRepository = recipeRepository;
  }

  // 特定のレシピのコメントを取得
  public List<Comment> getCommenListforRecipe(Recipe recipe) {
    return commentRepository.findByRecipe(recipe);
  }

  // 特定のコメントを取得して更新
  public void updataComment(Integer commentId, String comment) {
    Comment updataComment = commentRepository.findById(commentId).orElse(null);

    updataComment.setText(comment);
  }

  // コメントを追加
  public void addComment(User user, Integer recipeid, String NewComment, UserDetailsImpl userDetailsImpl) {
    Comment addComment = new Comment();
    Recipe recipe = recipeRepository.findById(recipeid).orElse(null);

    addComment.setUser(user);
    addComment.setRecipe(recipe);
    addComment.setText(NewComment);

    if (!(recipe.getUser().equals(userDetailsImpl.getUser()))) {
      recipe.setView(true);
    }

    recipeRepository.save(recipe);
    commentRepository.save(addComment);
  }

  // 特定のユーザーのコメントを削除(アカウント削除時外部キー制約対策)
  public void deleteUserComment(User user) {
    List<Comment> comments = commentRepository.findByUser(user);
    commentRepository.deleteAll(comments);
  }

  // コメント削除
  public void deleteComment(Integer Id) {
    commentRepository.deleteById(Id);
  }

  // 特定のレシピのコメント削除(レシピ削除時の外部キー制約の為)
  public void deleteCommentForRecipe(Integer recipeId){
    List<Comment> comments = commentRepository.findByRecipeId(recipeId);
    commentRepository.deleteAll(comments);
  }

  // ログインユーザーとコメントをしたユーザーが同じかチェック
  public Boolean checkCommentUser(User user, UserDetailsImpl userDetailsImpl) {
    return user.getId().equals(userDetailsImpl.getUser().getId());
  }
}

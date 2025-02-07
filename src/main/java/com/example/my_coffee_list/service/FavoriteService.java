package com.example.my_coffee_list.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.my_coffee_list.entity.Favorite;
import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.repository.FavoriteRepository;
import com.example.my_coffee_list.repository.RecipeRepository;
import com.example.my_coffee_list.repository.UserRepository;

@Service
public class FavoriteService {
  private final FavoriteRepository favoriteRepository;
  private final UserRepository userRepository;
  private final RecipeRepository recipeRepository;

  public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository,
      RecipeRepository recipeRepository) {
    this.favoriteRepository = favoriteRepository;
    this.userRepository = userRepository;
    this.recipeRepository = recipeRepository;
  }

  // 以下3メソッドでお気に入り情報切り替え(お気に入り登録 <--> お気に入り削除)
  // ①favテーブルにレコードが存在するか確認(特定のユーザーのお気に入り情報)
  public boolean existsByUserAndRecipe(User user, Recipe recipe) {
    return favoriteRepository.existsByUserAndRecipe(user, recipe);
  }

  // ②fav追加
  public void registFav(User user, Recipe recipe) {
    Favorite favorite = new Favorite();
    favorite.setUser(user);
    favorite.setRecipe(recipe);

    favoriteRepository.save(favorite);
  }

  // ③fav削除
  public void removeFav(User user, Recipe recipe) {
    Favorite favorite = favoriteRepository.findByUserAndRecipe(user, recipe);
    favoriteRepository.delete(favorite);
  }

  // ログインユーザーが特定のレシピのお気に入り状況を確認
  public Boolean checkFavforUser(Integer recipeId, Integer userId) {
    User user = userRepository.findById(userId).orElse(null);
    Recipe recipe = recipeRepository.findById(recipeId).orElse(null);

    return favoriteRepository.existsByUserAndRecipe(user, recipe);

  }

  // レシピ毎にお気に入りされているかチェック
  public Favorite searchFavRecipe(Recipe recipe) {
    Favorite searchedFav = favoriteRepository.findByRecipe(recipe).orElse(null);
    return searchedFav;
  }

  // ユーザーがレシピをお気に入りしているかチェック
  public boolean FavStatusForRecipe(Favorite favorite, User user) {
    return favorite != null && favorite.getUser().equals(user);
}


  // ユーザーがお気に入りしているレシピを一覧で取得
  public List<Favorite> selectedFavPage(User user) {
    List<Favorite> favPage = favoriteRepository.findByUser(user);
    return favPage;
  }

  // 特定のユーザーのお気に入り情報を削除(アカウント削除用)
  public void deleteUserFavorite(User user) {
    List<Favorite> favorites = favoriteRepository.findByUser(user);
    favoriteRepository.deleteAll(favorites);
  }

  // 特定のレシピのお気に入り削除(レシピ削除時の外部キー制約の為)
  public void deleteFavoriteForRecipe(Integer recipeId){
    List<Favorite> favorites = favoriteRepository.findByRecipeId(recipeId);
    favoriteRepository.deleteAll(favorites);
  }
}

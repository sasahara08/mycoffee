package com.example.my_coffee_list.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.security.UserDetailsImpl;
import com.example.my_coffee_list.service.FavoriteService;
import com.example.my_coffee_list.service.RecipeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
// @Controller
public class FavoriteController {
  private final FavoriteService favoriteService;
  private final RecipeService recipeService;


  public FavoriteController(FavoriteService favoriteService, RecipeService recipeService) {
    this.favoriteService = favoriteService;
    this.recipeService = recipeService;
  }

  @GetMapping("/Favorite/{recipeId}")
  public ResponseEntity<Map<String, Boolean>> favInfoToView(
    @PathVariable("recipeId") Integer recipeId,
    @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    HttpServletRequest request) {

    User user = userDetailsImpl.getUser();
    Recipe recipe = recipeService.SearchRecipeForId(recipeId);
    Integer userId = userDetailsImpl.getUser().getId();
    

    // お気に入り登録 <--> お気に入り削除
    if(favoriteService.existsByUserAndRecipe(user, recipe)){
      favoriteService.removeFav(user, recipe);
    }else{
      favoriteService.registFav(user, recipe);
    }

    // viewにお気に入り状況を返す(fav:true / notfav:false)
    boolean fovEnable = favoriteService.checkFavforUser(recipeId, userId);
    
    //キーを付けてjson形式に
      return ResponseEntity.ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(Map.of("isFav", fovEnable));
    
  }

    

}

package com.example.my_coffee_list.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.my_coffee_list.entity.Bean;
import com.example.my_coffee_list.entity.Comment;
import com.example.my_coffee_list.entity.Favorite;
import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.security.UserDetailsImpl;
import com.example.my_coffee_list.service.BeanService;
import com.example.my_coffee_list.service.CommentService;
import com.example.my_coffee_list.service.FavoriteService;
import com.example.my_coffee_list.service.RecipeService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class mainController {
  private RecipeService recipeService;
  private BeanService beanService;
  private FavoriteService favoriteService;
  private CommentService commentService;

  public mainController(RecipeService recipeService, BeanService beanService, FavoriteService favoriteService,
      CommentService commentService) {
    this.recipeService = recipeService;
    this.beanService = beanService;
    this.favoriteService = favoriteService;
    this.commentService = commentService;
  }

  // ホーム画面
  @GetMapping("/")
  public String indexPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Favorite favorite) {

    User user = userDetailsImpl.getUser();
    List<Recipe> recipeList = recipeService.recipeByUser(user);

    // view表示用
    for (Recipe recipe : recipeList) {
      User recipeUser = recipe.getUser();
      User favUser = userDetailsImpl.getUser();

      // レシピ作成ユーザーとログインユーザーが同じがチェック
      recipe.setSameUser(recipeService.checkUser(recipeUser, userDetailsImpl));

      // レシピをお気に入りしているユーザーとログインユーザーが同じがチェック
      Favorite searchedFavRecipe = favoriteService.searchFavRecipe(recipe);
      recipe.setFav(favoriteService.FavStatusForRecipe(searchedFavRecipe, favUser));

      // レシピにコメントを付ける
      List<Comment> commentList = commentService.getCommenListforRecipe(recipe);
      List<String> commentTexts = commentList.stream().map(Comment::getText).collect(Collectors.toList());
      recipe.setComment(commentTexts);
    }

    model.addAttribute("user", user);
    model.addAttribute("recipeList", recipeList);

    return "index";
  }

  // ヘッダーの豆検索フォーム→検索結果へ
  @GetMapping("/search")
  public String RecipeSearch(@RequestParam("beanSearch") String beanSearch, Model model,
      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    if (!beanSearch.isEmpty()) {
      Bean bean = beanService.beanSearch(beanSearch);
      List<Recipe> recipeList = recipeService.recipeByBean(bean);
      User user = userDetailsImpl.getUser();

      for (Recipe recipe : recipeList) {
        User recipeUser = recipe.getUser();

        // レシピ作成ユーザーとログインユーザーが同じがチェック
        recipe.setSameUser(recipeService.checkUser(recipeUser, userDetailsImpl));

        User favUser = userDetailsImpl.getUser();
        // レシピをお気に入りしているユーザーとログインユーザーが同じがチェック
        Favorite searchedFavRecipe = favoriteService.searchFavRecipe(recipe);
        recipe.setFav(favoriteService.FavStatusForRecipe(searchedFavRecipe, favUser));

        // レシピにコメントを付ける
        List<Comment> commentList = commentService.getCommenListforRecipe(recipe);
        List<String> commentTexts = commentList.stream().map(Comment::getText).collect(Collectors.toList());
        recipe.setComment(commentTexts);
      }
      
      System.out.println(recipeList);
      model.addAttribute("recipeList", recipeList);
      model.addAttribute("searchName", beanSearch);
      model.addAttribute("user", user);

      return "search";
    } else {
      model.addAttribute("errorMessage", "検索ワードがありません");
      System.out.println("検索ワードがない為ホームに戻ります。");
      return "redirect:/";
    }
  }

  // お気に入りレシピ確認ページへ遷移
  @GetMapping("/Favorite")
  public String favPageView(Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

    List<Favorite> favRecipePage = favoriteService.selectedFavPage(userDetailsImpl.getUser());
    User user = userDetailsImpl.getUser();
    List<Recipe> recipeList = favRecipePage.stream()
        .map(Favorite::getRecipe)
        .collect(Collectors.toList());

    for (Recipe recipe : recipeList) {
      User recipeCreateUser = recipe.getUser();

      // レシピ作成ユーザーとログインユーザーが同じがチェック(表示切替用)
      recipe.setSameUser(recipeService.checkUser(recipeCreateUser, userDetailsImpl));

      // レシピをお気に入りしているユーザーとログインユーザーが同じがチェック(お気に入り表示切替)
      User favUser = userDetailsImpl.getUser();
      Favorite searchedFavRecipe = favoriteService.searchFavRecipe(recipe);
      recipe.setFav(favoriteService.FavStatusForRecipe(searchedFavRecipe, favUser));

      // レシピにコメントを付ける
      List<Comment> commentList = commentService.getCommenListforRecipe(recipe);
      List<String> commentTexts = commentList.stream().map(Comment::getText).collect(Collectors.toList());
      recipe.setComment(commentTexts);
    }

    model.addAttribute("recipeList", recipeList);
    model.addAttribute("user", user);

    return "favorite";
  }

  // マイページに遷移
  @GetMapping("/mypage")
  public String mypage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    User user = userDetailsImpl.getUser();
    model.addAttribute("user", user);

    return "myPage";

  }

}

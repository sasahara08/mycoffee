package com.example.my_coffee_list.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.my_coffee_list.Form.RecipeForm;
import com.example.my_coffee_list.entity.Bean;
import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.repository.RecipeRepository;
import com.example.my_coffee_list.security.UserDetailsImpl;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RecipeService {

  // レシピフォームの値をレシピに登録
  public void saveFormDataToRecipe(Recipe recipe, RecipeForm recipeForm, UserDetailsImpl userDetailsImpl) {

    Bean bean = beanService.beanSearch(recipeForm.getName());

    recipe.setUser(userDetailsImpl.getUser());
    recipe.setBean(bean);
    recipe.setRoast(recipeForm.getRoast());
    recipe.setGrindSize(recipeForm.getGrindSize());
    recipe.setBeansWeight(recipeForm.getBeanWeight());
    recipe.setWaterVolume(recipeForm.getWaterValue());
    recipe.setWaterTemp(recipeForm.getWaterTemp());
    recipe.setSteamingTime(recipeForm.getSteamingTime());
    recipe.setDripper(recipeForm.getDoripper());
    recipe.setFilter(recipeForm.getFilter());
    recipe.setMemo(recipeForm.getMemo());
    recipe.setView(false); // falseで未読無し

    recipeRepository.save(recipe);
  }

  private final RecipeRepository recipeRepository;
  private final BeanService beanService;

  public RecipeService(RecipeRepository recipeRepository, BeanService beanService) {
    this.recipeRepository = recipeRepository;
    this.beanService = beanService;
  }

  // ユーザーごとのレシピを取得
  public List<Recipe> recipeByUser(User user) {
    return recipeRepository.findByUser(user);
  }

  // 豆ごとのレシピのリストを取得
  public List<Recipe> recipeByBean(Bean bean) {
    return recipeRepository.findByBean(bean);
  }

  // Idでレシピを特定して取得
  public Recipe SearchRecipeForId(Integer Id) {
    return recipeRepository.findById(Id).orElse(null);
  }

  // レシピを作成したユーザーとログインしているユーザーが同じかチェック
  public boolean checkUser(User user, UserDetailsImpl userDetailsImpl) {
    return user.getId().equals(userDetailsImpl.getUser().getId());
  }

  // フォームから入力した値を受け取りレシピを登録(新規登録用)
  public void saveRecipe(RecipeForm recipeForm, UserDetailsImpl userDetailsImpl) {

    Recipe recipe = new Recipe();
    saveFormDataToRecipe(recipe, recipeForm, userDetailsImpl);
  }

  // レシピ編集画面へ遷移
  public RecipeForm setRecipeToEditFrom(Integer id, RecipeForm recipeForm) {
    Recipe recipe = recipeRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("検索結果：レシピなし: " + id));

    recipeForm.setName(recipe.getBean().getName());
    recipeForm.setRoast(recipe.getRoast());
    recipeForm.setGrindSize(recipe.getGrindSize());
    recipeForm.setBeanWeight(recipe.getBeansWeight());
    recipeForm.setWaterValue(recipe.getWaterVolume());
    recipeForm.setWaterTemp(recipe.getWaterTemp());
    recipeForm.setSteamingTime(recipe.getSteamingTime());
    recipeForm.setDoripper(recipe.getDripper());
    recipeForm.setFilter(recipe.getFilter());
    recipeForm.setMemo(recipe.getMemo());

    return recipeForm;
  }

  // レシピ編集フォームから入力した値を受け取りレシピを登録(更新用)
  public void updataRecipe(Integer recipeId, RecipeForm recipeForm, UserDetailsImpl userDetailsImpl) {
    Recipe chakeRecipe = recipeRepository.findById(recipeId).orElse(null);

    if (chakeRecipe != null) {
      // 更新
      Recipe recipe = chakeRecipe;
      saveFormDataToRecipe(recipe, recipeForm, userDetailsImpl);
      // 新規作成
    } else {
      Recipe recipe = new Recipe();
      saveFormDataToRecipe(recipe, recipeForm, userDetailsImpl);
    }
  }

  // レシピを削除
  public void deleteRecipe(Integer id) {
    Recipe recipe = recipeRepository.findById(id).orElse(null);
    recipeRepository.delete(recipe);
  }

  // 特定のユーザーのレシピを全削除(アカウント削除用)
  public void deleteUsersRecipe(User user) {
    List<Recipe> recipes = recipeRepository.findByUser(user);
    recipeRepository.deleteAll(recipes);
  }

  // レシピの詳細を開いたときにviewの状態をfalseにする(既読検出)
  public void recipeView(Recipe recipe, UserDetailsImpl userDetailsImpl) {

    if (recipe.getUser().equals(userDetailsImpl.getUser())) {
      System.out.println("a");
      recipe.setView(false);
      recipeRepository.save(recipe);
    }
  }

}

package com.example.my_coffee_list.Form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RecipeForm {
  
  @NotBlank(message = "豆の種類を入力してください")
  private String name;
  
  @Length(max= 30,  message = "30文字以下で入力ください")
  private String roast;

  @Length(max= 30,  message = "30文字以下で入力ください")
  private String grindSize;

  @Range(min=0, max=100, message = "100g以下で入力してください")
  private Integer beanWeight;

  @Range(min=0, max=1500, message = "1,500ml以下で入力してください")
  private Integer waterValue;

  @Range(min=50, max=110, message = "50℃～110℃の範囲で入力してください")
  private Integer waterTemp;

  @Range(min=0, max=200, message = "蒸らし時間は0秒~200秒で設定してください")
  private Integer steamingTime;

  @Length(max= 30,  message = "30文字以内で入力ください")
  private String doripper;

  @Length(max= 30,  message = "30文字以内で入力ください")
  private String filter;

  @Length(max= 300,  message = "300文字以内で入力ください")
  private String memo;

}

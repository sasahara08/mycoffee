package com.example.my_coffee_list.Form;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupForm {
  
  @NotBlank(message = "ユーザー名を入力してください")
  private String name;

  @NotBlank(message = "メールアドレスを入力してください")
  @Email(message = "メールアドレスは正しい形式で入力ください。")
  private String email;

  @NotBlank(message = "パスワードを入力してください")
  @Length(min = 8, message = "パスワードは８文字以上入力してください")
  @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "半角英数字のみ入力できます")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).+$", message = "大文字と小文字を１文字以上含めてください")
  private String password;

  @NotBlank(message = "パスワード(確認用)を入力してください")
  private String passwordConfirmation;

  private MultipartFile img;
}
package com.example.my_coffee_list.Form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdataPwForm {
    
    private String beforePw;

    @NotBlank(message = "更新するパスワードを入力してください")
    @Length(min = 8, message = "パスワードは８文字以上入力してください")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "半角英数字のみ入力できます")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).+$", message = "大文字と小文字を１文字以上含めてください")
    private String afterPw;

    @NotBlank(message = "更新するパスワード(確認用)を入力してください")
    private String afterPwConfirmation;
}


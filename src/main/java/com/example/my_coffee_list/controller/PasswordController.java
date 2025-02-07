package com.example.my_coffee_list.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.my_coffee_list.Form.UpdataPwForm;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.security.UserDetailsImpl;
import com.example.my_coffee_list.service.PasswordService;
import com.example.my_coffee_list.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PasswordController {
    private final UserService userService;
    private final PasswordService passwordService;
    private final PasswordEncoder passwordEncoder;

    public PasswordController(UserService userService, PasswordService passwordService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordService = passwordService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/resetPw")
    public String resetPwPage() {
        return "resetPw";
    }

    // パスワード再設定(仮パスワード送信)
    @PostMapping("/resetPw")
    public String forgetPwUsersNewPw(@RequestParam("email") String email,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        if (userService.selectUserForEmail(email) != null) {
            String name = userService.selectUserForEmail(email).getName();
            String newPw = passwordService.randomPw(8);
            passwordService.sendNewPw(newPw, email, name);
            passwordService.registPw(newPw, email);
            redirectAttributes.addFlashAttribute("message", "仮パスワードをメールにて送信しました。");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("message", "メールアドレスが存在しません。");
            String resUrl = request.getHeader("Referer");
            return "redirect:" + resUrl;
        }
    }

    // パスワード変更(マイページからパスワード再設定画面へ遷移)
    @GetMapping("/updataPw")
    public String updataPw(Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        if (!model.containsAttribute("updataPwForm")) {
            model.addAttribute("updataPwForm", new UpdataPwForm());
        }

        User user = userDetailsImpl.getUser();
        System.out.println(user);
        model.addAttribute("user", user);

        return "updataPw";
    }

    // パスワード更新
    @PostMapping("/updataPw")
    public String updataPw(@ModelAttribute @Validated UpdataPwForm updataPwForm,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (!passwordEncoder.matches(updataPwForm.getBeforePw(), userDetailsImpl.getPassword())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "beforePw", "パスワードが違います");
            bindingResult.addError(fieldError);
        }

        if (!passwordService.isSamePassword(updataPwForm.getAfterPw(), updataPwForm.getAfterPwConfirmation())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "afterPwConfirmation",
                    "新規パスワードと確認用パスワードが違います");
            bindingResult.addError(fieldError);
        }

        if (bindingResult.hasErrors()) {
            User user = userDetailsImpl.getUser();
            model.addAttribute("user", user);
            model.addAttribute("updataPwForm", updataPwForm);
            return "updataPw";
        }

        User user = userDetailsImpl.getUser();
        String password = updataPwForm.getAfterPw();
        passwordService.registPw(password, user);
        redirectAttributes.addFlashAttribute("message", "パスワードを変更しました");

        return "redirect:/";
    }

}

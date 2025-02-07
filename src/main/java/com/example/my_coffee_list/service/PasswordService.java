package com.example.my_coffee_list.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.repository.UserRepository;

@Service
public class PasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public PasswordService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    // 確認用のパスワードが１回目入力のパスワードと一致しているか
    public boolean isSamePassword(String password, String passwordConfimation) {
        return password.equals(passwordConfimation);
    }

    // ランダムパスワード生成
    public String randomPw(int count) {
        while (true) {
            String result = "";
            String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

            java.util.Random ran = new java.util.Random();

            for (int i = 0; i < count; i++) {
                int pos = ran.nextInt(base.length());
                result += base.charAt(pos);
            }

            if (!result.matches(".*[a-z].*") || !result.matches(".*[A-Z].*")) {
                continue;
            }

            return result;
        }
    }

    // 新しいパスワードを送信
    public void sendNewPw(String password, String email, String name) {

        String senderAddress = "sasahara.yukio.08@gmail.com";
        String recipientAddress = email;
        String subject = "仮パスワード送信のご連絡\n\n";
        String message = "仮パスワードは以下の通りです。\n\n仮パスワードでログイン後、マイメニューにてパスワードの再設定を行ってください。\n";
        String sendPw = "仮パスワード: " + password + "\n\nBrewShare";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderAddress);
        mailMessage.setTo(recipientAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(name + "様\n" + message + "\n" + sendPw);
        javaMailSender.send(mailMessage);

    }

    // 新しいパスワードをDBに保存(メールアドレスが分からないユーザー用)
    public void registPw(String password, String email) {
        User user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    // 新しいパスワードをDBに保存(pwを再設定したいユーザー用)
    public void registPw(String password, User user) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}

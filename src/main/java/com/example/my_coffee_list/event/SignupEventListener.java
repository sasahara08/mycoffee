package com.example.my_coffee_list.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.service.VerificationTokenService;



@Component
public class SignupEventListener {
  private final VerificationTokenService verificationTokenService;
  private final JavaMailSender javaMailSender;

  public SignupEventListener(VerificationTokenService verificationTokenService, JavaMailSender javaMailSender) {
    this.verificationTokenService = verificationTokenService;
    this.javaMailSender = javaMailSender;
  }

  @EventListener
  private void onSignupEvent(SignupEvent signupEvent) {  //引数に指定したクラスから通知を受けたときに実行される処理
    User user = signupEvent.getUser();
    String token = UUID.randomUUID().toString();
    verificationTokenService.userAuth(user, token);

    String senderAddress = "sasahara.yukio.08@gmail.com";
    String recipientAddress = user.getEmail();
    String subject = "メール認証";
    String confirmationUrl = signupEvent.getRequestUrl() + "/verify?token=" + token;
    String message = "以下のリンクをクリックして会員登録を完了してください。";

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom(senderAddress);
    mailMessage.setTo(recipientAddress);
    mailMessage.setSubject(subject);
    mailMessage.setText(message + "\n" + confirmationUrl);
    javaMailSender.send(mailMessage);

  }
}

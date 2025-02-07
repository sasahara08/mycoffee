package com.example.my_coffee_list.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.entity.VerificationToken;
import com.example.my_coffee_list.repository.VerificationTokenRepository;

@Service
public class VerificationTokenService {
  private final VerificationTokenRepository verificationTokenRepository;

  public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
    this.verificationTokenRepository = verificationTokenRepository;
  }

  //トークンをユーザーごとに発行
  @Transactional
  public void userAuth(User user, String token){

    VerificationToken verificationToken = new VerificationToken();

    verificationToken.setUser(user);
    verificationToken.setToken(token);

    verificationTokenRepository.save(verificationToken);
  }

  // トークンの文字列で検索した結果を返す
  public VerificationToken getVerificationToken(String token) {
    return verificationTokenRepository.findByToken(token);
  }

  // 特定のユーザーのレコードを削除
  public void deleteUserToken(User user){
    VerificationToken verificationToken = verificationTokenRepository.findByUser(user);
    verificationTokenRepository.delete(verificationToken);
  }
  
}

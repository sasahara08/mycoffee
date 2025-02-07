package com.example.my_coffee_list.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            System.out.println("ユーザー情報取得成功");
            User user = userRepository.findByEmail(email);
            if(user == null){
                throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
            }
            System.out.println("ユーザー情報取得成功");
            System.out.println(user);
            return new UserDetailsImpl(user);
        } catch (Exception e) {
            System.out.println("ユーザー情報取得失敗");
            throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
        }
    }

}

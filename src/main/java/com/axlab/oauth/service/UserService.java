package com.axlab.oauth.service;

import com.axlab.oauth.controller.UserController;
import com.axlab.oauth.repository.UserRepository;
import com.axlab.oauth.modal.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public User create(UserController.EmailPasswordDto emailPasswordDto){
        return userRepository.save(new User(emailPasswordDto.email(), passwordEncoder.encode(emailPasswordDto.password())));
    }
}

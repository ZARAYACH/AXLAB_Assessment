package com.axlab.oauth;

import com.axlab.oauth.UserController.EmailPasswordDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLInsert;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public User create(EmailPasswordDto emailPasswordDto){
        return userRepository.save(new User(emailPasswordDto.email(), passwordEncoder.encode(emailPasswordDto.password())));
    }
}

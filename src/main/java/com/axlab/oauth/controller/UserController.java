package com.axlab.oauth.controller;

import com.axlab.oauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public record EmailPasswordDto(String email,
                            String password) {
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody EmailPasswordDto emailPasswordDto) {
        userService.create(emailPasswordDto);
    }

}

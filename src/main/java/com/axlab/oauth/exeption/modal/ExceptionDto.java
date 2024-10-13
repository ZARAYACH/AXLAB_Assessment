package com.axlab.oauth.exeption.modal;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class ExceptionDto {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String message;
    private final HttpStatus status;
    private final String errorId = RandomStringUtils.randomAlphanumeric(8);

}

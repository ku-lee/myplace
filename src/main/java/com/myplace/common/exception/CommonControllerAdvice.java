package com.myplace.common.exception;

import com.myplace.member.exception.CryptException;
import com.myplace.member.exception.InvalidPasswordException;
import com.myplace.member.exception.MemberIdDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CommonControllerAdvice {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> hanlder(HttpMessageNotReadableException e){
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("message", "invalid json");

        return new ResponseEntity<>(resBody, HttpStatus.BAD_REQUEST);
    }
}

package com.myplace.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handler(IllegalArgumentException e){
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("message", e.getMessage());

        return new ResponseEntity<>(resBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CryptException.class)
    public ResponseEntity<Map<String, Object>> handler(CryptException e){
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("message", "CryptException Occured");

        return new ResponseEntity<>(resBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MemberIdDuplicateException.class)
    public ResponseEntity<Map<String, Object>> hanlder(MemberIdDuplicateException e){
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("message", "Member Id is duplicated");

        return new ResponseEntity<>(resBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistingMemberException.class)
    public ResponseEntity<Map<String, Object>> hanlder(NotExistingMemberException e){
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("message", "Not exist member");

        return new ResponseEntity<>(resBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, Object>> hanlder(InvalidPasswordException e){
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("message", "Password is invalid");

        return new ResponseEntity<>(resBody, HttpStatus.UNAUTHORIZED);
    }
}

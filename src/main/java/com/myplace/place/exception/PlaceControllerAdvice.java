package com.myplace.place.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class PlaceControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handler(IllegalArgumentException e){
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("message", e.getMessage());

        return new ResponseEntity<>(resBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchPlaceException.class)
    public ResponseEntity<Map<String, Object>> handler(NoSuchPlaceException e){
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("message", "No such Place");

        return new ResponseEntity<>(resBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlaceAPIException.class)
    public ResponseEntity<Map<String, Object>> hanlder(PlaceAPIException e){
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("message", "Place API Exception occured");

        return new ResponseEntity<>(resBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

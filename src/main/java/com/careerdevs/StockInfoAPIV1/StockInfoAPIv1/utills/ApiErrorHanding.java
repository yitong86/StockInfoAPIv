package com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.utills;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiErrorHanding {
    public static ResponseEntity<?> genericApiError(Exception e){
        System.out.println(e.getMessage());
        System.out.println( e.getClass());
        return ResponseEntity.internalServerError().body(e.getMessage());

    }
    public static ResponseEntity<?> customApiError(String message, HttpStatus status){
        return ResponseEntity.status(status).body(message);
    }
}

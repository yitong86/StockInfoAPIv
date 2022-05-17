package com.careerdevs.StockInfoAPIV1.StockInfoAPIv1.controllers;


import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RequestMapping("/api/overview")
public class StockController {
    @Autowired
    private Environment env;

    @GetMapping("/apikey")
    public ResponseEntity<?> getKey(){
    return ResponseEntity.ok(env.getProperty("AV_API_KEY"));
}

}
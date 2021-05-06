package com.slingshot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TrieApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrieApplication.class, args);
    }
}
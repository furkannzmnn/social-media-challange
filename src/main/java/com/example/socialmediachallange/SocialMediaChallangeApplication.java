package com.example.socialmediachallange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SocialMediaChallangeApplication {

    public static void main(String[] args) {
        String  prefix = "";
        for (int i = 1; i <= 6; i++) {
            if (i == 1) {
                System.out.println("*");
            } else {
                prefix += "**";
                System.out.println(prefix);
            }
        }
    }

}

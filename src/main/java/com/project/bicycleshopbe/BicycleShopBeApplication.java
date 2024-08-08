package com.project.bicycleshopbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BicycleShopBeApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BicycleShopBeApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BicycleShopBeApplication.class);
    }
}

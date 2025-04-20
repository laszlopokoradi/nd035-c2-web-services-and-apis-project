package com.udacity.boogle.maps;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.discovery.*;


@SpringBootApplication
@EnableDiscoveryClient
public class BoogleMapsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoogleMapsApplication.class, args);
    }

}

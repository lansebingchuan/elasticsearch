package com.zht;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OracleApp {

    public static void main(String[] args) {
        SpringApplication.run(OracleApp.class, args);
        System.out.println("启动了");
    }


}

package com.example.CricketScoreBoard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CricScoreBoard {
    public static void main(String[] args) {
        SpringApplication.run(CricScoreBoard.class, args);
        DriverClass driverClass = new DriverClass();
    }

}

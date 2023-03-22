package com.jake.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) // DB 빼고 실행
@SpringBootApplication
public class NewSnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewSnsApplication.class, args);
    }

}

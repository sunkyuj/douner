package com.sunkyuj.douner;

import com.sun.net.httpserver.HttpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.net.InetSocketAddress;

@SpringBootApplication
@PropertySource("classpath:/application.properties")
public class DounerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DounerApplication.class, args);
    }

}

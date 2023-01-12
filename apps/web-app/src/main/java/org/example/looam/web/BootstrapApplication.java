package org.example.looam.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BootstrapApplication {
  public static void main(final String[] args) {
    SpringApplication.run(BootstrapApplication.class, args);
  }
}

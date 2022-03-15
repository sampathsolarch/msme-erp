package com.tmlab.erp;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;


import java.io.IOException;

@SpringBootApplication
@MapperScan("com.tmlab.erp.datasource.mappers")
@ServletComponentScan
@EnableScheduling
public class SpringbootErpApplication{
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootErpApplication.class, args);
        Environment environment = context.getBean(Environment.class);       
        System.out.println("To start the UI service, execute command: yarn/npm run serve");
    }
}

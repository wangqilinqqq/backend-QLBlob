package org.qilin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.qilin.mapper")
public class QiLinBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(QiLinBlogApplication.class, args);
    }
}

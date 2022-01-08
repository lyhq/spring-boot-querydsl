package com.lyhq.querydsl.chapter05;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class Chapter05Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter05Application.class, args);
    }

//    @Bean
//    public JPAQueryFactory queryFactory(EntityManager entityManager) {
//        return new JPAQueryFactory(entityManager);
//    }

}

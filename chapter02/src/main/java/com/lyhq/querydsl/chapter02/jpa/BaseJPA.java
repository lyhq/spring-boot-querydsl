package com.lyhq.querydsl.chapter02.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 核心JPA
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/2
 * Time：18:23
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
// 告诉JPA不要创建该接口的bean对象，这样spring容器中就不会有BaseJPA接口的bean对象
@NoRepositoryBean
public interface BaseJPA<T>
        extends JpaRepository<T, Long>,
        JpaSpecificationExecutor<T>,
        QuerydslPredicateExecutor<T> {
}
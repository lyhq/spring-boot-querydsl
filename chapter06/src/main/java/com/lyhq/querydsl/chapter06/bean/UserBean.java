package com.lyhq.querydsl.chapter06.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/12
 * Time：10:58
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@Entity
@Table(name = "users")
@Data
public class UserBean {
    @Id
    @GeneratedValue
    @Column(name = "u_id")
    private Long id;
    @Column(name = "u_username")
    private String name;
    @Column(name = "u_age")
    private int age;
    @Column(name = "u_score")
    private double socre;
}
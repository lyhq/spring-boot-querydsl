package com.lyhq.querydsl.chapter02.bean;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/2
 * Time：18:31
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@Data
@Entity
@Table(name = "t_user")
public class UserBean implements Serializable
{
    @Id
    @Column(name = "t_id")
    @GeneratedValue
    private Long id;
    @Column(name = "t_name")
    private String name;
    @Column(name = "t_age")
    private int age;
    @Column(name = "t_address")
    private String address;
    @Column(name = "t_pwd")
    private String pwd;
}
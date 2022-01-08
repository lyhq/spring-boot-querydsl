package com.lyhq.querydsl.chapter05.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/9
 * Time：15:04
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@Entity
@Table(name = "good_types")
@Data
public class GoodTypeBean
        implements Serializable {
    //主键
    @Id
    @GeneratedValue
    @Column(name = "tgt_id")
    private Long id;
    //类型名称
    @Column(name = "tgt_name")
    private String name;
    //是否显示
    @Column(name = "tgt_is_show")
    private int isShow;
    //排序
    @Column(name = "tgt_order")
    private int order;
}

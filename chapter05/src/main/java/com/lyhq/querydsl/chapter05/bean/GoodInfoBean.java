package com.lyhq.querydsl.chapter05.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/9
 * Time：15:08
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@Entity
@Table(name = "good_infos")
@Data
public class GoodInfoBean
        implements Serializable {
    //主键
    @Id
    @GeneratedValue
    @Column(name = "tg_id")
    private Long id;
    //商品标题
    @Column(name = "tg_title")
    private String title;
    //商品价格
    @Column(name = "tg_price")
    private double price;
    //商品单位
    @Column(name = "tg_unit")
    private String unit;
    //商品排序
    @Column(name = "tg_order")
    private int order;
    //类型外键
    @Column(name = "tg_type_id")
    private Long typeId;
}

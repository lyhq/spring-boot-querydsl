package com.lyhq.querydsl.chapter05.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品dto
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/10
 * Time：22:39
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@Data
public class GoodDTO
        implements Serializable {
    //主键
    private Long id;
    //标题
    private String title;
    //单位
    private String unit;
    //价格
    private double price;
    //类型名称
    private String typeName;
    //类型编号
    private Long typeId;
}
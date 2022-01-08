package com.lyhq.querydsl.chapter06.controller;

import com.lyhq.querydsl.chapter06.bean.QUserBean;
import com.lyhq.querydsl.chapter06.bean.UserBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/12
 * Time：10:59
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@RestController
public class UserController {
    //实体管理对象
    @Autowired
    private EntityManager entityManager;
    //queryDSL,JPA查询工厂
    private JPAQueryFactory queryFactory;

    //实例化查询工厂
    @PostConstruct
    public void init() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * count聚合函数
     *
     * @return
     */
    @RequestMapping(value = "/countExample")
    public long countExample() {
        //用户查询实体
        QUserBean qUserBean = QUserBean.userBean;
        return queryFactory
                .select(qUserBean.id.count())//根据主键查询总条数
                .from(qUserBean)
                .fetchOne();//返回总条数
    }

    /**
     * sum聚合函数
     *
     * @return
     */
    @RequestMapping(value = "/sumExample")
    public double sumExample() {
        //用户查询实体
        QUserBean qUserBean = QUserBean.userBean;
        return queryFactory
                .select(qUserBean.socre.sum())//查询积分总数
                .from(qUserBean)
                .fetchOne();//返回积分总数
    }


    /**
     * avg聚合函数
     *
     * @return
     */
    @RequestMapping(value = "/avgExample")
    public double avgExample() {
        //用户查询实体
        QUserBean qUserBean = QUserBean.userBean;
        return queryFactory
                .select(qUserBean.socre.avg())//查询积分平均值
                .from(qUserBean)
                .fetchOne();//返回平均值
    }

    /**
     * max聚合函数
     *
     * @return
     */
    @RequestMapping(value = "/maxExample")
    public double maxExample() {
        //用户查询实体
        QUserBean qUserBean = QUserBean.userBean;
        return queryFactory
                .select(qUserBean.socre.max())//查询最大积分
                .from(qUserBean)
                .fetchOne();//返回最大积分
    }

    /**
     * group by & having聚合函数
     *
     * @return
     */
    @RequestMapping(value = "/groupByExample")
    public List<UserBean> groupByExample() {
        //用户查询实体
        QUserBean qUserBean = QUserBean.userBean;
        return queryFactory
                .select(qUserBean)
                .from(qUserBean)
                .groupBy(qUserBean.socre)//根据积分分组
                .having(qUserBean.age.gt(22))//并且年龄大于22岁
                .fetch();//返回用户列表
    }
}
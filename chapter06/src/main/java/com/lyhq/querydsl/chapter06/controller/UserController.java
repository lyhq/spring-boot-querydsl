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
    public Long countExample() {
        //用户查询实体
        QUserBean qUserBean = QUserBean.userBean;
        return queryFactory
                //根据主键查询总条数
                .select(qUserBean.id.count())
                .from(qUserBean)
                //返回总条数
                .fetchOne();
    }

    /**
     * sum聚合函数
     *
     * @return
     */
    @RequestMapping(value = "/sumExample")
    public Double sumExample() {
        //用户查询实体
        QUserBean qUserBean = QUserBean.userBean;
        return queryFactory
                //查询积分总数
                .select(qUserBean.socre.sum())
                .from(qUserBean)
                //返回积分总数
                .fetchOne();
    }


    /**
     * avg聚合函数
     *
     * @return
     */
    @RequestMapping(value = "/avgExample")
    public Double avgExample() {
        //用户查询实体
        QUserBean qUserBean = QUserBean.userBean;
        return queryFactory
                //查询积分平均值
                .select(qUserBean.socre.avg())
                .from(qUserBean)
                //返回平均值
                .fetchOne();
    }

    /**
     * max聚合函数
     *
     * @return
     */
    @RequestMapping(value = "/maxExample")
    public Double maxExample() {
        //用户查询实体
        QUserBean qUserBean = QUserBean.userBean;
        return queryFactory
                //查询最大积分
                .select(qUserBean.socre.max())
                .from(qUserBean)
                //返回最大积分
                .fetchOne();
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
                //根据积分分组
                .groupBy(qUserBean.socre)
                //并且年龄大于22岁
                .having(qUserBean.age.gt(22))
                //返回用户列表
                .fetch();
    }
}
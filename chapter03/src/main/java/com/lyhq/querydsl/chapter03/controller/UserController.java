package com.lyhq.querydsl.chapter03.controller;

import com.lyhq.querydsl.chapter03.bean.UserBean;
import com.lyhq.querydsl.chapter03.jpa.UserJPA;
import com.lyhq.querydsl.chapter04.bean.QUserBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/2
 * Time：18:38
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserJPA userJPA;

    //----以下是新添加内容

    //实体管理者
    @Autowired
    private EntityManager entityManager;

    //JPA查询工厂
    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
        System.out.println("init JPAQueryFactory successfully");
    }

    /**
     * 使用JPA更新会员信息
     *
     * @param userBean
     */
    @RequestMapping(value = "/updateWithJpa")
    public String updateWithJpa(UserBean userBean) {
        // 保存会员信息相当于Hibernate内的SaveOrUpdate
        userJPA.save(userBean);
        return "SUCCESS";
    }

    /**
     * 使用QueryDsl更新实体
     *
     * @param userBean
     */
    @Transactional
    @RequestMapping(value = "/updateWithQueryDsl")
    public String updateWithQueryDsl(UserBean userBean) {

        // querydsl 查询实体
        QUserBean qUserBean = QUserBean.userBean;

        queryFactory
                //更新对象
                .update(qUserBean)
                // 更新字段列表
                .set(qUserBean.name, userBean.getName())
                .set(qUserBean.address, userBean.getAddress())
                .set(qUserBean.age, userBean.getAge())
                .set(qUserBean.pwd, userBean.getPwd())
                // 更新条件
                .where(qUserBean.id.eq(userBean.getId()))
                // 执行更新
                .execute();
        return "SUCCESS";
    }

    /**
     * 使用Jpa删除会员信息
     *
     * @param userBean
     */
    @RequestMapping(value = "/deleteWithJpa")
    public String deleteWithJpa(UserBean userBean) {
        //执行删除指定主键的值
        userJPA.delete(userBean);
        return "SUCCESS";
    }

    /**
     * 使用QueryDsl删除会员信息
     *
     * @param userBean
     */
    @RequestMapping(value = "/deleteWithQueryDsl")
    @Transactional
    public String deleteWithQueryDsl(UserBean userBean) {
        //querydsl查询实体
        QUserBean qUserBean = QUserBean.userBean;

        queryFactory
                //删除对象
                .delete(qUserBean)
                //删除条件
                .where(qUserBean.id.eq(userBean.getId()))
                //执行删除
                .execute();
        return "SUCCESS";
    }

    @RequestMapping(value = "/deleteWithQueryDsl2")
    @Transactional
    public String deleteWithQueryDsl2(UserBean userBean) {
        //querydsl查询实体
        QUserBean qUserBean = QUserBean.userBean;

        queryFactory
                //删除对象
                .delete(qUserBean)
                //删除条件
                .where(
                        qUserBean.name.eq(userBean.getName())
                                .and(qUserBean.age.gt(20))
                )
                //执行删除
                .execute();
        return "SUCCESS";
    }
}
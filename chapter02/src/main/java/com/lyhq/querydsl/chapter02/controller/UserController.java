package com.lyhq.querydsl.chapter02.controller;

import com.lyhq.querydsl.chapter02.bean.QUserBean;
import com.lyhq.querydsl.chapter02.bean.UserBean;
import com.lyhq.querydsl.chapter02.jpa.UserJPA;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

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
     * 查询全部数据并根据id倒序
     *
     * @return
     */
    @RequestMapping(value = "/queryAll")
    public List<UserBean> queryAll() {
        //使用querydsl查询
        QUserBean qUserBean = QUserBean.userBean;
        //查询并返回结果集
        return queryFactory
                .selectFrom(qUserBean)//查询源
                .orderBy(qUserBean.id.desc())//根据id倒序
                .fetch();//执行查询并获取结果集
    }

    /**
     * 分页查询全部数据并根据id倒序
     *
     * @return
     */
    @RequestMapping(value = "/queryAll2")
    public Page<UserBean> queryAll2() {
        Page<UserBean> userBeanPage = userJPA.findAll(QUserBean.userBean.isNotNull(), PageRequest.of(1, 1));
        return userBeanPage;
    }

    /**
     * 查询详情 - 完全QueryDSL风格
     *
     * @param id 主键编号
     * @return
     */
    @RequestMapping(value = "/detail/{id}")
    public UserBean detail(@PathVariable("id") Long id) {
        //使用querydsl查询
        QUserBean qUserBean = QUserBean.userBean;
        //查询并返回结果集
        return queryFactory
                .selectFrom(qUserBean)//查询源
                .where(qUserBean.id.eq(id))//指定查询具体id的数据
                .fetchOne();//执行查询并返回单个结果
    }

    /**
     * SpringDataJPA & QueryDSL实现单数据查询
     * SpringDataJPA整合QueryDSL风格
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail_2/{id}")
    public UserBean detail_2(@PathVariable("id") Long id) {
        //使用querydsl查询
        QUserBean qUserBean = QUserBean.userBean;
        //查询并返回指定id的单条数据
        return userJPA.findOne(qUserBean.id.eq(id)).orElse(null);
    }

    /**
     * 根据名称模糊查询
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/likeQueryWithName")
    public List<UserBean> likeQueryWithName(String name) {
        //使用querydsl查询
        QUserBean qUserBean = QUserBean.userBean;

        return queryFactory
                .selectFrom(qUserBean)//查询源
                .where(qUserBean.name.like(name))//根据name模糊查询
                .fetch();//执行查询并返回结果集
    }
}
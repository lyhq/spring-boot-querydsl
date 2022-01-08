package com.lyhq.querydsl.chapter07.controller;

import com.lyhq.querydsl.chapter07.bean.GoodInfoBean;
import com.lyhq.querydsl.chapter07.bean.QGoodInfoBean;
import com.lyhq.querydsl.chapter07.bean.QGoodTypeBean;
import com.querydsl.jpa.JPAExpressions;
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
 * Date：2017/7/14
 * Time：9:30
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@RestController
public class GoodController {
    //实体管理对象
    @Autowired
    private EntityManager entityManager;

    //jpa查询工厂对象
    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void init() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * 子查询 模糊查询
     *
     * @return
     */
    @RequestMapping(value = "/childLikeSelect")
    public List<GoodInfoBean> childLikeSelect() {
        //商品基本信息查询实体
        QGoodInfoBean qGoodInfoBean = QGoodInfoBean.goodInfoBean;
        //商品类型查询实体
        QGoodTypeBean qGoodTypeBean = QGoodTypeBean.goodTypeBean;

        return queryFactory
                //查询商品基本信息表
                .selectFrom(qGoodInfoBean)
                .where(
                        //查询类型名称包含“蔬菜”
                        qGoodInfoBean.typeId.in(
                                JPAExpressions.select(
                                        qGoodTypeBean.id
                                )
                                        .from(qGoodTypeBean)
                                        .where(qGoodTypeBean.name.like("%蔬菜%"))
                        )
                ).fetch();
    }

    /**
     * 子查询 价格最高的商品列表
     *
     * @return
     */
    @RequestMapping(value = "/childEqSelect")
    public List<GoodInfoBean> childEqSelect() {
        //商品基本信息查询实体
        QGoodInfoBean qGoodInfoBean = QGoodInfoBean.goodInfoBean;

        return queryFactory
                .selectFrom(qGoodInfoBean)
                //查询价格最大的商品列表
                .where(qGoodInfoBean.price.eq(
                        JPAExpressions.select(
                                qGoodInfoBean.price.max()
                        )
                                .from(qGoodInfoBean)
                ))
                .fetch();
    }

    /**
     * 子查询 价格高于平均价格的商品列表
     *
     * @return
     */
    @RequestMapping(value = "/childGtAvgSelect")
    public List<GoodInfoBean> childGtAvgSelect() {
        //商品基本信息查询实体
        QGoodInfoBean qGoodInfoBean = QGoodInfoBean.goodInfoBean;
        return queryFactory
                .selectFrom(qGoodInfoBean)
                //查询价格高于平均价的商品列表
                .where(
                        qGoodInfoBean.price.gt(
                                JPAExpressions.select(qGoodInfoBean.price.avg())
                                        .from(qGoodInfoBean)
                        )
                ).fetch();
    }
}
package com.lyhq.querydsl.chapter05.controller;

import com.lyhq.querydsl.chapter05.bean.GoodInfoBean;
import com.lyhq.querydsl.chapter05.bean.QGoodInfoBean;
import com.lyhq.querydsl.chapter05.bean.QGoodTypeBean;
import com.lyhq.querydsl.chapter05.dto.GoodDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/9
 * Time：15:24
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@RestController
public class GoodController {

    @Autowired
    private EntityManager entityManager;

    //查询工厂实体
    private JPAQueryFactory queryFactory;

    //实例化控制器完成后执行该方法实例化JPAQueryFactory
    @PostConstruct
    public void initFactory() {
        System.out.println("开始实例化JPAQueryFactory");
        queryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * 根据QueryDSL查询
     *
     * @return
     */
    @RequestMapping(value = "/selectWithQueryDSL")
    public List<GoodDTO> selectWithQueryDsl() throws InterruptedException {
        //商品基本信息
        QGoodInfoBean qGoodInfoBean = QGoodInfoBean.goodInfoBean;
        //商品类型
        QGoodTypeBean qGoodTypeBean = QGoodTypeBean.goodTypeBean;

        return queryFactory
                .select(
                        Projections.bean(
                                GoodDTO.class,//返回自定义实体的类型
                                qGoodInfoBean.id,
                                qGoodInfoBean.price,
                                qGoodInfoBean.title,
                                qGoodInfoBean.unit,
                                qGoodTypeBean.name.as("typeName"),//使用别名对应dto内的typeName
                                qGoodTypeBean.id.as("typeId")//使用别名对应dto内的typeId
                        )
                )
                .from(qGoodInfoBean, qGoodTypeBean)//构建两表笛卡尔集
                .where(qGoodInfoBean.typeId.eq(qGoodTypeBean.id))//关联两表
                .orderBy(qGoodInfoBean.order.desc())//倒序
                .fetch();
    }

    /**
     * 使用java8新特性Collection内stream方法转换dto
     *
     * @return
     */
    @RequestMapping(value = "/selectWithStream")
    public List<GoodDTO> selectWithStream() {

        //商品基本信息
        QGoodInfoBean qGoodInfoBean = QGoodInfoBean.goodInfoBean;
        //商品类型
        QGoodTypeBean qGoodTypeBean = QGoodTypeBean.goodTypeBean;
        return queryFactory
                .select(
                        qGoodInfoBean.id,
                        qGoodInfoBean.price,
                        qGoodInfoBean.title,
                        qGoodInfoBean.unit,
                        qGoodTypeBean.name,
                        qGoodTypeBean.id
                )
                .from(qGoodInfoBean, qGoodTypeBean)//构建两表笛卡尔集
                .where(qGoodInfoBean.typeId.eq(qGoodTypeBean.id))//关联两表
                .orderBy(qGoodInfoBean.order.desc())//倒序
                .fetch()
                .stream()
                //转换集合内的数据
                .map(tuple -> {
                    //创建商品dto
                    GoodDTO dto = new GoodDTO();
                    //设置商品编号
                    dto.setId(tuple.get(qGoodInfoBean.id));
                    //设置商品价格
                    dto.setPrice(tuple.get(qGoodInfoBean.price));
                    //设置商品标题
                    dto.setTitle(tuple.get(qGoodInfoBean.title));
                    //设置单位
                    dto.setUnit(tuple.get(qGoodInfoBean.unit));
                    //设置类型编号
                    dto.setTypeId(tuple.get(qGoodTypeBean.id));
                    //设置类型名称
                    dto.setTypeName(tuple.get(qGoodTypeBean.name));
                    //返回本次构建的dto
                    return dto;
                })
                //返回集合并且转换为List<GoodDTO>
                .collect(Collectors.toList());
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
                .selectFrom(qGoodInfoBean)//查询商品基本信息表
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
}
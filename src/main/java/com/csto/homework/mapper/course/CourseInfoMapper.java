package com.csto.homework.mapper.course;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 处理课程信息的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface CourseInfoMapper {
    /**
     * 获取在指定时间内(七天)不同类型商品的交易数量
     * @param parameterMap
     * @return
     */
    Integer countSellNumberByCommodity(Map parameterMap);

    /**
     * 获取所有交易完成的商品的数量
     * @return
     */
    Integer countAllSellGoodsNumber();

}

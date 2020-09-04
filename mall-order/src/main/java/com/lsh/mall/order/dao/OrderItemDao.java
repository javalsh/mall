package com.lsh.mall.order.dao;

import com.lsh.mall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author lsh
 * @email javalsh@126.com
 * @date 2020-09-04 11:43:38
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}

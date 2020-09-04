package com.lsh.mall.coupon.dao;

import com.lsh.mall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author lsh
 * @email javalsh@126.com
 * @date 2020-09-04 11:40:52
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}

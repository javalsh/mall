package com.lsh.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.common.utils.PageUtils;
import com.lsh.mall.coupon.entity.CouponSpuRelationEntity;

import java.util.Map;

/**
 * 优惠券与产品关联
 *
 * @author lsh
 * @email javalsh@126.com
 * @date 2020-09-04 11:40:52
 */
public interface CouponSpuRelationService extends IService<CouponSpuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


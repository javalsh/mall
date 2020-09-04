package com.lsh.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.common.utils.PageUtils;
import com.lsh.mall.order.entity.OrderReturnApplyEntity;

import java.util.Map;

/**
 * 订单退货申请
 *
 * @author lsh
 * @email javalsh@126.com
 * @date 2020-09-04 11:43:38
 */
public interface OrderReturnApplyService extends IService<OrderReturnApplyEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


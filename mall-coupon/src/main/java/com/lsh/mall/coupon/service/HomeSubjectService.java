package com.lsh.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.common.utils.PageUtils;
import com.lsh.mall.coupon.entity.HomeSubjectEntity;

import java.util.Map;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 *
 * @author lsh
 * @email javalsh@126.com
 * @date 2020-09-04 11:40:52
 */
public interface HomeSubjectService extends IService<HomeSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


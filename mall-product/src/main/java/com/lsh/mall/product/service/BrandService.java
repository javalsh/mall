package com.lsh.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.common.utils.PageUtils;
import com.lsh.mall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author lsh
 * @email javalsh@126.com
 * @date 2020-09-03 21:52:29
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


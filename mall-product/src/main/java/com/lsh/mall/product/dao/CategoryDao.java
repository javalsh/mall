package com.lsh.mall.product.dao;

import com.lsh.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author lsh
 * @email javalsh@126.com
 * @date 2020-09-03 21:52:29
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}

package com.lsh.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lsh.mall.product.dao.BrandDao;
import com.lsh.mall.product.dao.CategoryDao;
import com.lsh.mall.product.entity.BrandEntity;
import com.lsh.mall.product.entity.CategoryEntity;
import jdk.nashorn.internal.ir.IfNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.common.utils.PageUtils;
import com.lsh.common.utils.Query;

import com.lsh.mall.product.dao.CategoryBrandRelationDao;
import com.lsh.mall.product.entity.CategoryBrandRelationEntity;
import com.lsh.mall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private BrandDao brandDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long catelogId = categoryBrandRelation.getCatelogId();
        Long brandId = categoryBrandRelation.getBrandId();
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        BrandEntity brandEntity = brandDao.selectById(brandId);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setBrandId(brandId);
        categoryBrandRelationEntity.setBrandName(name);
        this.update(categoryBrandRelationEntity,new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));
    }

    @Override
    public void updateCategory(Long catId, String name) {
        this.baseMapper.updateCategory(catId,name);
    }

    @Override
    public List<BrandEntity> getBrandsByCatId(Long catId) {
        List<CategoryBrandRelationEntity> brandRelationEntities = this.list(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
        List<Long> list = brandRelationEntities.stream()
                .map(categoryBrandRelationEntity -> {
                    return categoryBrandRelationEntity.getBrandId();
                }).collect(Collectors.toList());
        if (list!=null&&list.size()>0){
            return brandDao.selectBatchIds(list);
        }else {
            return null;
        }

    }

}
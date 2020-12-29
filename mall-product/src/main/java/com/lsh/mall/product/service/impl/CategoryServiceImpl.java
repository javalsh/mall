package com.lsh.mall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.common.utils.PageUtils;
import com.lsh.common.utils.Query;

import com.lsh.mall.product.dao.CategoryDao;
import com.lsh.mall.product.entity.CategoryEntity;
import com.lsh.mall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> list = baseMapper.selectList(null);
        List<CategoryEntity> treeList = list.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .sorted((c1,c2)->(c1.getSort()==null?0:c1.getSort())-(c2.getSort()==null?0:c2.getSort()))
                .map(categoryEntity -> {
                    categoryEntity.setChild(getChild(categoryEntity, list));
                    return categoryEntity;
                })
                .collect(Collectors.toList());
        return treeList;
    }

    private List<CategoryEntity> getChild(CategoryEntity categoryEntityParent, List<CategoryEntity> list) {
        List<CategoryEntity> treeList = list.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == categoryEntityParent.getCatId())
                .sorted((c1,c2)->{
                    return (c1.getSort()==null?0:c1.getSort())-(c2.getSort()==null?0:c2.getSort());
                })
                .map(categoryEntity -> {
                    categoryEntity.setChild(getChild(categoryEntity, list));
                    return categoryEntity;
                })
                .collect(Collectors.toList());
        return treeList;
    }

}
package com.lsh.mall.product.service.impl;

import com.lsh.mall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {


    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;
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
                    categoryEntity.setChildren(getChild(categoryEntity, list));
                    return categoryEntity;
                })
                .collect(Collectors.toList());
        return treeList;
    }

    @Override
    public void removeByLogic(List<Long> asList) {
        //TODO
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public List<Long> getCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);

        Collections.reverse(parentPath);


        return parentPath;
    }

    @Transactional
    @Override
    public void updateDetail(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());

    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        paths.add(catelogId);
        CategoryEntity categoryEntity = baseMapper.selectById(catelogId);
        if (categoryEntity.getParentCid()!=0){
            findParentPath(categoryEntity.getParentCid(),paths);
        }
        return paths;
    }


    private List<CategoryEntity> getChild(CategoryEntity categoryEntityParent, List<CategoryEntity> list) {
        List<CategoryEntity> treeList = list.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == categoryEntityParent.getCatId())
                .sorted((c1,c2)->{
                    return (c1.getSort()==null?0:c1.getSort())-(c2.getSort()==null?0:c2.getSort());
                })
                .map(categoryEntity -> {
                    categoryEntity.setChildren(getChild(categoryEntity, list));
                    return categoryEntity;
                })
                .collect(Collectors.toList());
        return treeList;
    }

}
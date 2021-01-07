package com.lsh.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lsh.common.constant.ProductConstant;
import com.lsh.mall.product.dao.AttrAttrgroupRelationDao;
import com.lsh.mall.product.dao.AttrGroupDao;
import com.lsh.mall.product.dao.CategoryDao;
import com.lsh.mall.product.entity.AttrAttrgroupRelationEntity;
import com.lsh.mall.product.entity.AttrGroupEntity;
import com.lsh.mall.product.entity.CategoryEntity;
import com.lsh.mall.product.service.CategoryService;
import com.lsh.mall.product.vo.AttrGroupRelationVo;
import com.lsh.mall.product.vo.AttrRespVo;
import com.lsh.mall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.common.utils.PageUtils;
import com.lsh.common.utils.Query;

import com.lsh.mall.product.dao.AttrDao;
import com.lsh.mall.product.entity.AttrEntity;
import com.lsh.mall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    AttrGroupDao attrGroupDao;
    @Autowired
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.save(attrEntity);
        if (attr.getAttrType()== ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()&&attr.getAttrGroupId()!=null){
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }


    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, String attrType, Long catelogId) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>();
        String key = (String) params.get("key");
        queryWrapper.eq("attr_type","base".equalsIgnoreCase(attrType)?1:0);
        if (catelogId!=0){
            queryWrapper.eq("catelog_id",catelogId);
        }
        if (!StringUtils.isEmpty(key)){
            queryWrapper.and(query->{
                query.eq("attr_id",key).or().like("attr_name",key);
            });
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> list = records.stream()
                .map(attrEntity -> {
                    AttrRespVo attrRespVo = new AttrRespVo();
                    BeanUtils.copyProperties(attrEntity,attrRespVo);
                    CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
                    if (categoryEntity!=null){
                        attrRespVo.setCatelogName(categoryEntity.getName());
                        List<Long> catelogPath = categoryService.getCatelogPath(attrEntity.getCatelogId());
                        attrRespVo.setCatelogPath(catelogPath);
                    }

                    if (attrEntity.getAttrType()==ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
                        AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
                        if (relationEntity!=null){
                            Long attrGroupId = relationEntity.getAttrGroupId();
                            AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                            if (attrGroupEntity!=null){
                                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                            }

                        }

                    }

                    return attrRespVo;
                })
                .collect(Collectors.toList());

        pageUtils.setList(list);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity,attrRespVo);

        CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
        if (categoryEntity!=null){
            attrRespVo.setCatelogName(categoryEntity.getName());
            List<Long> catelogPath = categoryService.getCatelogPath(attrEntity.getCatelogId());
            attrRespVo.setCatelogPath(catelogPath);
        }
        if (attrEntity.getAttrType()==ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            if (relationEntity!=null){
                Long attrGroupId = relationEntity.getAttrGroupId();
                attrRespVo.setAttrGroupId(attrGroupId);
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                if (attrGroupEntity!=null){
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }

            }

        }
        return attrRespVo;
    }

    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.updateById(attrEntity);
        if (attrEntity.getAttrType()==ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()&&attr.getAttrGroupId()!=null){
            Integer count = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attr.getAttrId());
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            if (count>0){
                attrAttrgroupRelationDao.update(attrAttrgroupRelationEntity,new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attr.getAttrId()));
            }else {
                attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
            }
        }
    }

    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> relationEntities = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
        if (relationEntities!=null&&relationEntities.size()>0){
            List<AttrEntity> attrEntities = relationEntities.stream()
                    .map(relationEntity -> {
                        AttrEntity attrEntity = this.getById(relationEntity.getAttrId());
                        return attrEntity;
                    }).collect(Collectors.toList());
            return attrEntities;
        }else {
            return null;
        }


    }

    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        List<AttrGroupEntity> attrGroupEntities = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> groupIds = attrGroupEntities.stream()
                .map(attrGroup -> {
                    return attrGroup.getAttrGroupId();
                }).collect(Collectors.toList());
        List<AttrAttrgroupRelationEntity> relationEntities = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", groupIds));
        List<Long> attrIds = relationEntities.stream()
                .map(relationEntity -> {
                    return relationEntity.getAttrId();
                }).collect(Collectors.toList());
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(query->{
            query.eq("catelog_id",catelogId).eq("attr_type",ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        });
        if (attrIds!=null&&attrIds.size()>0){
            queryWrapper.notIn("attr_id",attrIds);
        }
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            queryWrapper.and((w)->{
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);

        PageUtils pageUtils = new PageUtils(page);

        return pageUtils;
    }


}
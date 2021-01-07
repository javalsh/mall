package com.lsh.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lsh.mall.product.entity.AttrEntity;
import com.lsh.mall.product.service.AttrAttrgroupRelationService;
import com.lsh.mall.product.service.AttrService;
import com.lsh.mall.product.service.CategoryService;
import com.lsh.mall.product.vo.AttrGroupRelationVo;
import com.lsh.mall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lsh.mall.product.entity.AttrGroupEntity;
import com.lsh.mall.product.service.AttrGroupService;
import com.lsh.common.utils.PageUtils;
import com.lsh.common.utils.R;



/**
 * 属性分组
 *
 * @author lsh
 * @email javalsh@126.com
 * @date 2020-09-03 21:52:29
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    @Autowired
    AttrAttrgroupRelationService relationService;

    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId")Long catelogId){

        //1、查出当前分类下的所有属性分组，
        //2、查出每个属性分组的所有属性
        List<AttrGroupWithAttrsVo> vos =  attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return R.ok().put("data",vos);
    }

    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos){

        relationService.saveBatch(vos);
        return R.ok();
    }
    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody  List<AttrGroupRelationVo> vos){
        relationService.deleteRelation(vos);
        return R.ok();
    }


    /**
     * 获取属性分组的关联的所有属性列表
     */
    @RequestMapping("/{attrgroupId}/attr/relation")
    //@RequiresPermissions("product:attrgroup:list")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId){
        List<AttrEntity> entities =  attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data",entities);
    }
    ///product/attrgroup/{attrgroupId}/noattr/relation
    /**
     * 获取属性分组没有关联的其他属性
     */
    @RequestMapping("/{attrgroupId}/noattr/relation")
    //@RequiresPermissions("product:attrgroup:list")
    public R attrNoRelation(@RequestParam Map<String, Object> params,
                            @PathVariable("attrgroupId") Long attrgroupId){
        PageUtils page = attrService.getNoRelationAttr(params,attrgroupId);

        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrGroupService.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/list/{catId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R listByCatId(@RequestParam Map<String, Object> params,@PathVariable("catId") Long catId){
        PageUtils page = attrGroupService.queryPageByCatId(params,catId);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        List<Long> path = categoryService.getCatelogPath(attrGroup.getCatelogId());
        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}

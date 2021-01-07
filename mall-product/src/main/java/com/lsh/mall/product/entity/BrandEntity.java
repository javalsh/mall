package com.lsh.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.lsh.common.valid.AddGroup;
import com.lsh.common.valid.ListValue;
import com.lsh.common.valid.UpdateGroup;
import com.lsh.common.valid.UpdateStatusGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 品牌
 * 
 * @author lsh
 * @email javalsh@126.com
 * @date 2020-09-03 21:52:29
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	@Null(groups = {AddGroup.class})
	@NotNull(groups = {UpdateGroup.class})
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(groups = {AddGroup.class,UpdateGroup.class})
	@Null(groups = {UpdateStatusGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@ListValue(vals = {0,1},groups = {AddGroup.class,UpdateGroup.class,UpdateStatusGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	private String firstLetter;
	/**
	 * 排序
	 */
	private Integer sort;

}

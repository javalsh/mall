package com.lsh.mall.member.dao;

import com.lsh.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author lsh
 * @email javalsh@126.com
 * @date 2020-09-04 11:42:33
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}

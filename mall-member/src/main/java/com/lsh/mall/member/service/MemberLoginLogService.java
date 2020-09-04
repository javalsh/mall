package com.lsh.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.common.utils.PageUtils;
import com.lsh.mall.member.entity.MemberLoginLogEntity;

import java.util.Map;

/**
 * 会员登录记录
 *
 * @author lsh
 * @email javalsh@126.com
 * @date 2020-09-04 11:42:33
 */
public interface MemberLoginLogService extends IService<MemberLoginLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


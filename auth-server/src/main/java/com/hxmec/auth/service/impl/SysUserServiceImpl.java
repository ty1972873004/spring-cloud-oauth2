package com.hxmec.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxmec.auth.entity.SysUser;
import com.hxmec.auth.mapper.SysUserMapper;
import com.hxmec.auth.service.ISysUserService;
import org.springframework.stereotype.Service;

/**
 * 功能描述: 
 * @author  Trazen
 * @date  2020/7/14 14:56
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
}

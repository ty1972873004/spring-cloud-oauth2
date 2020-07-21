package com.hxmec.test.mapper;

import com.hxmec.auth.AuthServerApplication;
import com.hxmec.auth.entity.SysUser;
import com.hxmec.auth.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = AuthServerApplication.class)
public class SysMapperTest {

    @Resource
    private SysUserMapper sysUserMapper;

    @Test
    public void test1(){
        SysUser sysUser = new SysUser();
        sysUser.setUsername("trazen");
        sysUser.setPassword("123456");
        sysUser.setDelFlag(false);
        sysUser.setEmail("trazen@126.com");
        sysUser.setMobile("18559756159");
        sysUserMapper.insert(sysUser);
    }

    @Test
    public void test2(){
        SysUser sysUser = new SysUser();
        sysUser.setId(1282941563927805954L);
        sysUser.setUsername("trazen");
        sysUserMapper.updateById(sysUser);
    }


}

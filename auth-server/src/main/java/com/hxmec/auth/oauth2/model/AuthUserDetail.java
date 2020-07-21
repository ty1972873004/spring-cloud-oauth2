package com.hxmec.auth.oauth2.model;

import com.hxmec.auth.entity.SysUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

/**
 * 功能描述: 
 * @author  Trazen
 * @date  2020/7/14 15:55
 */
public class AuthUserDetail extends User {

    @Setter
    @Getter
    private SysUser sysUser;

    public AuthUserDetail(SysUser user){
        super(user.getUsername(),user.getPassword(),true,true,true, true,Collections.EMPTY_SET);
        this.sysUser = user;
    }
}

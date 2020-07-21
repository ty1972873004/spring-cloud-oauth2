package com.hxmec.auth.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 功能描述: 
 * @author  Trazen
 * @date  2020/7/14 17:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class CurrentUser {

    private Long id;

    private String email;

    private String mobile;

    private String username;

    private Collection<? extends GrantedAuthority> authorities;


}

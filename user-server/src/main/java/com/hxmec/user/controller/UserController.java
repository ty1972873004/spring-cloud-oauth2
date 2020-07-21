package com.hxmec.user.controller;

import com.hxmec.auth.model.CurrentUser;
import com.hxmec.auth.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述: 
 * @author  Trazen
 * @date  2020/7/15 10:39
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @GetMapping("/a")
    @PreAuthorize("isAuthenticated()")
    public String get(@AuthenticationPrincipal CurrentUser currentUser){
        return "1";
    }

    @GetMapping("/b")
    public String get02(){
        log.info("---------------->{}",SecurityUtils.getCurrentUser());
        return SecurityUtils.getCurrentUser().getUsername();
    }

    @GetMapping("/c")
    public String get03(@AuthenticationPrincipal CurrentUser currentUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("---------------->{}",currentUser);
        return "3";
    }

}

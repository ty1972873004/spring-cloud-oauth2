package com.hxmec.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 功能描述:
 * @author  Trazen
 * @date  2020/7/15 17:41
 */
@FeignClient(value = "user-server")
public interface UserFeignApi {

    /**
     * user服务b接口
     * @return
     */
    @GetMapping("/user/b")
    String get02();

}

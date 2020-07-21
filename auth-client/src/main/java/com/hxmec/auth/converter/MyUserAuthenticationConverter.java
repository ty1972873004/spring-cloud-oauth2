package com.hxmec.auth.converter;

import com.hxmec.auth.model.CurrentUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 功能描述:  重写token解析器,根据checkToken的结果转化用户信息
 * @author  Trazen
 * @date  2020/7/14 17:16
 */
public class MyUserAuthenticationConverter implements UserAuthenticationConverter {

    private static final String N_A = "N/A";


    @Override
    public Map<String, ?> convertUserAuthentication(Authentication userAuthentication) {
        return null;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (!map.containsKey(USERNAME)){
            return null;
        }else{
            CurrentUser user = CurrentUser.builder()
                    .id((Long) map.get("id"))
                    .username((String) map.get(USERNAME))
                    .email((String) map.get("email"))
                    .mobile((String) map.get("mobile"))
                    .build();
            // 有权限信息就格式化权限信息
            if (map.containsKey("authorities") && map.get("authorities") != null){
                Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
                user.setAuthorities(authorities);
                return new UsernamePasswordAuthenticationToken(user, N_A,authorities);
            }else {

                return new UsernamePasswordAuthenticationToken(user, N_A,null);
            }
        }

    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }else if (authorities == null){

        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }

}

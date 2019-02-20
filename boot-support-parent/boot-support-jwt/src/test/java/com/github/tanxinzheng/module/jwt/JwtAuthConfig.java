package com.github.tanxinzheng.module.jwt;

import com.github.tanxinzheng.module.jwt.support.JwtLoadService;
import com.github.tanxinzheng.module.jwt.support.TestJwtLoadService;
import com.github.tanxinzheng.module.jwt.support.access.PermissionGrantedAuthority;
import com.github.tanxinzheng.module.jwt.support.access.SecurityMetadataHandler;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by tanxinzheng on 2018/9/24.
 */
@Configuration
public class JwtAuthConfig extends JwtConfigAdapter {

    @Bean
    @Override
    public JwtLoadService getJwtLoadService() {
        return new TestJwtLoadService();
    }

    @Bean
    @Override
    public SecurityMetadataHandler getSecurityMetadataHandler() {
        return new SecurityMetadataHandler() {
            @Override
            public List<PermissionGrantedAuthority> loadAllPermission() {
                List list = Lists.newArrayList();
                PermissionGrantedAuthority permissionGrantedAuthority = new PermissionGrantedAuthority();
                permissionGrantedAuthority.setUrl("/user**");
                permissionGrantedAuthority.setRoles(Lists.newArrayList("ROLE_USER"));
                permissionGrantedAuthority.setRequestMethod(RequestMethod.GET);
                list.add(permissionGrantedAuthority);
                PermissionGrantedAuthority authority = new PermissionGrantedAuthority();
                authority.setUrl("/admin/user**");
                authority.setRoles(Lists.newArrayList("ROLE_ADMIN"));
                authority.setRequestMethod(RequestMethod.GET);
                list.add(authority);
                return list;
            }
        };
    }
}
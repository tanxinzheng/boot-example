package com.github.tanxinzheng.framework.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
public class CurrentLoginUser implements UserDetails {

    private String refreshToken;
    private String token;
    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    private String avatar;
    private String email;
    private String phone;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Set<String> roles;
    private Set<String> permissions;
    private List<SimpleGrantedAuthority> authorities;

    public CurrentLoginUser(){}

    public CurrentLoginUser(String id, String name, String username, Set<String> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.roles = roles;
        Collection<SimpleGrantedAuthority> collection = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(roles)){
            roles.stream().forEach(role -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
                collection.add(simpleGrantedAuthority);
            });
        }
    }

    public CurrentLoginUser(String id, String name, String username, Set<String> roles, Set<String> permissions) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.roles = roles;
        this.permissions = permissions;
        List<SimpleGrantedAuthority> collection = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(roles)){
            roles.stream().forEach(role -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
                collection.add(simpleGrantedAuthority);
            });
        }
        if(CollectionUtils.isNotEmpty(permissions)){
            permissions.stream().forEach(permission -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
                collection.add(simpleGrantedAuthority);
            });
        }
        this.authorities = collection;
    }

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}

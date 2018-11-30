package com.github.tanxinzheng.module.jwt.support;

/**
 * Created by tanxinzheng on 2018/11/30.
 */
public enum TokenType {

    BEARER("Bearer");

    private String code;

    TokenType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

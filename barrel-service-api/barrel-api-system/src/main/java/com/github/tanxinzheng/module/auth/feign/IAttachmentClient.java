package com.github.tanxinzheng.module.auth.feign;

import com.github.tanxinzheng.framework.model.Result;
import com.github.tanxinzheng.framework.secure.domain.AuthUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Hello world!
 *
 */
@FeignClient(value = "barrel-attachment")
@Api(tags = {"Feign接口", "附件服务"})
public interface IAttachmentClient {

    /**
     * 查询用户
     * @param username
     * @return
     */
    @ApiOperation(value = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名")
    })
    @GetMapping(value = "/user-info/username")
    Result<AuthUser> getUserByUsername(@RequestParam(value = "username") String username);

    /**
     * 查询主键用户
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据主键查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID")
    })
    @GetMapping(value = "/user-info/{id}")
    Result<AuthUser> getUserByUserId(@PathVariable(value = "id") String userId);

    /**
     * 查询用户角色
     * @param id
     * @return
     */
    @ApiOperation(value = "查询用户角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户主键")
    })
    @GetMapping(value = "/user-info/{id}/roles") 
    Result<List<String>> getRoles(@PathVariable(value = "id") String id);

}
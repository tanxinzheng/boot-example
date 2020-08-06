package com.github.tanxinzheng.module.account.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/*
 * @Description TODO
 * @Author tanxinzheng
 * @Date 2020/7/14
 */
@Mapper
public interface AccountMapper {

    int updatePassword(@Param(value = "userId") String userId,
                           @Param(value = "newSalt") String newSalt,
                           @Param(value = "newPassword") String newPassword);

    int updateNickname(@Param(value = "userId") String userId,
                       @Param(value = "nickname") String nickname);

    int updateAvatar(@Param(value = "userId") String userId,
                       @Param(value = "avatar") String avatar);

    int bindEmail(@Param(value = "email") String email,
                  @Param(value = "id") String id);

    int bindPhone(@Param(value = "phone") String phone,
                  @Param(value = "id") String id);

}
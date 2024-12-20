package com.mifan.server.mapper;

import com.mifan.server.entity.UmsUsers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 存储用户信息 Mapper 接口
 * </p>
 *
 * @author Milan
 * @since 2024-12-19
 */
@Mapper
public interface UmsUsersMapper extends BaseMapper<UmsUsers> {

    @Select("SELECT u.*, r.name AS roleName FROM ums_users u LEFT JOIN ums_roles r ON u.role_id = r.id WHERE u.phone = #{phone}")
    UmsUsers selectByPhoneWithRole(String phone);

    @Select("SELECT * FROM ums_users WHERE nickname = #{nickname}")
    UmsUsers selectByNickname(String nickname);

    @Select("SELECT * FROM ums_users WHERE phone = #{phone}")
    UmsUsers selectByPhone(String phone);
}

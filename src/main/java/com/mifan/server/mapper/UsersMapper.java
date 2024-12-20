package com.mifan.server.mapper;

import com.mifan.server.entity.Users;
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
public interface UsersMapper extends BaseMapper<Users> {

    @Select("SELECT u.*, r.name AS roleName FROM users u LEFT JOIN roles r ON u.role_id = r.id WHERE u.phone = #{phone}")
    Users selectByPhoneWithRole(String phone);

    @Select("SELECT * FROM users WHERE nickname = #{nickname}")
    Users selectByNickname(String nickname);

    @Select("SELECT * FROM users WHERE phone = #{phone}")
    Users selectByPhone(String phone);
}

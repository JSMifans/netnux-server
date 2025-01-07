package com.mifan.server.mapper;

import com.mifan.server.entity.UmsUsers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

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

    @Select("SELECT u.*, r.name AS roleName FROM ums_users u " +
           "LEFT JOIN ums_roles r ON u.role_id = r.id " +
           "WHERE u.phone = #{phone} LIMIT 1")
    UmsUsers selectByPhoneWithRole(String phone);

    @Select("SELECT * FROM ums_users WHERE nickname = #{nickname} LIMIT 1")
    UmsUsers selectByNickname(String nickname);

    @Select("SELECT * FROM ums_users WHERE phone = #{phone} LIMIT 1")
    UmsUsers selectByPhone(String phone);

    @Select("<script>" +
            "SELECT u.*, r.name as roleName, p.nickname as parentName " +
            "FROM ums_users u " +
            "LEFT JOIN ums_roles r ON u.role_id = r.id " +
            "LEFT JOIN ums_users p ON u.parent_id = p.id " +
            "<where>" +
            "<if test='phone != null and phone != \"\"'>" +
            "u.phone LIKE CONCAT('%', #{phone}, '%') " +
            "</if>" +
            "AND u.role_id = 3 " +
            "</where>" +
            "ORDER BY u.created_at DESC" +
            "</script>")
    Page<UmsUsers> selectUserPage(Page<UmsUsers> page, @Param("phone") String phone);



    @Select("<script>" +
        "SELECT u.*, r.name as roleName " +
        "FROM ums_users u " +
        "LEFT JOIN ums_roles r ON u.role_id = r.id " +
        "<where>" +
        "<if test='phone != null and phone != \"\"'>" +
        "u.phone LIKE CONCAT('%', #{phone}, '%') " +
        "</if>" +
        "AND u.role_id != 3 " +
        "</where>" +
        "ORDER BY u.created_at DESC" +
        "</script>")
    Page<UmsUsers> selectAdminPage(Page<UmsUsers> page, @Param("phone") String phone);
}

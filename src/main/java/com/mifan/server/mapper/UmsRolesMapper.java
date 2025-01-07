package com.mifan.server.mapper;

import com.mifan.server.entity.UmsRoles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author Mifan
 * @since 2024-12-20
 */
@Mapper
public interface UmsRolesMapper extends BaseMapper<UmsRoles> {

    @Select("SELECT r.* FROM ums_roles r " +
           "LEFT JOIN ums_user_role ur ON r.id = ur.role_id " +
           "WHERE ur.user_id = #{userId}")
    UmsRoles getUserRole(Long userId);
}

package com.mifan.server.mapper;

import com.mifan.server.entity.UmsMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author Mifan
 * @since 2024-12-24
 */
@Mapper
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    @Select("SELECT DISTINCT m.* FROM ums_menu m " +
            "WHERE m.id IN ( " +
            "   SELECT rm.menu_id FROM ums_role_menu rm WHERE rm.role_id = #{roleId} " +
            ") OR m.id IN ( " +
            "   SELECT p.id FROM ums_menu p " +
            "   WHERE p.id IN ( " +
            "       SELECT m2.parent_id FROM ums_menu m2 " +
            "       INNER JOIN ums_role_menu rm2 ON m2.id = rm2.menu_id " +
            "       WHERE rm2.role_id = #{roleId} " +
            "   ) " +
            ") ORDER BY m.sort")
    List<UmsMenu> getUserMenus(Long roleId);

}

package com.mifan.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mifan.server.entity.UmsRoleMenu;
import com.mifan.server.mapper.UmsRoleMenuMapper;
import com.mifan.server.util.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 角色菜单关联表 前端控制器
 * </p>
 *
 * @author Mifan
 * @since 2025-01-02
 */
@RestController
@RequestMapping("/ums-role-menu")
public class UmsRoleMenuController {

    @Resource
    private UmsRoleMenuMapper umsRoleMenuMapper;

    @PostMapping("update")
    public Response update(@RequestParam Long roleId, @RequestBody Long[] menuIds) {
        // 1. 先删除该角色的所有菜单关联
        LambdaQueryWrapper<UmsRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UmsRoleMenu::getRoleId, roleId);
        umsRoleMenuMapper.delete(wrapper);
        
        // 2. 批量插入新的关联关系
        if (menuIds != null && menuIds.length > 0) {
            for (Long menuId : menuIds) {
                UmsRoleMenu roleMenu = new UmsRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                umsRoleMenuMapper.insert(roleMenu);
            }
        }
        
        return Response.success("更新成功");
    }
}

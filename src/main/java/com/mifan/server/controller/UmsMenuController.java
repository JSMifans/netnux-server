package com.mifan.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mifan.server.entity.UmsMenu;
import com.mifan.server.mapper.UmsMenuMapper;
import com.mifan.server.util.JwtUtil;
import com.mifan.server.util.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author Mifan
 * @since 2024-12-24
 */
@RestController
@RequestMapping("/ums-menu")
public class UmsMenuController {

    @Resource
    private UmsMenuMapper umsMenuMapper;

    @GetMapping("/getUserMenuList")
    public Response<List<UmsMenu>> getUserMenuList(Long roleId) {
        if (roleId == null) {
            return Response.error("角色ID不能为空");
        }
        if(roleId == 1) {
            return Response.success(umsMenuMapper.selectList(null));
        }
        
        // 获取该用户的菜单列表（通过关联表查询）
        List<UmsMenu> menus = umsMenuMapper.getUserMenus(roleId);
        System.out.println(menus);
        
        // // 如果没有菜单权限
        // if (menus == null || menus.isEmpty()) {
        //     return Response.error("没有菜单权限");
        // }
        
        return Response.success(menus);
    }


    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @GetMapping("page")
    public Response<Page<UmsMenu>> page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "100") Integer size) {
        Page<UmsMenu> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<UmsMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(UmsMenu::getSort);
        Page<UmsMenu> umsMenuPage = umsMenuMapper.selectPage(pageParam, null);
        return Response.success(umsMenuPage);
    }


    /**
     * 修改
     * @param umsMenu
     * @return
     */
    @PostMapping("save")
    public Response save(@RequestBody UmsMenu umsMenu) {
        int update = umsMenuMapper.updateById(umsMenu);
        return Response.success(update);
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("delete")
    public Response delete(Long id) {
        int update = umsMenuMapper.deleteById(id);
        return Response.success(update);
    }


    /**
     * 新增
     * @param umsMenu
     * @return
     */
    @PostMapping("insert")
    public Response insert(@RequestBody UmsMenu umsMenu) {
        int insert = umsMenuMapper.insert(umsMenu);
        return Response.success(insert);
    }

    
}

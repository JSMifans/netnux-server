package com.mifan.server.controller;


import com.mifan.server.entity.UmsRoles;
import com.mifan.server.mapper.UmsRolesMapper;
import com.mifan.server.util.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author Mifan
 * @since 2024-12-20
 */
@RestController
@RequestMapping("/ums-roles")
public class UmsRolesController {

    @Resource
    private UmsRolesMapper umsRolesMapper;


    @GetMapping("page")
    public Response page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String name) {
                
        // 创建分页对象
        Page<UmsRoles> pageParam = new Page<>(page, size);
        
        // 创建查询条件
        LambdaQueryWrapper<UmsRoles> queryWrapper = new LambdaQueryWrapper<>();
  
        if (StringUtils.hasText(name)) {
            queryWrapper.like(UmsRoles::getName, name);
        }
        
        // 执行分页查询
        Page<UmsRoles> result = umsRolesMapper.selectPage(pageParam, queryWrapper);
        
        return Response.success(result);
    }

    /**
     * 新增角色
     * @param umsRoles
     * @return
     */
    @PostMapping("/insert")
    public Response<UmsRoles> insert(@RequestBody UmsRoles umsRoles) {
        // 参数校验
        if (umsRoles == null) {
            return Response.error("参数不能为空");
        }

        if (StringUtils.isEmpty(umsRoles.getName())) {
            return Response.error("角色名称不能为空");
        }

        // 超级管理员校验
        if(umsRoles.getId() != null && umsRoles.getId() == 1) {
            return Response.error("超级管理员不能被设置");
        }

        // 检查角色名是否已存在
        LambdaQueryWrapper<UmsRoles> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UmsRoles::getName, umsRoles.getName());
        if(umsRolesMapper.selectCount(queryWrapper) > 0) {
            return Response.error("角色名称已存在");
        }

        // 新增角色
        int result = umsRolesMapper.insert(umsRoles);
        if (result > 0) {
            return Response.success(umsRoles);
        } else {
            return Response.error("新增失败");
        }
    }

    /**
     * 更新角色
     * @param umsRoles
     * @return
     */
    @PostMapping("/update")
    public Response<UmsRoles> update(@RequestBody UmsRoles umsRoles) {
        // 参数校验
        if (umsRoles == null || umsRoles.getId() == null) {
            return Response.error("参数不能为空");
        }

        if (StringUtils.isEmpty(umsRoles.getName())) {
            return Response.error("角色名称不能为空");
        }

        // 超级管理员校验
        if(umsRoles.getId() == 1) {
            return Response.error("超级管理员不能被设置");
        }

        // 检查角色名是否已存在（排除当前记录）
        LambdaQueryWrapper<UmsRoles> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UmsRoles::getName, umsRoles.getName())
                .ne(UmsRoles::getId, umsRoles.getId());
        if(umsRolesMapper.selectCount(queryWrapper) > 0) {
            return Response.error("角色名称已存在");
        }

        // 更新角色
        int result = umsRolesMapper.updateById(umsRoles);
        if (result > 0) {
            return Response.success(umsRoles);
        } else {
            return Response.error("更新失败");
        }
    }


    /**
     * 删除角色
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public Response<UmsRoles> delete(@PathVariable Long id) {
        // 超级管理员校验
        if(id == 1) {
            return Response.error("超级管理员不能被删除");
        }

        umsRolesMapper.deleteById(id);
        return Response.success(null);
    }
}

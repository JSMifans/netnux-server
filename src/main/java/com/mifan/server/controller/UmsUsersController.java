package com.mifan.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mifan.server.mapper.UmsUsersMapper;
import com.mifan.server.util.JwtUtil;
import com.mifan.server.util.Response;
import com.mifan.server.entity.UmsUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 存储用户信息 前端控制器
 * </p>
 *
 * @author Milan
 * @since 2024-12-19
 */
@RestController
@RequestMapping("/ums-users")
public class UmsUsersController {

    @Resource
    private UmsUsersMapper umsUsersMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("userInfo")
    public Response<UmsUsers> userInfo(@RequestHeader("Authorization") String token) {
        System.out.println(token);
        // 从令牌中提取用户标识符（如手机号）
        String phone = JwtUtil.extractClaims(token.substring(7)).getSubject();
        System.out.println(phone);
        // 查询用户信息，包括角色信息
        UmsUsers user = umsUsersMapper.selectByPhoneWithRole(phone);
        if (user == null) {
            return Response.error("用户不存在");
        }

        // 返回用户信息
        return Response.success(user);
    }


    /**
     *  获取用户 page 普通用户
     * @param page
     * @param phone
     * @return
     */
    @GetMapping("/pageByUser")
    public Response<Page<UmsUsers>> pageByUser(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            String phone) {
        Page<UmsUsers> pageParams = new Page<>(page, size);
        Page<UmsUsers> umsUsersPage = umsUsersMapper.selectUserPage(pageParams, phone);
        return Response.success(umsUsersPage);
    }

    /**
     * 获取用户 page 管理员
     * @param page
     * @param size
     * @param phone
     * @return
     */
    @GetMapping("/pageByAdmin")
    public Response<Page<UmsUsers>> pageByAdmin(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            String phone) {
        Page<UmsUsers> pageParams = new Page<>(page, size);
        Page<UmsUsers> umsUsersPage = umsUsersMapper.selectAdminPage(pageParams, phone);
        return Response.success(umsUsersPage);
    }

    /**
     * 更新用户
     * @param umsUsers
     * @return
     */
    @PostMapping("/update")
    public Response<UmsUsers> update(@RequestBody UmsUsers umsUsers) {
        if(umsUsers.getRoleId() == 1) {
            return Response.error("超级管理员不能被设置");
        }
        if(umsUsers.getId() == 1) {
            return Response.error("超级管理员不能被设置");
        }
        umsUsersMapper.updateById(umsUsers);
        return Response.success(umsUsers);
    }


    /**
     * 删除用户
     * @param id 用户ID
     * @return
     */
    @GetMapping("/delete/{id}")
    public Response<UmsUsers> delete(@PathVariable Long id) {
        // 判断是否为超级管理员
        if(id == 1) {
            return Response.error("超级管理员不能被删除");
        }
        
        // 删除用户
        int result = umsUsersMapper.deleteById(id);
        if(result > 0) {
            return Response.success(null);
        } else {
            return Response.error("删除失败");
        }
    }


    /**
     * 新增用户
     * @param umsUsers
     * @return
     */
    @PostMapping("/insertManager")
    public Response<UmsUsers> add(@RequestBody UmsUsers umsUsers, @RequestHeader("Authorization") String token) {
        if(umsUsers.getRoleId() == 1) {
            return Response.error("超级管理员不能被设置");
        }
        String phone = JwtUtil.extractClaims(token.substring(7)).getSubject();
        UmsUsers roleUmsUsers = umsUsersMapper.selectByPhone(phone);
        if(roleUmsUsers.getRoleId() != 1) {
            return Response.error("非超级管理员不能新增管理角色");
        }
        umsUsers.setPasswordHash(passwordEncoder.encode("123456"));
        umsUsers.setParentId(0);
        umsUsersMapper.insert(umsUsers);
        return Response.success(umsUsers);
    }
}

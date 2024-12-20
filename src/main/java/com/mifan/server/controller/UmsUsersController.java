package com.mifan.server.controller;

import com.mifan.server.mapper.UmsUsersMapper;
import com.mifan.server.util.JwtUtil;
import com.mifan.server.util.Response;
import com.mifan.server.entity.UmsUsers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("info")
    public Response<UmsUsers> userinfo(@RequestHeader("Authorization") String token) {
        // 从令牌中提取用户标识符（如手机号）
        String phone = JwtUtil.extractClaims(token.substring(7)).getSubject();

        // 查询用户信息，包括角色信息
        UmsUsers user = umsUsersMapper.selectByPhoneWithRole(phone);
        if (user == null) {
            return Response.error("用户不存在");
        }

        // 返回用户信息
        return Response.success(user);
    }
}

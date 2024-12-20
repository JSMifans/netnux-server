package com.mifan.server.controller;

import com.mifan.server.mapper.UsersMapper;
import com.mifan.server.util.JwtUtil;
import com.mifan.server.util.Response;
import com.mifan.server.entity.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AuthController {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UsersMapper usersMapper;

    @PostMapping("/register")
    public Response<String> register(@RequestBody Users users) {
        // 检查手机号唯一性
        if (usersMapper.selectByPhone(users.getPhone()) != null) {
            return Response.error("手机号已被注册");
        }

        // 检查昵称唯一性
        if (usersMapper.selectByNickname(users.getNickname()) != null) {
            return Response.error("昵称已被使用");
        }

        // 对密码进行哈希处理
        String hashedPassword = passwordEncoder.encode(users.getPasswordHash());
        users.setPasswordHash(hashedPassword);

        // 将用户信息存入数据库
        usersMapper.insert(users);
        return Response.success("注册成功");
    }


    @PostMapping("/login")
    public Response<String> login(@RequestBody Users users) {
        // 检查手机号是否存在
        Users user = usersMapper.selectByPhone(users.getPhone());
        if (user == null) {
            return Response.error("手机号不存在");
        }

        // 检查密码是否正确
        if (!passwordEncoder.matches(users.getPasswordHash(), user.getPasswordHash())) {
            return Response.error("密码错误");
        }

        // 生成JWT令牌
        String token = JwtUtil.generateToken(user.getPhone());

        return Response.success(token);
    }

} 
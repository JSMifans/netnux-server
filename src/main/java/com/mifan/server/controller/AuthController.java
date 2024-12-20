package com.mifan.server.controller;

import com.mifan.server.mapper.UmsUsersMapper;
import com.mifan.server.util.JwtUtil;
import com.mifan.server.util.Response;
import com.mifan.server.entity.UmsUsers;
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
    private UmsUsersMapper umsUsersMapper;

    @PostMapping("/register")
    public Response<String> register(@RequestBody UmsUsers umsUsers) {
        // 检查手机号唯一性
        if (umsUsersMapper.selectByPhone(umsUsers.getPhone()) != null) {
            return Response.error("手机号已被注册");
        }

        // 检查昵称唯一性
        if (umsUsersMapper.selectByNickname(umsUsers.getNickname()) != null) {
            return Response.error("昵称已被使用");
        }

        // 对密码进行哈希处理
        String hashedPassword = passwordEncoder.encode(umsUsers.getPasswordHash());
        umsUsers.setPasswordHash(hashedPassword);

        // 将用户信息存入数据库
        umsUsersMapper.insert(umsUsers);
        return Response.success("注册成功");
    }


    @PostMapping("/login")
    public Response<String> login(@RequestBody UmsUsers umsUsers) {
        // 检查手机号是否存在
        UmsUsers user = umsUsersMapper.selectByPhone(umsUsers.getPhone());
        if (user == null) {
            return Response.error("手机号不存在");
        }

//        String encode = passwordEncoder.encode("120130");
//        System.out.println(encode);

        // 检查密码是否正确
        if (!passwordEncoder.matches(umsUsers.getPasswordHash(), user.getPasswordHash())) {
            return Response.error("密码错误");
        }
        System.out.println(umsUsers);
        // 生成JWT令牌
        String token = JwtUtil.generateToken(user.getPhone());

        return Response.success(token);
    }

} 
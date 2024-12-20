package com.mifan.server.controller;

import com.mifan.server.util.Response;
import com.mifan.server.entity.Users;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

   

    @PostMapping("/register")
    public Response<String> register(@RequestBody Users user) {
        // 处理注册逻辑
        return Response.success("User registered successfully");
    }
} 
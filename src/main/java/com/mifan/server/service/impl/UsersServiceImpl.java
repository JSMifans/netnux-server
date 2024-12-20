package com.mifan.server.service.impl;

import com.mifan.server.entity.Users;
import com.mifan.server.mapper.UsersMapper;
import com.mifan.server.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 存储用户信息 服务实现类
 * </p>
 *
 * @author Milan
 * @since 2024-12-19
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}

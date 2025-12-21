package com.gxuwz.ccsa_server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxuwz.ccsa_server.common.Result;
import com.gxuwz.ccsa_server.entity.Admin;
import com.gxuwz.ccsa_server.entity.User;
import com.gxuwz.ccsa_server.mapper.AdminMapper;
import com.gxuwz.ccsa_server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserMapper userMapper;

    // 管理员登录
    @PostMapping("/admin/login")
    public Result<Admin> adminLogin(@RequestBody Admin admin) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", admin.getAccount());
        queryWrapper.eq("password", admin.getPassword());

        Admin result = adminMapper.selectOne(queryWrapper);
        if (result != null) {
            return Result.success(result);
        } else {
            return Result.error("账号或密码错误");
        }
    }

    // 居民登录
    @PostMapping("/user/login")
    public Result<User> userLogin(@RequestBody User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getPhone());
        queryWrapper.eq("password", user.getPassword());

        User result = userMapper.selectOne(queryWrapper);
        if (result != null) {
            return Result.success(result);
        } else {
            return Result.error("手机号或密码错误");
        }
    }

    // 居民注册
    @PostMapping("/user/register")
    public Result<User> userRegister(@RequestBody User user) {
        // 检查手机号是否重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getPhone());
        if (userMapper.selectCount(queryWrapper) > 0) {
            return Result.error("该手机号已注册");
        }

        int rows = userMapper.insert(user);
        if (rows > 0) {
            return Result.success(user);
        } else {
            return Result.error("注册失败");
        }
    }
}
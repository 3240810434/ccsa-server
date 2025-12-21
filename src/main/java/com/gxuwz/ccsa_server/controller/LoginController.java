package com.gxuwz.ccsa_server.controller;

import com.gxuwz.ccsa_server.common.Result; // 假设你有一个通用的Result类，如果没有请使用Map返回
import com.gxuwz.ccsa_server.entity.Admin;
import com.gxuwz.ccsa_server.entity.Merchant;
import com.gxuwz.ccsa_server.entity.User;
import com.gxuwz.ccsa_server.repository.AdminRepository;
import com.gxuwz.ccsa_server.repository.MerchantRepository;
import com.gxuwz.ccsa_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private MerchantRepository merchantRepository;

    // ================= 居民接口 =================

    @PostMapping("/resident/register")
    public Map<String, Object> registerResident(@RequestBody User user) {
        // 查重
        if (userRepository.findByPhone(user.getPhone()) != null) {
            return buildError("该手机号已注册");
        }
        try {
            User savedUser = userRepository.save(user);
            return buildSuccess(savedUser);
        } catch (Exception e) {
            return buildError("注册失败: " + e.getMessage());
        }
    }

    @PostMapping("/resident/login")
    public Map<String, Object> loginResident(@RequestBody User user) {
        User foundUser = userRepository.findByPhoneAndPassword(user.getPhone(), user.getPassword());
        if (foundUser != null) {
            return buildSuccess(foundUser);
        }
        return buildError("账号或密码错误");
    }

    // ================= 商家接口 =================

    @PostMapping("/merchant/register")
    public Map<String, Object> registerMerchant(@RequestBody Merchant merchant) {
        if (merchantRepository.findByPhone(merchant.getPhone()) != null) {
            return buildError("该手机号已注册");
        }
        // 设置默认状态
        if (merchant.getStatus() == null) merchant.setStatus("pending");
        if (merchant.getQualificationStatus() == null) merchant.setQualificationStatus(0);

        try {
            Merchant savedMerchant = merchantRepository.save(merchant);
            return buildSuccess(savedMerchant);
        } catch (Exception e) {
            return buildError("注册失败: " + e.getMessage());
        }
    }

    @PostMapping("/merchant/login")
    public Map<String, Object> loginMerchant(@RequestBody Merchant merchant) {
        Merchant foundMerchant = merchantRepository.findByPhoneAndPassword(merchant.getPhone(), merchant.getPassword());
        if (foundMerchant == null) {
            return buildError("账号或密码错误");
        }
        // 检查状态：如果是 'rejected' (已拒绝) 则禁止登录，其他状态允许登录
        if ("rejected".equals(foundMerchant.getStatus())) {
            return buildError("您的账号审核未通过，请联系管理员");
        }
        return buildSuccess(foundMerchant);
    }

    // ================= 管理员接口 =================

    @PostMapping("/admin/login")
    public Map<String, Object> loginAdmin(@RequestBody Admin admin) {
        // 管理员同时校验 账号、密码、小区
        Admin foundAdmin = adminRepository.findByAccountAndPassword(admin.getAccount(), admin.getPassword());

        if (foundAdmin == null) {
            return buildError("账号或密码错误");
        }
        // 校验小区权限
        if (!foundAdmin.getCommunity().equals(admin.getCommunity())) {
            return buildError("该账号没有管理 " + admin.getCommunity() + " 的权限");
        }

        return buildSuccess(foundAdmin);
    }

    // ================= 工具方法 =================
    private Map<String, Object> buildSuccess(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true); // 对应安卓 ApiResponse 的 isSuccess()
        map.put("code", 200);
        map.put("msg", "操作成功");
        map.put("data", data);
        return map;
    }

    private Map<String, Object> buildError(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("code", 400);
        map.put("msg", msg);
        return map;
    }
}
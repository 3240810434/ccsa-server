package com.gxuwz.ccsa_server.entity;

import javax.persistence.*;

@Entity
@Table(name = "merchant")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String community;       // 所属小区
    private String name;            // 店铺名称
    private String contact;         // 联系人
    private String gender;          // 性别
    private String phone;           // 电话
    private String password;        // 密码
    private String status;          // 状态 (pending, approved, rejected)

    @Column(name = "qualification_status")
    private Integer qualificationStatus; // 资质认证状态

    // --- 新增字段 (解决 Controller 报错) ---
    private String address;         // 详细地址
    private String description;     // 商家描述/公告

    @Column(name = "image_url")
    private String imageUrl;        // 商家图片/头像

    // --- 无参构造函数 ---
    public Merchant() {}

    // --- Getters and Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQualificationStatus() {
        return qualificationStatus;
    }

    public void setQualificationStatus(Integer qualificationStatus) {
        this.qualificationStatus = qualificationStatus;
    }

    // --- 新增字段的 Getters/Setters ---

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
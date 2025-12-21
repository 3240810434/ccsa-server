package com.gxuwz.ccsa_server.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxuwz.ccsa_server.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
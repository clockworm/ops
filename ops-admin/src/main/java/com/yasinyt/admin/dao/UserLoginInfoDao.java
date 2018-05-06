package com.yasinyt.admin.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yasinyt.admin.entity.UserLoginInfo;

@Mapper
public interface UserLoginInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(UserLoginInfo record);

    int insertSelective(UserLoginInfo record);

    UserLoginInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserLoginInfo record);

    int updateByPrimaryKey(UserLoginInfo record);
}
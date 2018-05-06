package com.yasinyt.admin.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yasinyt.admin.entity.Emplyee;

@Mapper
public interface EmplyeeDao {
	int deleteByPrimaryKey(String id);

	int insert(Emplyee record);

	int insertSelective(Emplyee record);

	Emplyee selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Emplyee record);

	int updateByPrimaryKey(Emplyee record);
}
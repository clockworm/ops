package com.yasinyt.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.web.form.UserForm;

@Mapper
public interface UserDao {

	int deleteByPrimaryKey(String id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	@Select("select * from ops_user where user_name = #{userName}")
	User findByUserName(@Param("userName") String userName);

	@Select("select * from ops_user where user_name = #{userName} and password = #{password}")
	User findByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);
	
	List<User> findAll(UserForm form);
}
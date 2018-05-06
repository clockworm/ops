package com.yasinyt.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yasinyt.admin.entity.Role;
import com.yasinyt.admin.web.form.RoleForm;

@Mapper
public interface RoleDao {
    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> findAll(RoleForm form);
}
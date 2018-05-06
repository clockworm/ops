package com.yasinyt.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.web.form.PermissionForm;

@Mapper
public interface PermissionDao {
    int deleteByPrimaryKey(String id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
    
    List<Permission> findAll(PermissionForm form);
    
    List<Permission> findMenuByUser(String userId);

	List<Permission> findResources();
}
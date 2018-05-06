package com.yasinyt.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yasinyt.admin.entity.Permission;
import com.yasinyt.admin.entity.Role;

@Mapper
public interface RolePermissionDao {

	@Select("select r.* from ops_role r,ops_role_permission rp where r.id = rp.role_id and rp.permission_id = #{permissionId}")
	List<Role> getRoles(@Param("permissionId") String permissionId);
	
	@Select({"<script>",
		"select p.* from ops_permission p,ops_role_permission rp where p.id = rp.permission_id",
		" and rp.role_id = #{roleId} ",
		"<when test='status!=null'>",
		" and p.status = #{status}",
		"</when>",
	"</script>"})
	List<Permission> getPermissions(@Param("roleId") String roleId,@Param("status") Integer status);
	
	List<Permission> findPermissionsByRoles(@Param("roleIds") String[] roleIds,@Param("status") Integer status);

	@Delete("delete from ops_role_permission where role_id=#{roleId}")
	int deletePermissionByRoleId(@Param("roleId") String roleId);

	int roleAddPermissionBinds(@Param("roleId") String roleId,@Param("permissionIds") String[] permissionIds);
}

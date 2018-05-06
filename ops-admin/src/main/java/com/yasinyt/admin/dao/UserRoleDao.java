package com.yasinyt.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yasinyt.admin.entity.Role;
import com.yasinyt.admin.entity.User;

@Mapper
public interface UserRoleDao {

	@Select({"<script>",
		"select r.* from ops_role r,ops_user_role ur where r.id=ur.role_id",
		" and ur.user_id = #{userId} ",
		"<when test='status!=null'>",
		" and r.status = #{status}",
		"</when>",
	"</script>"})
	List<Role> getRoles(@Param("userId") String userId, @Param("status") Integer status);

	@Select("select u.* from ops_user u,ops_user_role ur where u.id=ur.user_id and ur.role_id = #{roleId}")
	List<User> getUsers(@Param("roleId") String roleId);
	
	@Delete("delete from ops_user_role where user_id = #{userId}")
	int deleteRolesByUserId(@Param("userId") String userId);
	
	/**用户绑定角色*/
	int userAddRolesBinds(@Param("userId") String userId,@Param("roleIds")  String[] roleIds);
	
}

package com.yasinyt.admin.web.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.yasinyt.admin.base.annotation.CheckUserName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper=true)
public class UserForm extends Page{
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private String id;
	
	@NotNull(message = "用户名不能为空")
	@Length(min = 3, message = "用户名长度太短")
	@CheckUserName(required = true)
	private String username;
	
	@NotNull(message = "密码不能为空")
	@Length(min = 6, message = "密码长度太短")
	private String password;
	
	/** 昵称 */
	private String nickName;
	/** 员工主键 */
	private String emplyeeId;
	/** 启用状态 */
	private Integer status;
	/** 头像 */
	private String head;
	/**用户IP*/
	private String ip;
	/**用户系统来源*/
	private String systemName;

}

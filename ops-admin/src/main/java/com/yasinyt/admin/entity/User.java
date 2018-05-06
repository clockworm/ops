package com.yasinyt.admin.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author TangLingYun
 * @detail 用户实体 对应表:Ops_user
 */
@Data
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private String id;
	/** 用户名 */
	private String userName;
	/** 昵称 */
	private String nickName;
	/** 密码 */
	private String password;
	/** 随机盐值 */
	private String salt;
	/** 员工主键 */
	private String emplyeeId;
	/** 启用状态 */
	private Integer status;
	/** 头像 */
	private String head;
	/** 插入时间 */
	private Date insertTime = new Date();
	/** 修改时间 */
	private Date updateTime;

}
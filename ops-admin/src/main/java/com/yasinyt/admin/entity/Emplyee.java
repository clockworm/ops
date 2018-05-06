package com.yasinyt.admin.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author TangLingYun
 * @describe 员工实体类 对应表: Ops_Emplyee
 */
@Data
public class Emplyee implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键*/
	private String id;
	/** 姓名*/
	private String name;
	/** 性别*/
	private Integer sex;
	/** 出生年月*/
	private Date birthday;
	/** 民族*/
	private String nation;
	/** 婚姻状况*/
	private String maritalstatus;
	/** 籍贯*/
	private String nativeplace;
	/** 学历*/
	private String degree;
	/** 院校*/
	private String college;
	/** 毕业时间*/
	private Date graduationtime;
	/** 专业*/
	private String major;
	/** 手机号码*/
	private String mobilephone;
	/** 邮箱*/
	private String email;
	/** 居住地址*/
	private String residenceaddress;
	/** 身份证号码*/
	private String idcard;
	/** 启用状态*/
	private Integer status;
	/** 备注*/
	private String memo;
	/** 个人信息*/
	private String summary;
	/** 插入时间*/
	private Date insertTime = new Date();
	/** 修改时间*/
	private Date updateTime;
}
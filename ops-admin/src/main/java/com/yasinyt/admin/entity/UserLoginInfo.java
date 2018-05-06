package com.yasinyt.admin.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @detail 用户登录信息
 * @author TangLingYun
 */
@Data
public class UserLoginInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
    private String userId;
    private String userName;
    private String loginIp;
    private String systemName;
    private Date loginTime;

}
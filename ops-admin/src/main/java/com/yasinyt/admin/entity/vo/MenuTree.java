package com.yasinyt.admin.entity.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class MenuTree implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String pid;
	
	private String title;
	
	private String font;
	
	private String icon;
	
	private String url;
	
	private boolean spiread;
	
	private List<MenuTree> children;
}

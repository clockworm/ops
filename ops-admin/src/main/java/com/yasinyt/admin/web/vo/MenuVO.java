package com.yasinyt.admin.web.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.yasinyt.admin.entity.Permission;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MenuVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String pid;
	private String title;
	private String name;
	private String font;
	private String icon;
	private String url;
	private boolean spread;
	private Integer status;
	private String type;
	private String perm;
	private List<MenuVO> children;
	private Integer sort;
	public MenuVO() {
		// 无参数构造
	}

	/** 资源转菜单 参数构造 */
	public MenuVO(Permission permisson, List<Permission> subMenuList,boolean spread) {
		this.id = permisson.getId();
		this.pid = permisson.getParentId();
		this.title = permisson.getName();
		this.name = permisson.getName();
		this.url = permisson.getUrl();
		this.font = "larry-icon";
		this.icon = permisson.getIcon();
		this.spread = spread;
		this.status = permisson.getStatus();
		this.type = permisson.getResourceType();
		this.perm = permisson.getPermission();
		this.sort = permisson.getSortFlag();
		if(!CollectionUtils.isEmpty(subMenuList)) {
			List<MenuVO> children = new ArrayList<MenuVO>();
			MenuVO subMenu = null;
			for (Permission sub : subMenuList) {
				if (!StringUtils.equals(sub.getParentId(), permisson.getId())) continue;
				subMenu = new MenuVO(sub, Collections.emptyList(),false);
				children.add(subMenu);
			}
			this.children = children;
		}
	}
	
}

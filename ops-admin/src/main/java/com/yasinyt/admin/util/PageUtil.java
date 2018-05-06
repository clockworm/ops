package com.yasinyt.admin.util;

import com.github.pagehelper.Page;

/**
 * @author Tanglingyun
 * @detail 分页工具类
 */
public class PageUtil {
	
	/**不使用Mybaits集成使用分页 查询全部*/
	public static final Page<?> NO_USE_PAGE = new Page<>(0,0);
}

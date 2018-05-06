package com.yasinyt.system.service;

import java.io.Serializable;

import com.github.pagehelper.PageInfo;
import com.yasinyt.admin.web.form.Page;

/**
 * @author Tanglingyun
 * @detail 通用服务层接口
 * @param <T> 通用实体泛型
 */
public interface BaseService<T> {

	/** 新增数据 */
	int insert(T t);

	/** 通过Id唯一主键删除数据 */
	int deleteById(String id);

	/** 修改数据 */
	int update(T t);

	/** 通过ID唯一主键查询数据 */
	T findById(Serializable id);
	
	/** 分页查询-带条件 */
	PageInfo<T> findAll(Page page);
}

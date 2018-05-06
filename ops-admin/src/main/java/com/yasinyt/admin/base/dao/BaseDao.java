package com.yasinyt.admin.base.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {
	
	int insert(T t);

	int deleteById(Serializable id);
	
	int updateById(Serializable id);
	
	T findById(Serializable id);
	
	List<T> findAll();
}

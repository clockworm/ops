package com.yasinyt.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yasinyt.system.entity.Attachment;

@Mapper
public interface AttachmentDao {
	/** 批量添加 */
	int insertBatch(@Param("list") List<Attachment> list);

	@Select("select * from Attachment where id = #{id}")
	Attachment findById(@Param("id") String id);
}

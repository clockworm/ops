package com.yasinyt.system.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.yasinyt.system.base.BaseTest;
import com.yasinyt.system.entity.RepaymentPlan;
import com.yasinyt.system.web.form.RepaymentPlanForm;

public class RepaymentPlanServiceTest extends BaseTest{

	@Autowired
	private RepaymentPlanService planService;
	
	@Test
	public void testFindAll() {
		RepaymentPlanForm form = new RepaymentPlanForm();
		form.setBorrowCode("2016041838070801");
		PageInfo<RepaymentPlan> info = planService.findAll(form);
		List<RepaymentPlan> list = info.getList();
		Assert.assertNotEquals(0, list.size());
	}

}

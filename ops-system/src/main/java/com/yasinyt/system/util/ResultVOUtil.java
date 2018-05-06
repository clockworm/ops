package com.yasinyt.system.util;

import com.yasinyt.admin.web.vo.ResultVO;
import com.yasinyt.system.enums.ResultEnum;

/**
 * @author TangLingYun
 * @describe 前端交互对象
 */
public class ResultVOUtil {

	/** 请求成功 */
	public static ResultVO<?> success(Object object) {
		ResultVO<Object> resultVO = new ResultVO<>();
		resultVO.setCode(0);
		resultVO.setMessage("成功");
		resultVO.setData(object);
		return resultVO;
	}

	/** 请求成功 */
	public static ResultVO<?> success() {
		return success(null);
	}

	/** 请求失败 */
	public static ResultVO<?> error(Integer code, String message) {
		ResultVO<?> resultVO = new ResultVO<>();
		resultVO.setCode(code);
		resultVO.setMessage(message);
		resultVO.setData(null);
		return resultVO;
	}
	
	/** 请求失败 */
	public static ResultVO<?> error(ResultEnum e) {
		ResultVO<?> resultVO = new ResultVO<>();
		resultVO.setCode(e.getCode());
		resultVO.setMessage(e.getMessage());
		resultVO.setData(null);
		return resultVO;
	}

	/** 请求错误 */
	public static ResultVO<?> fail(String message) {
		ResultVO<?> resultVO = new ResultVO<>();
		resultVO.setCode(1);
		resultVO.setMessage(message);
		resultVO.setData(null);
		return resultVO;
	}
}
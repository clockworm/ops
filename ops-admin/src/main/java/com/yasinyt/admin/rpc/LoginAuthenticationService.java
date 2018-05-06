package com.yasinyt.admin.rpc;

import com.yasinyt.admin.entity.User;
import com.yasinyt.admin.web.form.UserForm;
import com.yasinyt.admin.web.vo.ResultVO;

public interface LoginAuthenticationService {
	
	/**登录认证RPC接口 返回token令牌 */
	ResultVO<?> loginAuthentication(UserForm userForm);
	
	/**获取当前用户UserSession*/
	ResultVO<?> userSession(String token);
	
	/**退出全局系统*/
	void logout(User user,String token);
}

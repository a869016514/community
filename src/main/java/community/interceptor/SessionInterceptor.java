package community.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import community.mapper.UserMapper;
import community.model.User;

@Component
public class SessionInterceptor implements HandlerInterceptor {
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception { 
	 
		Cookie[] cookies=request.getCookies();
		User user=null;
		if(cookies!=null && cookies.length != 0) {
		 for (Cookie cookie :cookies) {
			if(cookie.getName().equals("token")) {
				String token=cookie.getValue();
				 user=userMapper.findByToken(token);
				if(user!=null) {
					request.getSession().setAttribute("user", user);
				}
				break;
			}
		 }
	   }	
		return true;
	}
}

package community.interceptor;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import community.mapper.UserMapper;
import community.model.User;
import community.model.UserExample;

@Component
public class SessionInterceptor implements HandlerInterceptor {
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception { 
	 
		Cookie[] cookies=request.getCookies();
		if(cookies!=null && cookies.length != 0) {
		 for (Cookie cookie :cookies) {
			if(cookie.getName().equals("token")) {
				String token=cookie.getValue();
				UserExample example=new UserExample();
				example.createCriteria().andTokenEqualTo(token);
				List<User> users=userMapper.selectByExample(example);
				if(users.size()!=0) {
					request.getSession().setAttribute("user", users.get(0));
				}
				break;
			}
		 }
	   }	
		return true;
	}
}

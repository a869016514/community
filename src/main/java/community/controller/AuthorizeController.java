package community.controller;

import java.util.UUID;

import javax.servlet.http.Cookie; 
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import community.dto.AccessTokenDTO;
import community.dto.GithubUser;
import community.mapper.UserMapper;
import community.model.User;
import community.provider.GithubProvider;

@Controller
public class AuthorizeController {
	@Autowired
	private GithubProvider githubProvider;
	
	@Value("${github.client.id}")
	private String clientId;
	@Value("${github.client.secret}")
	private String clientSecret;
	@Value("${github.redirect.uri}")
	private String clientRedirectUri;
	
	
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping("/callback")
	public String callback(@RequestParam(name="code") String code
			,@RequestParam(name="state") String state, 
			HttpServletResponse response) {
		//1- 使用github 登陆
		AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setClient_secret(clientSecret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(clientRedirectUri);
		accessTokenDTO.setState(state);
		String accessToken =githubProvider.getAccessToken(accessTokenDTO);
		GithubUser githubUser=githubProvider.getUser(accessToken);
		System.out.println(githubUser.getAvatar_url());
		 //2 持久化登陆
		if(githubUser!=null) {
			//随机生成一个token 用来作为辨认
			String token = UUID.randomUUID().toString() ;
			User user=new User(); 
			user.setName(githubUser.getName());
			user.setAccountId(String.valueOf(githubUser.getId()));
			user.setGmtCreate(System.currentTimeMillis());
 			user.setToken(token);
			user.setAvatarUrl(githubUser.getAvatar_url());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(user);   //用户的登陆信息插入数据库			 
			//把token放入cookie
			response.addCookie(new Cookie("token", token)); 
			return "redirect:/"; //重定向
		}else {
			//登陆失败
			return "redirect:/"; //重定向
		} 
	}
}
package community.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import community.dto.AccessTokenDTO;
import community.dto.GithubUser;
import community.mapper.UserMaapper;
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
	private UserMaapper userMapper;
	
	@GetMapping("/callback")
	public String callback(@RequestParam(name="code") String code
			,@RequestParam(name="state") String state,
			HttpServletRequest request) {
		AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setClient_secret(clientSecret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(clientRedirectUri);
		accessTokenDTO.setState(state);
		String accessToken =githubProvider.getAccessToken(accessTokenDTO);
		GithubUser githubUser=githubProvider.getUser(accessToken);
		 
		if(githubUser!=null) {
			User user=new User(); 
			user.setName(githubUser.getName());
			user.setAccountId(String.valueOf(githubUser.getId()));
			user.setGmtCreate(System.currentTimeMillis());
			user.setToken(UUID.randomUUID().toString());
			userMapper.insert(user);
			request.getSession().setAttribute("user", githubUser);
			//登陆成功，写cookie，session
			return "redirect:/"; //重定向
		}else {
			//登陆失败
			return "redirect:/"; //重定向
		} 
	}
}

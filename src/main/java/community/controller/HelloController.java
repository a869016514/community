package community.controller;
 

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import community.dto.QuestionDTO;
import community.mapper.QuestionMapper;
import community.mapper.UserMapper;
import community.model.Question;
import community.model.User;
import community.service.QuestionService;

@Controller
public class HelloController {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/")
	public String index(HttpServletRequest request,
			Model model) {
		Cookie[] cookies=request.getCookies();
		if(cookies!=null && cookies.length != 0) {
		 for (Cookie cookie :cookies) {
			if(cookie.getName().equals("token")) {
				String token=cookie.getValue();
				User user=userMapper.findByToken(token);
				if(user!=null) {
					request.getSession().setAttribute("user", user);
				}
				break;
			}
		 }
	   }
		
		List <QuestionDTO> questionList =questionService.getQuestionList();
		model.addAttribute("questions",questionList);
		return "index";
	}
}

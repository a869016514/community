package community.controller;
 

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import community.dto.PaginationDTO;
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
			Model model,
			@RequestParam(name="page",defaultValue = "1") Integer page,
			@RequestParam(name="size",defaultValue = "5") Integer size ) {
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
		if(user==null) {
			return"index"; 
		}
		
		PaginationDTO paginationDTO =questionService.getQuestionList(page,size);
		model.addAttribute("pagination",paginationDTO);
		return "index";
	}
}

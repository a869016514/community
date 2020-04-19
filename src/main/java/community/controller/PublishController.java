package community.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import community.mapper.QuestionMapper;
import community.mapper.UserMapper;
import community.model.Question;
import community.model.User;

@Controller
public class PublishController {
	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping("/publish")
	public String  publish() {
		return"publish";
	}
	
	@PostMapping("/publish")
	public String doPublic( @RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam("tag") String tag,
			HttpServletRequest request,
			Model model
			) {
		
		User user=null;
		Cookie[] cookies=request.getCookies();
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
			model.addAttribute("error","用户未登录");
			return "publish";
		}
		
		Question question =new Question();
		question.setTag(tag);
		question.setTitle(title);
		question.setDescription(description);
		question.setCreator(user.getAccountId());
		question.setGmtCreate(System.currentTimeMillis());
		question.setGmtModified(question.getGmtCreate());
		questionMapper.create(question);
		return "redirect:/";
	}
	 
}

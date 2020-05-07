package community.controller;

 
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import community.dto.NotificationDTO;
import community.dto.PaginationDTO;
import community.dto.QuestionDTO;
import community.model.Question;
import community.model.User;
import community.service.NotificationService;
import community.service.QuestionService;

@Controller
public class ProfileController {
 
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private NotificationService notifycationService;
	@GetMapping("/profile/{action}")
	public String profile(@PathVariable(name="action") String action,
			HttpServletRequest request,
			Model model,
			@RequestParam(name="page",defaultValue = "1") Integer page,
			@RequestParam(name="size",defaultValue = "5") Integer size) {
  		User user=(User)request.getSession().getAttribute("user");
		if(user==null) {
			return "redirect:/";
		}
		
		if("questions".equals(action)) {
			model.addAttribute("section","questions");
			model.addAttribute("sectionName","我的提问");  
			PaginationDTO<QuestionDTO> paginationDTO = questionService.getMyQuestionList(user.getAccountId(),page,size);
			model.addAttribute("pagination",paginationDTO);
		}else if("replies".equals(action)) {
			PaginationDTO<NotificationDTO> paginationDTO = notifycationService.list(user.getAccountId(),page,size);
			model.addAttribute("pagination",paginationDTO);
			model.addAttribute("section","replies");
			model.addAttribute("sectionName","我的回复");  
		}
		
		return"profile";
	}
}
package community.controller;
 
 
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import community.dto.PaginationDTO;
 
import community.service.QuestionService;

@Controller
public class HelloController {
 
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/")
	public String index(HttpServletRequest request,
			Model model,
			@RequestParam(name="page",defaultValue = "1") Integer page,
			@RequestParam(name="size",defaultValue = "5") Integer size,
			@RequestParam(name="search",required = false) String search)throws Exception {
		PaginationDTO paginationDTO =questionService.getQuestionList(search,page,size);
		if(StringUtils.isEmpty(search)) { 
			model.addAttribute("pagination",paginationDTO);
			return "index";
		}else {
			model.addAttribute("pagination",paginationDTO);
			model.addAttribute("search",search);
			return"search";
		}
	
	
	}
}

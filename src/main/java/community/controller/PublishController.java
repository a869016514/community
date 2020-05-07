package community.controller;
 
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import community.dto.QuestionDTO;
import community.mapper.QuestionMapper;

import community.model.Question;
import community.model.User;
import community.service.QuestionService;

@Controller
public class PublishController {
 
	@Autowired
	private QuestionService questionService;
	@GetMapping("/publish/{id}")
   public String edit(@PathVariable(name="id") Integer id,
		    Model model) {
		QuestionDTO questionDTO= questionService.getQuestionById(id);
		model.addAttribute("title", questionDTO.getTitle());
		model.addAttribute("description", questionDTO.getDescription());
		model.addAttribute("tag", questionDTO.getTag());
		model.addAttribute("id", questionDTO.getId());	 
		return "publish";
   }
	
	@GetMapping("/publish")
	public String  publish() {
		return"publish";
	}
	
	@PostMapping("/publish")
	public String doPublic( @RequestParam(value="title" ,required=false) String title,
			@RequestParam(value="description" ,required=false) String description,
			@RequestParam(value="tag" ,required=false) String tag,
			@RequestParam(value="id", required=false) Integer id,
			HttpServletRequest request,
			Model model
			) {
		model.addAttribute("title", title);
		model.addAttribute("tag", tag);
		model.addAttribute("description", description); 
		User user=(User)request.getSession().getAttribute("user"); 
		if(user==null) {
			model.addAttribute("error","用户未登录");
			return "publish";
		} 
		if(StringUtils.isBlank(title) || StringUtils.isBlank(description) ||StringUtils.isBlank(tag) ) {
			model.addAttribute("error",  "输入不正确，标题、内容、标签不能为空");
			return "publish";
		}
		Question question =new Question();
		question.setId(id);
		question.setTag(tag);
		question.setTitle(title);
		question.setViewCount(0);
		question.setCommentCount(0);
		question.setLikeCount(0);
		question.setDescription(description);
		question.setCreator(user.getAccountId());
		question.setGmtCreate(System.currentTimeMillis());
		question.setGmtModified(question.getGmtCreate());
		questionService.createOrUpdate(question); 
		return "redirect:/";
	}
	 
}

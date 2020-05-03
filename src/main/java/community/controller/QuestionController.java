package community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import community.dto.QueryCommentDTO;
import community.dto.QuestionDTO;
import community.service.CommentService;
import community.service.QuestionService;

@Controller
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private CommentService commentService;
	@GetMapping("/question/{id}")
	public String question(@PathVariable(name="id") Integer id,
			Model model) { 
		QuestionDTO questionDTO= questionService.getQuestionById(id);
		questionService.incView(id);
		List<QueryCommentDTO> queryCommentDTOs=commentService.listByQuestionId(id);
		model.addAttribute("question", questionDTO);
		model.addAttribute("comments", queryCommentDTOs);
		return "question";
	}
	
}

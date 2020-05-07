package community.controller;

 

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import community.dto.CommentDTO;
import community.dto.QueryCommentDTO;
import community.dto.ResultDTO;
import community.enums.CommentTypeEnum;
import community.exception.CustomizErrorCode;
import community.model.Comment;
import community.model.User;
import community.service.CommentService;

@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	//一级评论
	@ResponseBody
	@RequestMapping(value="/comment",method=RequestMethod.POST)
	public Object post(@RequestBody CommentDTO commentDTO,
			HttpServletRequest request) {
		User user= (User) request.getSession().getAttribute("user");
		if(user == null) {
			return ResultDTO.errorOf(CustomizErrorCode.NOT_LOGIN);
		}
		if(commentDTO == null || StringUtils.isBlank(commentDTO.getContent())) {
			return ResultDTO.errorOf(CustomizErrorCode.COMMENT_IS_EMPTY);
		}
		
		Comment comment=new Comment();
		comment.setParentId(commentDTO.getParentId());
		comment.setContent(commentDTO.getContent());
		comment.setType(commentDTO.getType());
		comment.setGmtCreate(System.currentTimeMillis());
		comment.setGmtModified(System.currentTimeMillis());
		comment.setCommentator(user.getAccountId());
		comment.setLikeCount(1L);
		comment.setCountComment(0);
		comment.setViewCount(0);
		commentService.insert(comment,user); 
		return ResultDTO.okOf();
	}
	
	//二级评论
	@ResponseBody
	@RequestMapping(value="/comment/{id}",method=RequestMethod.GET)
	public ResultDTO comments(@PathVariable(name="id") Integer id) { 
		List<QueryCommentDTO> commentDTO=commentService.listByTargerId(id,CommentTypeEnum.COMMENT);
 		return ResultDTO.okOf(commentDTO);
	}
}

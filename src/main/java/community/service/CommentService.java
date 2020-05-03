package community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import community.dto.CommentDTO;
import community.dto.QueryCommentDTO;
import community.enums.CommentTypeEnum;
import community.exception.CustomizErrorCode;
import community.exception.CustomizeException;
import community.mapper.CommentMapper;
import community.mapper.QuestionMapper;
import community.mapper.QuestionMapperExt;
import community.mapper.UserMapper;
import community.model.Comment;
import community.model.CommentExample;
import community.model.Question;
import community.model.User;
import community.model.UserExample;

@Service
public class CommentService {
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private QuestionMapperExt questionMapperExt;
	@Autowired
	private UserMapper userMapper;
	/**
	 * 插入回复
	 * */
	@Transactional
	public void insert(Comment comment) {
		if (comment.getParentId() == null || comment.getParentId() == 0) {
			throw new CustomizeException(CustomizErrorCode.TARGET_PARAM_NOT_FOUND);
		}

		if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
			throw new CustomizeException(CustomizErrorCode.TYPE_PARAM_WRONG);
		}

		if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
			// 回复评论
			Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
			if (dbComment == null) {
				throw new CustomizeException(CustomizErrorCode.COMMENT_NOT_FOUND);
			}

			commentMapper.insert(comment);
		} else {
			// 回复问题
			Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizErrorCode.QUESTION_NOT_FOUND);
			}
			commentMapper.insert(comment);
			question.setCommentCount(1);
			questionMapperExt.incCommentCount(question);
		}
	
	}

	/**
	 * 通过前端给的id
	 * 查询回复 
	 * 返回给前端展示
	 * */
	public List<QueryCommentDTO> listByQuestionId(Integer id) { 
		CommentExample example=new CommentExample();
		example.createCriteria()
		.andParentIdEqualTo(id)
		.andTypeEqualTo(CommentTypeEnum.QUESTION.getType()); 
		List<Comment> comments=commentMapper.selectByExample(example);
		if(comments.size()==0) {
			return new ArrayList<>();
		}
		//获取去重的评论人
	    Set<String>commentators=comments.stream().map(comment->comment.getCommentator()).collect(Collectors.toSet());
		List<String> userIds=new ArrayList<String>();
		userIds.addAll(commentators);
		//获取评论人并转化为map
		UserExample userExample=new UserExample();
		userExample.createCriteria()
		.andAccountIdIn(userIds); 
		List<User> users=userMapper.selectByExample(userExample);
		Map<String, User>userMap= users.stream().collect(Collectors.toMap(user ->user.getAccountId(),user->user));
		//转换comment为commentDTO
		List<QueryCommentDTO> queryCommentDTOs= comments.stream().map(comment ->{
			QueryCommentDTO queryCommentDTO = new QueryCommentDTO();
			BeanUtils.copyProperties(comment,queryCommentDTO);
			queryCommentDTO.setUser(userMap.get(comment.getCommentator()));
			return queryCommentDTO;
		}).collect(Collectors.toList());
		return queryCommentDTOs;
	}
	
}

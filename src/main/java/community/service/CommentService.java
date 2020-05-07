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
 
import community.dto.QueryCommentDTO;
import community.enums.CommentTypeEnum;
import community.enums.NotificationEnum;
import community.enums.NotificationStatusEnum;
import community.exception.CustomizErrorCode;
import community.exception.CustomizeException;
import community.mapper.CommentMapper;
import community.mapper.CommentMapperExt;
import community.mapper.NotificationMapper;
import community.mapper.QuestionMapper;
import community.mapper.QuestionMapperExt;
import community.mapper.UserMapper;
import community.model.Comment;
import community.model.CommentExample;
import community.model.Notification;
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
	@Autowired
	private CommentMapperExt commentMapperExt;
	@Autowired
	private NotificationMapper notificationMapper;
	/**
	 * 插入回复
	 * @param commentator 
	 * */
	@Transactional
	public void insert(Comment comment, User commentator) {
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
			
			Question queryQuestion = questionMapper.selectByPrimaryKey(dbComment.getParentId());
			if (queryQuestion == null) {
				throw new CustomizeException(CustomizErrorCode.QUESTION_NOT_FOUND);
			}
			//comment的评论数+1
			commentMapper.insert(comment);
			comment.setCountComment(1);
			commentMapperExt.incCountComment(comment);
			//问题的评论数+1
			Question question=new Question();
			question.setCommentCount(1);
			question.setId(dbComment.getParentId());
			questionMapperExt.incCommentCount(question);
			//创建通知
			createNotify(comment,dbComment.getCommentator(),commentator.getName(),question.getId(),queryQuestion.getTitle(),NotificationEnum.REPLY_COMMENT);
		} else {
			// 回复问题
			CommentExample example=new CommentExample();
			Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizErrorCode.QUESTION_NOT_FOUND);
			}
			//插入评论
			commentMapper.insert(comment);
			//更新question的评论数
			example.createCriteria().andParentIdEqualTo(question.getId()).andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
 			question.setCommentCount(1); 
			questionMapperExt.incCommentCount(question); 
			//通知
			createNotify(comment,question.getCreator(),commentator.getName(),question.getId(),question.getTitle(),NotificationEnum.REPLY_QUESTION);
			} 
		
	
	}
	/**
	 * 创建通知
	 * @param notifierName 
	 * @param title 
	 * 
	 * */
	private void createNotify(Comment comment,String receiver,String notifierName,Integer outerId, String outerTitle, NotificationEnum notificationType) {
		Notification notification =new Notification();
		notification.setGmtCreate(System.currentTimeMillis());
		notification.setType(notificationType.getType());
		notification.setOuterId(outerId);
		notification.setNotifier(comment.getCommentator());
		notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
		notification.setReceiver(receiver); 
		notification.setNotifierName(notifierName);
		notification.setOuterTitle(outerTitle);
		notificationMapper.insert(notification);
	}

	/**
	 * 通过前端给的id
	 * 查询回复 
	 * 返回给前端展示
	 * */
	public List<QueryCommentDTO> listByTargerId(Integer id,CommentTypeEnum type) { 
		CommentExample example=new CommentExample();
		example.createCriteria()
		.andParentIdEqualTo(id)
		.andTypeEqualTo(type.getType());   //CommentTypeEnum.QUESTION.getType()
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

package community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import community.dto.PaginationDTO;
import community.dto.QuestionDTO;
import community.mapper.QuestionMapper;
import community.mapper.UserMapper;
import community.model.Question;
import community.model.User;

@Service
public class QuestionService  {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private QuestionMapper questionMapper;
	
	public PaginationDTO getQuestionList(Integer page,Integer size) {
		PaginationDTO paginationDTOList=new PaginationDTO();
		Integer totalCount=questionMapper.count();
 		paginationDTOList.setPagination(totalCount,page,size);
		if(page<1) {
			page=1;
		}
		if(page>paginationDTOList.getTotalPage()) {
			page=paginationDTOList.getTotalPage();
		}
		
	    //size*(page-1)
		//导航条显示第几页到第几页 如果page=1  导航条显示 1-5页
		Integer offset=size*(page-1); 
		List<Question> questions=questionMapper.getQuestionList(offset,size);
		List<QuestionDTO> questionDTOList=new ArrayList<QuestionDTO>();
		
	
		
		for(Question q:questions) {
			User user=userMapper.findByID(q.getCreator());
			QuestionDTO qd=new QuestionDTO();
			BeanUtils.copyProperties(q, qd);
			qd.setUser(user);
			questionDTOList.add(qd);
		}
		paginationDTOList.setQuestions(questionDTOList);


		return paginationDTOList;
	}

	public PaginationDTO getMyQuestionList(String creator, Integer page, Integer size) {
		PaginationDTO paginationDTOList=new PaginationDTO();
		Integer totalCount=questionMapper.countByUserId(creator);
 		paginationDTOList.setPagination(totalCount,page,size);
		if(page<1) {
			page=1;
		}
		if(page>paginationDTOList.getTotalPage()) {
			page=paginationDTOList.getTotalPage();
		}
		
	    //size*(page-1)
		//导航条显示第几页到第几页 如果page=1  导航条显示 1-5页
		Integer offset=size*(page-1); 
		List<Question> questions=questionMapper.getQuestionListById(creator,offset,size);
		List<QuestionDTO> questionDTOList=new ArrayList<QuestionDTO>();
		
	
		
		for(Question q:questions) {
			User user=userMapper.findByID(q.getCreator());
			QuestionDTO qd=new QuestionDTO();
			BeanUtils.copyProperties(q, qd);
			qd.setUser(user);
			questionDTOList.add(qd);
		}
		paginationDTOList.setQuestions(questionDTOList);


		return paginationDTOList;
		
	}

	public QuestionDTO getQuestionById(Integer id) {
		Question  question  =questionMapper.getQuestionById(id);
		QuestionDTO questionDTO =new QuestionDTO();
		User user=userMapper.findByID(question.getCreator());
		BeanUtils.copyProperties(question, questionDTO);
		questionDTO.setUser(user);
		return questionDTO;
	}
	
	
	public void createOrUpdate(Question question) {
		Question q= questionMapper.getQuestionById(question.getId());
		if(q == null) {
			questionMapper.create(question);
		}else {
			question.setGmtModified(System.currentTimeMillis());
			questionMapper.update(question);
		}
	}
	
}
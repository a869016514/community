package community.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import community.dto.PaginationDTO;
import community.dto.QuestionDTO;
import community.mapper.QuestionMapper;
import community.mapper.UserMapper;
import community.model.Question;
import community.model.QuestionExample;
import community.model.User;
import community.model.UserExample;

@Service
public class QuestionService  {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private QuestionMapper questionMapper;
	
	public PaginationDTO getQuestionList(Integer page,Integer size) {
		PaginationDTO paginationDTOList=new PaginationDTO();
		Integer totalCount=(int)questionMapper.countByExample(null);
 		paginationDTOList.setPagination(totalCount,page,size);
		if(page<1) {
			page=1;
		}
		if(page>paginationDTOList.getTotalPage()) {
			page=paginationDTOList.getTotalPage();
		}
		
	    //size*(page-1)
		//导航条显示第几页到第几页 如果page=1  导航条显示 1-5页
		Integer offset=size*(page-1);// 
		List<Question> questions =questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
		List<QuestionDTO> questionDTOList=new ArrayList<QuestionDTO>();
		UserExample example=new UserExample();	
		for(Question q:questions) {
			example.createCriteria().andAccountIdEqualTo(q.getCreator());
			List<User> users=userMapper.selectByExample(example);
			QuestionDTO qd=new QuestionDTO();
			BeanUtils.copyProperties(q, qd);
			qd.setUser(users.get(0));
			questionDTOList.add(qd);
		}
		paginationDTOList.setQuestions(questionDTOList);


		return paginationDTOList;
	}

	public PaginationDTO getMyQuestionList(String creator, Integer page, Integer size) {
		PaginationDTO paginationDTOList=new PaginationDTO();
		QuestionExample questionExample=new QuestionExample();
		UserExample example=new UserExample();
		questionExample.createCriteria().andCreatorEqualTo(creator);	
		Integer totalCount= (int)questionMapper.countByExample(questionExample);
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
		QuestionExample pageExample= new QuestionExample() ;
		pageExample.createCriteria().andCreatorEqualTo(creator);
		List<Question> questions=questionMapper.selectByExampleWithRowbounds(pageExample, new RowBounds(offset, size));
		List<QuestionDTO> questionDTOList=new ArrayList<QuestionDTO>();	
		for(Question q:questions) {
			example.createCriteria().andAccountIdEqualTo(q.getCreator());
			List<User> users=userMapper.selectByExample(example);
			QuestionDTO qd=new QuestionDTO();
			BeanUtils.copyProperties(q, qd);
			qd.setUser(users.get(0));
			questionDTOList.add(qd);
		}
		paginationDTOList.setQuestions(questionDTOList);


		return paginationDTOList;
		
	}

	public QuestionDTO getQuestionById(Integer id) {
		UserExample example=new UserExample();
		Question  question  =questionMapper.selectByPrimaryKey(id);
		QuestionDTO questionDTO =new QuestionDTO();
		example.createCriteria().andAccountIdEqualTo(question.getCreator());
		List<User> users=userMapper.selectByExample(example);
		BeanUtils.copyProperties(question, questionDTO);
		questionDTO.setUser(users.get(0));
		return questionDTO;
	}
	
	
	public void createOrUpdate(Question question) {
		Question q= questionMapper.selectByPrimaryKey(question.getId());
		if(q == null) {
			questionMapper.insert(question);
		}else {
			question.setGmtModified(System.currentTimeMillis());
			questionMapper.updateByPrimaryKey(question);
		}
	}
	
}
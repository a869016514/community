package community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<QuestionDTO> getQuestionList() { 
		List<Question> questions=questionMapper.getQuestionList();
		List<QuestionDTO> questionDTOList=new ArrayList<QuestionDTO>();
		for(Question q:questions) {
			User user=userMapper.findByID(q.getCreator());
			QuestionDTO qd=new QuestionDTO();
			BeanUtils.copyProperties(q, qd);
			qd.setUser(user);
			questionDTOList.add(qd);
		}
		return questionDTOList;
	}
	
	
}

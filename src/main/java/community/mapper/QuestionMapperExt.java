package community.mapper;

import java.util.List;

import community.dto.QuestionQueryDTO;
import community.model.Question;

public interface QuestionMapperExt {
	
	void incView(Question record);
	int incCommentCount(Question record);
	List<Question> selectRelated(Question question);
	Integer countBySearch(QuestionQueryDTO questionQueryDTO);
	List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}

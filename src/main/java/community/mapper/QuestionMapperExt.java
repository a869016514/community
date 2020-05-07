package community.mapper;

import java.util.List;

import community.model.Question;

public interface QuestionMapperExt {
	
	void incView(Question record);
	int incCommentCount(Question record);
	List<Question> selectRelated(Question question);
}

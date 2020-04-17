package community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import community.model.Question;

@Mapper
public interface QuestionMapper {
	
	@Insert("INSERT INTO question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag}) ")
	void create(Question question);

	@Select("select * from question")
	List<Question> getQuestionList();
}

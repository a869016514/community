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

	@Select("select * from question limit #{offset},#{size}")
	List<Question> getQuestionList(Integer offset, Integer size);
	
	@Select("SELECT COUNT(1) FROM question")
	Integer count();
	
	@Select("select * from question where creator=#{userId} limit #{offset},#{size}")
	List<Question> getQuestionListById(String userId, Integer offset, Integer size);
	
	@Select("SELECT COUNT(1) FROM question where creator=#{userId} ")
	Integer countByUserId(String userId);
 
}
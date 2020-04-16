package community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper; 

import community.model.User;

@Mapper
public interface UserMaapper {
	 @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified)values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
	  void insert(User user);  
	   

}

package community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import community.model.User;

@Mapper
public interface UserMapper {
	  @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,AVATAR_URL)values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
	  void insert(User user);  
	  
	  @Select("select * from user where token=#{token}")
	 User findByToken(@Param("token") String token);

	  @Select("select * from user where account_id=#{accountId}")
	  User findByID(String accountId);
	  @Select("select count(1) from user where account_id=#{accountId}")
	  int findNumByAccountId(String accountId);
	  @Update("update user set token=#{token},name=#{name},gmt_modified=#{gmtModified},avatar_Url=#{avatarUrl} where account_id=#{accountId}")
	void updateUser(String token, String name, Long gmtModified, String avatarUrl,String accountId);
}

package community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import community.mapper.UserMapper;
import community.model.User;
import community.model.UserExample;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public void createOrUpdate(User user) {
		UserExample example =new UserExample();
		example.createCriteria().andAccountIdEqualTo(user.getAccountId());
		 List<User> users=userMapper.selectByExample(example); 	 
		 if( users.size()==0) {
			 userMapper.insert(user);
		 }else {
		      User dbUser=users.get(0);
		      User updateUser=new User();
		      updateUser.setGmtModified(System.currentTimeMillis());
		      updateUser.setToken(dbUser.getToken());
		      updateUser.setName(dbUser.getName());
		      updateUser.setAvatarUrl(dbUser.getAvatarUrl());
		      UserExample example2=new UserExample();
		      example.createCriteria().andIdEqualTo(dbUser.getId());
		      userMapper.updateByExampleSelective(updateUser, example2);
		 }
		
	}
  
	 
	
	
}

package community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import community.mapper.UserMapper;
import community.model.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public void createOrUpdate(User user) {
		int num=userMapper.findNumByAccountId(user.getAccountId());
		 if( num==0) {
			 userMapper.insert(user);
		 }else {
				userMapper.updateUser(user.getToken(), user.getName(), user.getGmtModified(), user.getAvatarUrl(),user.getAccountId()); 
		 }
		
	}
  
	 
	
	
}

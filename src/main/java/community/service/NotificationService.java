package community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors; 
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import community.dto.NotificationDTO;
import community.dto.PaginationDTO;
import community.enums.NotificationEnum;
import community.enums.NotificationStatusEnum;
import community.exception.CustomizErrorCode;
import community.exception.CustomizeException;
import community.mapper.NotificationMapper;
import community.mapper.UserMapper;
import community.model.Notification;
import community.model.NotificationExample;
import community.model.User;
import community.model.UserExample;

@Service
public class NotificationService {

	@Autowired
	private NotificationMapper notifycationMapper;
 
	
	public PaginationDTO<NotificationDTO> list(String accountId, Integer page, Integer size) {
		PaginationDTO<NotificationDTO> paginationDTO=new PaginationDTO<>();
		NotificationExample notificationExample=new NotificationExample(); 
		notificationExample.createCriteria().andReceiverEqualTo(accountId);
		Integer totalCount= (int)notifycationMapper.countByExample(notificationExample);
		Integer totalPage = 0;
		
		if(totalCount % size == 0) {
			totalPage = totalCount / size;
		}else {
			totalPage = totalCount / size + 1;
		} 
		
		if(page<1) {
			page = 1;
		}
		
		if(page  > totalPage) {
			page = totalPage;
		}
		paginationDTO.setPagination(totalCount,page,size);
	    //size*(page-1)
		//导航条显示第几页到第几页 如果page=1  导航条显示 1-5页
		Integer offset=size*(page-1); 
		NotificationExample Notifiexample= new NotificationExample() ;
		Notifiexample.createCriteria().andReceiverEqualTo(accountId);
		Notifiexample.setOrderByClause("gmt_create desc");
		
		List<Notification> notifycations=notifycationMapper.selectByExampleWithRowbounds(Notifiexample, new RowBounds(offset, size));
		if(notifycations.size() == 0) {
			return paginationDTO;
		}
		List<NotificationDTO> notificationDTOs=new ArrayList<>();
		for(Notification notification:notifycations) {
			NotificationDTO notificationDTO=new NotificationDTO();
			BeanUtils.copyProperties(notification, notificationDTO);
			notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
			notificationDTOs.add(notificationDTO);
		}
		
			
		paginationDTO.setData(notificationDTOs);
 
		return paginationDTO;
		
	}



	
	public Long unreadCount(String accountId) {
		NotificationExample example=new NotificationExample();
		example.createCriteria().andReceiverEqualTo(accountId)
		.andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
		
		return notifycationMapper.countByExample(example);  
	}




	public NotificationDTO read(Long id, User user) {
		Notification notification=notifycationMapper.selectByPrimaryKey(id);
		if(notification == null) {
			throw new CustomizeException(CustomizErrorCode.NOTIFICATION_NOT_FOUND);
		}
		if(!notification.getReceiver().equals(user.getAccountId()) ) {
			throw new CustomizeException(CustomizErrorCode.READ_NOTIFICATION_FAIL);
		}
		//更新已读
		notification.setStatus(NotificationStatusEnum.READ.getStatus());
		notifycationMapper.updateByPrimaryKey(notification);
		
		NotificationDTO notificationDTO=new NotificationDTO();
		BeanUtils.copyProperties(notification, notificationDTO);
		notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType())); 
		notificationDTO.setOuterid(notification.getOuterId());
		return notificationDTO;
	}
	
}

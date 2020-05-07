package community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 
import community.dto.NotificationDTO; 
import community.enums.NotificationEnum; 
import community.model.User;
import community.service.NotificationService; 

@Controller
public class NotificationController {

 
	@Autowired
	private NotificationService notifycationService;
	@GetMapping("/notification/{id}")
	public String profile(@PathVariable(name="id") Long id ,
			HttpServletRequest request) {
		
  		User user=(User)request.getSession().getAttribute("user");
		if(user==null) {
			return "redirect:/";
		} 
		NotificationDTO notificationDTO= notifycationService.read(id,user);
		if(NotificationEnum.REPLY_COMMENT.getType() == notificationDTO.getType() ||
				NotificationEnum.REPLY_QUESTION.getType() == notificationDTO.getType()	) {
			return "redirect:/question/"+ notificationDTO.getOuterid();
		}else {
			return "redirect:/";
		}
	}
}

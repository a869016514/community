package community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import community.dto.FileDTO;
import community.provider.AliProvider;

@Controller
public class FileController {
	
	@Autowired
	private AliProvider aliProvider;
	
	
	@RequestMapping("/file/upload")
	@ResponseBody
   public FileDTO upload(HttpServletRequest request  ) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file=multipartRequest.getFile("editormd-image-file");  
		try {
			aliProvider.init();
			String name = aliProvider.uploadImg2Oss(file);
			String url = aliProvider.getImgUrl(name); 
			FileDTO fileDTO=new FileDTO();
			fileDTO.setSuccess(1);
			fileDTO.setUrl(url);
			return fileDTO;
		} catch (Exception e) { 
			e.printStackTrace();
			return null;
		}

	   
   }
    
}

package community.advice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import community.dto.ResultDTO;
import community.exception.CustomizErrorCode;
import community.exception.CustomizeException;

@ControllerAdvice
public class CustomizeExceptionHandle {

	@ExceptionHandler(Exception.class)
	ModelAndView handle(HttpServletRequest request, 
			Throwable e, Model model,
			HttpServletResponse response
			) { 
		String contenType=request.getContentType();
		if("application/json".equals(contenType)) {
			//返回json
			ResultDTO resultDTO;
			if(e instanceof CustomizeException) {
				 resultDTO = ResultDTO.errorOf((CustomizeException) e);
			}else {
				resultDTO =ResultDTO.errorOf(CustomizErrorCode.SYS_ERROR);
			}			
			try {
				response.setContentType("application/json");
				response.setStatus(200);
				response.setCharacterEncoding("utf-8");
				PrintWriter writer= response.getWriter();
				writer.write(JSON.toJSONString(resultDTO));
				writer.close();
			}catch(IOException ex) {
				
			}
			return null;
	
		}else {
			//错误页面跳转
			if(e instanceof CustomizeException) {
				model.addAttribute("message",e.getMessage());
			}else {
				model.addAttribute("message",CustomizErrorCode.SYS_ERROR.getMessage());
			}			
			return new ModelAndView("error");
		}
		
	
	}
	
	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode=(Integer) request.getAttribute("javax.server.error.status_code");
		if(statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.valueOf(statusCode);
	}
}

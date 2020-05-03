package community.dto;

import community.exception.CustomizErrorCode;
import community.exception.CustomizeException;

public class ResultDTO {
		private String message;
		private Integer code;
		
		public static ResultDTO errorOf(Integer code , String message) {
			ResultDTO resultDTO=new ResultDTO();
			resultDTO.setCode(code);
			resultDTO.setMessage(message);
			return resultDTO;
		}
		
		public static ResultDTO errorOf(CustomizErrorCode errorCode) {
 
			return errorOf (errorCode.getCode(),errorCode.getMessage());
		}
		
		public static ResultDTO okOf() {
			ResultDTO resultDTO=new ResultDTO();
			resultDTO.setCode(200);
			resultDTO.setMessage("请求成功");
			return resultDTO;
		}
		
		public static ResultDTO errorOf(CustomizeException e) {
			 
			return errorOf(e.getCode(),e.getMessage());
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		
		
		
}

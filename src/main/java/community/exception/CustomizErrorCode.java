package community.exception;

public enum CustomizErrorCode implements ICustomizErrorCode {
		QUESTION_NOT_FOUND(2001,"你找的问题都不在，要不换一个试试"),
		TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
		NOT_LOGIN(2003,"当前操作需要登陆，请登陆后重试"),
		SYS_ERROR(2004,"服务器冒烟了，请骚等"),
		TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
		COMMENT_NOT_FOUND(2006,"回复评论不存在了，要不换个试试"),
		COMMENT_IS_EMPTY(2007,"回复不能为空");	
		
		private String message;
		private Integer code;
		
		
		
		
		private CustomizErrorCode(Integer code , String message) {
			 this.message=message;
			 this.code=code;
		}

		@Override 
		public String getMessage() {
			return message;
		}

		public Integer getCode() { 
			return code;
		}
 
		
		 
}

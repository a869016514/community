package community.exception;

public enum CustomizErrorCode implements ICustomizErrorCode {
		QUESTION_NOT_FOUND("你找的问题都不在，要不换一个试试");	
		
		private String message;
		
		private CustomizErrorCode(String message) {
			 this.message=message;
		}

		@Override
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
		 
}

package community.dto;

import community.model.User;

public class QueryCommentDTO {
	 private Integer id; 
	    private Integer parentId; 
	    private Integer type; 
	    private String commentator; 
	    private Long gmtCreate;  
	    private Long gmtModified; 
	    private Long likeCount; 
	    private String content;
	    private User user;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getParentId() {
			return parentId;
		}
		public void setParentId(Integer parentId) {
			this.parentId = parentId;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public String getCommentator() {
			return commentator;
		}
		public void setCommentator(String commentator) {
			this.commentator = commentator;
		}
		public Long getGmtCreate() {
			return gmtCreate;
		}
		public void setGmtCreate(Long gmtCreate) {
			this.gmtCreate = gmtCreate;
		}
		public Long getGmtModified() {
			return gmtModified;
		}
		public void setGmtModified(Long gmtModified) {
			this.gmtModified = gmtModified;
		}
		public Long getLikeCount() {
			return likeCount;
		}
		public void setLikeCount(Long likeCount) {
			this.likeCount = likeCount;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
	    
}

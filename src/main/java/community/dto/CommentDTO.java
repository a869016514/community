package community.dto;

public class CommentDTO {
	private Integer parentId;
	private String content;
	private Integer type;
	private Integer countComment;
	public Integer getCountComment() {
		return countComment;
	}
	public void setCountComment(Integer countComment) {
		this.countComment = countComment;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}

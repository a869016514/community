package community.model;

public class Comment {
    
    private Integer id;
 
    private Integer parentId;
 
    private Integer type;
 
    private String commentator;
 
    private Long gmtCreate;

     
    private Long gmtModified;

    
    private Long likeCount;

    
    private String content;
 
    private Integer countComment;
 
    private Integer viewCount;
    public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

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
        this.commentator = commentator == null ? null : commentator.trim();
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
        this.content = content == null ? null : content.trim();
    }
 
    public Integer getCountComment() {
        return countComment;
    }
 
    public void setCountComment(Integer countComment) {
        this.countComment = countComment;
    }
}

/**
 * 提交回复
 * **/
function post(){
	var questionId=$("#question_id").val(); 
	var content=$("#comment_content").val();
	comment2Target(questionId,content,1);
}

function comment2Target(targetId,content,type){
	if(!content){
		alert("不能回复空内容哦~~");
		return;
	}
	$.ajax({
		type:"POST",
		url:"/comment",
		contentType:"application/json",
		data:JSON.stringify({
			"parentId":targetId,
			"content":content,
			"type":type
		}),
		success: function(result){
			console.log(result);
			if(result.code==200){
				window.location.reload();
			}else{
				if(result.code == 2003){
					var isAccepted=confirm(result.message);
					if(isAccepted){
						window.open("https://github.com/login/oauth/authorize?client_id=Iv1.2222d53e2c9f073c&redirect_uri=http://localhost:8887/callback&state=1");
						window.localStorage.setItem("closable",true);
					} 
				}else{
				alert(result.message);
				}
			}
		},
		dataType:"json"
	});
	
	console.log(questionId);
	console.log(content);
}
function comment(e){
	var id=e.getAttribute("data-id");
	var content=$("#input-"+id).val();
	comment2Target(id,content,2);
}

/**
 * 展开二级评论
 * 
 * */
function collapseComments(e){
	var id=e.getAttribute("data-id");
	var comments=$("#comment-"+id);
	$(comments).toggleClass("sub collapse in");  
	var flag=$(comments).hasClass("sub"); 
	
	if(flag){
		var subCommentContainer=$("#comment-"+id);
		 if(subCommentContainer.children().length != 1){
			 return;
		 }else{
		 $.getJSON("/comment/" + id, function (data) {
             console.log(data); 
             
             $.each(data.data,function(index,comment){ 
            	 var mediaLeftElement=$("<div/>",{
            		"class":"media-left" 
            	 }).append($("<img/>",{
            		 "class":"media-object avatar img-rounded",
            		 "src":comment.user.avatarUrl
            	 }));
            	 
            	 var mediaBodyElement=$("<div/>",{
            		 "class":"media-body"
            	 }).append($("<div/>",{ 
            		 "class": "media-heading",
                     "html": comment.user.name
                 })).append($("<div/>", {
                     "html": comment.content
                 })).append($("<div/>", {
                     "class": "menu"
                 }).append($("<span/>", {
                     "class": "pull-right",
                     "html":  moment(comment.gmtCreate).format('YYYY-MM-DD')
                 })));
            	 
            	 var mediaElement = $("<div/>",{
            		"class":"media" 
            	 }).append(mediaLeftElement).append(mediaBodyElement);
            	 
            	 var commentElement=$("<div/>",{
            		 "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 question_section", 
            	 }).append(mediaElement);
            	 subCommentContainer.prepend(commentElement);  
             });
	  });
	 }
	}
}

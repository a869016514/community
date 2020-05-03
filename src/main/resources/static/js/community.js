

function post(){
	var questionId=$("#question_id").val(); 
	var content=$("#comment_content").val();
	if(!content){
		alert("不能回复空内容哦~~");
		return;
	}
	$.ajax({
		type:"POST",
		url:"/comment",
		contentType:"application/json",
		data:JSON.stringify({
			"parentId":questionId,
			"content":content,
			"type":1
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


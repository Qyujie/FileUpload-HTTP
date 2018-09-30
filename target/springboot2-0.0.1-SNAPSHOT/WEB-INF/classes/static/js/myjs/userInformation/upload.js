$(function(){
	$("#upload button").click(function(){
		return $("#upload input").click();
	});
	
	$(function(){
		   $("#upload input").change(function(){
			   var page = "upload";
			   var formData = new FormData();
			   formData.append('file', $("#upload input")[0].files[0]);
			   $.ajax({
				    url: page,
				    type: "POST",
				    data: formData,
				    contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理 
				    processData: false, //必须false才会自动加上正确的Content-Type
				    success:function(result){
				    	console.log(result);
				    }
			   });
		     }); 
		   });
});

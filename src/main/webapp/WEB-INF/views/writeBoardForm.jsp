<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<style type="text/css">

td{
 padding:5px
}

</style>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

      <!-- 작성폼 -->
      <div align="center" style="padding-top: 10%;padding-bottom: 10%;">
      <h1>게시글 등록</h1>
         <form name="writeForm"  id="formname1">
         <table>
           <tr>
          <td>이름 : </td>
          <td> <input type="text" name="writer" style="width:100%"></td>           
           </tr>
          <tr>
          <td>제목 : </td>
          <td> <input type="text" name="title" style="width:100%"></td>           
           </tr>
          <td></td>
          <td><textarea id="editor1" name="content"></textarea>
            </td>           
           </tr>
         
         </table>
         <button class="btn-info" onclick="checkForm(event)">등록</button>
         <button onclick="back(event)">뒤로가기</button>
         </form>
      </div>

</body>

<script src="/resources/ckeditor/ckeditor.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">

var editor;
window.onload = function() {
	editor =CKEDITOR.replace('editor1',{
    	language:'ko',
    	height:'600px'
    });
};



function back(event){
	event.preventDefault();
	window.history.back();
}

function checkForm(event){
	event.preventDefault();
	var form = document.writeForm;
	
	var title = form.title.value;
	var writer = form.writer.value;
	var content = CKEDITOR.instances.editor1.getData(); // ck에디터 값
	
	if(title==null || title==""){
		alert("제목은 필수입니다.");
		return;
	}else if(writer==null || writer==""){
		alert("이름은 필수입니다.");
		return;
	}else if (content==null || content==""){
		alert("내용은 필수입니다.");
		return;
	}	
	
	//var params = $("#formname1").serialize(); // serialize() : 입력된 모든Element(을)를 문자열의 데이터에 serialize 한다.
	    $.ajax({
	        url: '/Test/insertBoard.do',
	        type: 'POST',
	        data:{content:content,writer:writer,title:title},
	        contentType: 'application/x-www-form-urlencoded; charset=UTF-8', 
	        dataType: 'text',
	        success: function (data) {
		          if(data=='success'){
		        	  alert("등록 되었습니다.");
		        	  location.href="/Test/mainBoard.do";
		          }else{
		        	  alert("등록실패");
		          }
	        }
	    });

	
	
}

</script>

</html>
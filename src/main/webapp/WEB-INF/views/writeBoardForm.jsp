<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
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
      <div align="center" style="padding-top: 20%;">
         <form name="writeForm"  id="formname1">
         <table>
           <tr>
          <td>이름 : </td>
          <td> <input type="text" name="writer"></td>           
           </tr>
          <tr>
          <td>제목 : </td>
          <td> <input type="text" name="title"></td>           
           </tr>
          <td>내용 : </td>
          <td><textarea name="content"></textarea></td>           
           </tr>
         
         </table>
         <button onclick="checkForm(event)">등록</button>
         <button onclick="back(event)">뒤로가기</button>
         </form>
      </div>

</body>


<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">

function back(event){
	event.preventDefault();
	window.history.back();
}

function checkForm(event){
	event.preventDefault();
	var form = document.writeForm;
	var title = form.title.value;
	var writer = form.writer.value;
	var content = form.content.value;
	
	if(title==null || title==""){
		alert("제목은 필수입니다.");
	}else if(writer==null || writer==""){
		alert("이름은 필수입니다.");
	}else if (content==null || content==""){
		alert("내용은 필수입니다.");
	}	
	
	var params = $("#formname1").serialize(); // serialize() : 입력된 모든Element(을)를 문자열의 데이터에 serialize 한다.
	    $.ajax({
	        url: '/Test/insertBoard.do',
	        type: 'POST',
	        data:params,
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
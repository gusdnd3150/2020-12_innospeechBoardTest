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
         <form name="writeForm">
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
         </form>
      </div>

</body>

<script type="text/javascript">

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
	}else{
		form.action="/Test/insertBoard.do"
		form.method="post";
		form.submit();
	}
}

</script>

</html>
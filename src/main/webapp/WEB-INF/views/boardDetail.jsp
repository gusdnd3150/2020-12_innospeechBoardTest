<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script src="http://code.jquery.com/jquery-latest.js"></script>

<style type="text/css">
table, th, td {
	border-style: groove;
}
</style>


<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div class="container">
		<div align="center" style="padding-top: 10%">
			<form name="modifyInfo" id="modifyInfo">
			<input type="hidden" name ="boardNum" value="${boardDetail.boardNum }">
				<table class="table">
					<tr>
						<td>작성자</td>
						<td><input type="text" name="writer" readonly="readonly"
							value="${boardDetail.writer }" style="border: none;"></td>
					</tr>
					<tr>
						<td>제목</td>
						<td><input type="text" name="title" readonly="readonly"
							value="${boardDetail.title }" style="border: none;"></td>
					</tr>
					<tr>
						<td>작성일</td>
						<td>${boardDetail.updateD }</td>
					</tr>
					<tr>
					
						<td colspan="2"><textarea id="modeEditor" name="content" >${boardDetail.content }</textarea></td>
					</tr>
				</table>

			</form>
		</div>

		<!-- 버튼 위치 -->
		<div id="button" align="center" style="padding-top: 10px;padding-bottom:10%">
			<button onclick="back()">뒤로가기</button>
			<button onclick="modify()">수정하기</button>
			<button class="btn-danger" onclick="deleteBoard(${boardDetail.boardNum})">삭제</button>
			<button class="btn-info" onclick="updateBoardVO()" style="display: none"
				id="buttonModify">수정</button>
		</div>

	</div>
	
	
</body>

<script src="/resources/ckeditor/ckeditor.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">

var editor;
window.onload = function() {
	editor =CKEDITOR.replace('modeEditor',{
    	language:'ko',
    	readOnly:'true',
    	height:'600px',
    	removePlugins: 'elementspath',
    	       resize_enabled: false
    });
};

CKEDITOR.on('instanceReady', function(ev) {
    editor = ev.editor;
    document.getElementById('readOnlyOn').style.display = '';
    editor.on('readOnly', function() {
      document.getElementById('readOnlyOn').style.display = this.readOnly ? 'none' : '';
      document.getElementById('readOnlyOff').style.display = this.readOnly ? '' : 'none';
    });
  });

function toggleReadOnly(isReadOnly) {
    editor.setReadOnly(isReadOnly);
  }




	function back() {
		window.history.back();

	}

	function modify() {
		var form = document.modifyInfo;
		var wrierTag = form.writer;
		var contentTag = form.content;
		var titleTag = form.title;
		wrierTag.removeAttribute("readonly");
		titleTag.removeAttribute("readonly");
		 toggleReadOnly(false);    // readlony 속성을 풀어준다
		document.getElementById("buttonModify").style.display = "inline";
	}

	
	//수정하기
	function updateBoardVO() {
		var form = document.modifyInfo;
		var writer = form.writer.value;
		var title = form.title.value;
		var boardNum =form.boardNum.value;
		var content = CKEDITOR.instances.modeEditor.getData(); // ck에디터 값
			$.ajax({
			url : '/Test/modifyBoard.do',
			type : 'POST',
			data : {content:content,title:title,writer:writer,boardNum:boardNum},
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			dataType : 'text',
			success : function(data) {
				if (data == 'success') {
					alert("수정완료");
					location.href="/Test/mainBoard.do";
				} else {

				}
			}
		}); 
	}
	
	function deleteBoard(boardNum){
		var check = window.confirm("삭제하시겠습니까");
		
		if(check==true){
			
			$.ajax({
				url : '/Test/deleteBoard.do',
				type : 'get',
				data : {boardNum:boardNum},
				dataType : 'text',
				success : function(data) {
					if (data == 'success') {
						alert("삭제완료");
						location.href="/Test/mainBoard.do";
					} else {
					}
				}
			});
		}
		return;
	}
</script>
</html>
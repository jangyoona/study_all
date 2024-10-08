<%@ page language="java" 
		 contentType="text/html; charset=utf-8"
    	 pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8" />
	<title>글상세보기</title>
	<link rel="Stylesheet" href="/mvcdemoweb/styles/default.css" />
	<link rel="Stylesheet" href="/mvcdemoweb/styles/input2.css" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

	<div id="pageContainer">
	
		<jsp:include page="/WEB-INF/views/modules/header.jsp" />
		
		<div style="padding-top:25px;text-align:center">
		<div id="inputcontent">
		    <div id="inputmain">
		        <div class="inputsubtitle">게시글 정보</div>		        
		        <table>
		            <tr>
		                <th>제목</th>
		                <td>${ board.title }</td>
		            </tr>
		            <tr>
		                <th>작성자</th>
		                <td>${ board.writer }</td>
		            </tr>
		            <tr>
		            	<th>조회수</th>
		            	<td>${ board.readCount }</td>
		            </tr>
		            <tr>
		            	<th>작성일자</th>
		            	<td><fmt:formatDate value="${ board.writeDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		            </tr>
		            <tr>
		            	<th>수정일자</th>
		            	<td><fmt:formatDate value="${ board.writeDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		            </tr>
		            <tr>
		                <th>첨부파일</th>
		                <td>
		                <c:forEach var="attach" items="${ board.attachments }">
		                	<a href="download?attachno=${ attach.attachNo }">
		                	${ attach.userFileName }
		                	</a><br>
						</c:forEach>
		                </td>
		            </tr>
		            <tr>
		                <th>글내용</th>
						<td>
<c:set var="enter" value="
" />
							${ fn:replace(board.content, enter, "<br>") }
						</td>
		            </tr>
		        </table>
		        <div class="buttons">
		        	<%-- <c:if test="${ not empty loginuser and loginuser.memberId eq board.writer }"> --%>
		        	<c:if test="${ loginuser != null && loginuser.memberId == board.writer}">
		        	<input type="button" id="edit_button" value="편집" style="height:25px" />
		        	<input type="button" id="delete_button" value="삭제" style="height:25px" />
		        	</c:if>
		        	<input type="button" id="tolist_button" value="목록보기" style="height:25px" />
		        </div>
		    </div>
		</div>   	

		<br>

		<!-- write comment area -->
		<form id="commentform" action="write-comment" method="post">
			<input type="hidden" name="boardNo" value="${ board.boardNo }" />
			<input type="hidden" name="pageNo" value="${ pageNo }" />
			<input type="hidden" name="writer" value="${ loginuser.memberId }" />
			<table style="width:800px;border:solid 1px;margin:0 auto">
				<tr>
					<td style="width:750px">	                	
						<textarea id="comment_content" name="content" style="width:100%;resize: none;" rows="3"></textarea>	                    
					</td>
					<td style="width:50px;vertical-align:middle">
						<a id="write-comment-lnk" href="javascript:" style="text-decoration:none">
							댓글<br />등록
						</a>
					</td>
				</tr>                    
			</table>
		</form>
		<!-- end of write comment area -->
		
		<br>
	    <hr style="width:800px;margin:0 auto">
	    <br>
	    
	    <!-- comment list area -->
	    <table id="comment-list" style="width:800px;border:solid 1px;margin:0 auto">
		<c:forEach var="comment" items="${ board.comments }">				
			<tr>
				<td style="text-align:left;margin:5px;border-bottom: solid 1px;">
					<table>
						<tr>
							<td>
								<c:forEach begin="0" end="${ comment.depth }">
									&nbsp;&nbsp;
								</c:forEach>
								<c:if test="${ comment.depth > 0 }">
									<img src="/mvcdemoweb/image/re.gif">
									&nbsp;
								</c:if>
							</td>
							<td>					
								<div id="comment-view-area-${ comment.commentNo }">
								<c:choose>
								<c:when test="${ comment.deleted }">
									<br><br>
									<span style='color:gray'>삭제된 글입니다.</span>
									<br><br>
								</c:when>
								<c:otherwise>
									${ comment.writer } &nbsp;&nbsp; [<fmt:formatDate value="${ comment.writeDate }" pattern="yyyy-MM-dd hh:mm:ss"/>]
								    <br /><br />
								    <span>${ fn:replace(comment.content, enter, "<br>") }</span>
									<br /><br />
									<div style='float:left; display:${ (not empty loginuser and loginuser.memberId == comment.writer) ? "block" : "none" }'>
								    	<a class="edit-comment" data-comment-no="${ comment.commentNo }" href="javascript:">편집</a>
										&nbsp;
										<a class="delete-comment" data-comment-no="${ comment.commentNo }" href="javascript:">삭제</a>
										&nbsp;&nbsp;
									</div>
									<div style='float:left; display:${ not empty loginuser ? "block" : "none" }'>
										<a class="write-recomment" data-comment-no="${ comment.commentNo }" href="javascript:">댓글쓰기</a>
									</div>
									<span style="clear:both"></span>
								</c:otherwise>
								</c:choose>
								</div>	                
								<div id="comment-edit-area-${ comment.commentNo }" style="display: none">
									${ comment.writer } &nbsp;&nbsp; [${ comment.writeDate }]
									<br /><br />
									<form action="edit-comment" method="post">
									<input type="hidden" name="boardno" value="${ board.boardNo }" />
									<input type="hidden" name="commentno" value="${ comment.commentNo }" />
									<textarea name="content" style="width: 99%; resize: none" rows="3" cols="120">${ comment.content }</textarea>
									</form>
									<br />
									<div>
										<a class="modify-comment" data-comment-no="${ comment.commentNo }" href="javascript:">수정</a> 
										&nbsp; 
										<a class="cancel-edit-comment" data-comment-no="${ comment.commentNo }" href="javascript:">취소</a>
									</div>
								</div>
						
							</td>
			
						</tr>
					</table>
				</td>
			</tr>
		</c:forEach>        	
		</table>
		<!-- end of comment list area -->
		
		<br><br><br><br><br>
	
	</div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="recomment-modal" tabindex="-1" aria-labelledby="recomment-modal-label" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="recomment-modal-label">댓글 쓰기</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <form id="recommentform" action="write-recomment" method="post">
				<input type="hidden" name="boardno" value="${ board.boardNo }" />
				<input type="hidden" name="commentno" value="" />
				<input type="hidden" name="writer" value="${ loginuser.memberId }" />
				
				<textarea id="recomment-content" name="content" class="form-control" style="resize: none;" rows="3"></textarea>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
	        <button type="button" class="btn btn-primary" id="write-recomment-btn">댓글 쓰기</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$('#delete_button').on('click', function(event) {
				const ok = confirm("${ board.boardNo }번 글을 삭제할까요?");
				if (ok) {
					location.href = 'delete?boardno=${ board.boardNo }';
				}
			});
			
			$('#edit_button').on('click', function(event) {
				location.href = 'edit?boardno=${ board.boardNo }';
			});
			
			$('#tolist_button').on('click', function(event) { // 목록보기버튼
				location.href = 'list?pageNo=${ pageNo }'; 
			});
			//////////////////////////////////////////////////////
			
			// 댓글쓰기
			$('#write-comment-lnk').on('click', function(event) {
				
				if ($('#comment_content').val().length == 0) {
					alert('댓글 내용을 작성하세요');
					$('#comment_content').focus();
					return;
				}
				
				$('#commentform').submit(); // <form> 요소를 서버로 전송하는 명령
				
			});
			
			// 댓글 삭제
			$('.delete-comment').on('click', function(event) {
				const commentNo = $(this).data('comment-no'); // .data('comment-no') -> data-comment-no 속성의 값 조회
				const ok = confirm(commentNo + "번 댓글을 삭제할까요?");
				if (ok) {
					location.href = 'delete-comment?boardno=${ board.boardNo }&commentno=' + commentNo;
				}
				
			});
			
			
			// 댓글 수정 0 - 공통 변수 및 함수 선언
			let currentEditCommentNo = null;
			function changeCommentEditMode(commentNo, isCommentMode) {
				$('#comment-view-area-' + commentNo).css({'display': isCommentMode ? 'none' : ''});
				$('#comment-edit-area-' + commentNo).css({'display':isCommentMode ? '' : 'none'});
			}
			
			// 댓글 수정 1 - 수정 화면으로 변경
			$('.edit-comment').on('click', function(event) {
				if (currentEditCommentNo != null) {
					changeCommentEditMode(currentEditCommentNo, false);		
				}
				const commentNo = $(this).data('comment-no'); // .data('comment-no') -> data-comment-no 속성의 값 조회
				changeCommentEditMode(commentNo, true);
				currentEditCommentNo = commentNo;
			});
			// 댓글 수정 2 - 보기 화면으로 변경
			$('.cancel-edit-comment').on('click', function(event) {
				const commentNo = $(this).data('comment-no'); // .data('comment-no') -> data-comment-no 속성의 값 조회
				changeCommentEditMode(commentNo, false);
				currentEditCommentNo = null;
			});
			
			// 댓글 수정 3 - 수정 요청 보내기 (submit)
			$('.modify-comment').on('click', function(event) {
				const commentNo = $(this).data('comment-no');
				$('#comment-edit-area-' + commentNo + ' form').submit();
			});
			
			// 대댓글 (recomment) 1. 대댓글 창 표시
			$('.write-recomment').on('click', function(event) {
				$('#recommentform')[0].reset(); // <form> 초기화 == 닫기 버튼을 눌렀을 때나 뒤로 갔다가 새로운 댓글에 대댓글 달 때 비워주기 위함.
				const commentNo = $(this).data('comment-no'); // 현재 클릭된 댓글의 번호 (누구의 댓글인지 확인)
				$('#recommentform input[name=commentno]').val(commentNo);
				
				$('#recomment-modal').modal('show'); // show : 표시, hide : 숨기기
			});
			
			// 대댓글 (recomment) 2. 대댓글 작성 요청 보내기
			$('#write-recomment-btn').on('click', function(event) {
				$('#recommentform').submit();
			});
		});
	</script>
	

</body>
</html>
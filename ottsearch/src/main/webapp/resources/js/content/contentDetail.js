// 삭제버튼 클릭시 알림창
const confirmDelete = (contentId) => {
  let confirmResult = confirm("컨텐츠를 삭제하시겠습니까?");

  if (confirmResult) {
    deleteContent(contentId);
  };
};
// 삭제 요청
const deleteContent = (contentId) => {
  let deleteData = {contentId : contentId};

  $.ajax({
    method: 'POST',
    url: `/content/delete/${contentId}`,
    data: deleteData,
    success: function(res) {
    	alert('삭제되었습니다.');
    	location.replace('/content/contentList');
    }
  });
};

// 댓글 작성 권한
function commentRight(isLogOn, loginForm, contentId) {
  if (isLogOn) {
    addComment(contentId);
  } else {
    alert("로그인 후 댓글 작성이 가능합니다.");
    location.href = loginForm + '?action=/content/contentDetail/' + contentId;
  }
}

// 좋아요
function toggleLike(contentId) {
    if (!isLogOn) {
        alert("로그인 후 이용 가능합니다.");
        return;
    }

    $.ajax({
        url: '/like/toggle/' + contentId,
        type: 'POST',
        data: { userId: loggedInUserId },
        success: function(response) {
            $("#likeCount").text(response.likeCount);
        },
        error: function() {
            alert('오류가 발생했습니다. 다시 시도해주세요.');
        }
    });
}

// 댓글 추가
function addComment(contentId) {
    var commentText = $("#comment-text").val();
    var commentData = { 
        contentId: contentId, 
        userId: loggedInUserId, 
        commentText: commentText 
    };

    $.ajax({
        url: '/content/' + contentId + '/comments',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(commentData),
        success: function(response) {
            alert('댓글이 추가되었습니다.');
            location.reload();
        },
        error: function() {
            alert('댓글 추가에 실패했습니다.');
        }
    });
}

// 댓글 삭제
function deleteComment(commentId, commentUserId) {
    if (loggedInUserId !== commentUserId) {
        alert("본인의 댓글만 삭제할 수 있습니다.");
        return;
    }

    if (confirm("댓글을 삭제하시겠습니까?")) {
        $.ajax({
            url: '/content/comments/' + commentId,
            type: 'DELETE',
            success: function(response) {
                alert('댓글이 삭제되었습니다.');
                location.reload();
            },
            error: function() {
                alert('댓글 삭제에 실패했습니다.');
            }
        });
    }
}

// 댓글 수정
function updateComment(commentId, commentUserId) {
    if (loggedInUserId !== commentUserId) {
        alert("본인의 댓글만 수정할 수 있습니다.");
        return;
    }

    var commentText = $("#commentText-" + commentId).val();
    var commentData = { commentText: commentText };

    $.ajax({
        url: '/content/comments/' + commentId,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(commentData),
        success: function(response) {
            alert('댓글이 수정되었습니다.');
            location.reload();
        },
        error: function() {
            alert('댓글 수정에 실패했습니다.');
        }
    });
}

// 댓글 조회
function readComment(contentId) {
    $.ajax({
        url: '/content/' + contentId + '/comments',
        type: 'GET',
        success: function(comments) {
            var commentsHtml = "";
            $.each(comments, function(index, comment) {
                commentsHtml += "<div id='comment-" + comment.commentId + "' data-comment-text='" + comment.commentText + "'>";
                commentsHtml += "<p>" + comment.commentText + " (작성자: " + comment.nickname + ")</p>";
                if (loggedInUserId === comment.userId) {
                    commentsHtml += "<button class='btn btn-outline-light' onclick='deleteComment(" + comment.commentId + ", " + comment.userId + ")'>삭제</button>";
                    commentsHtml += "<button class='btn btn-outline-light' onclick='editComment(" + comment.commentId + ")'>수정</button>";
                }
                commentsHtml += "</div>";
            });
            $("#comments").html(commentsHtml);
        },
        error: function() {
            alert('댓글 조회에 실패했습니다.');
        }
    });
}

// 댓글 수정 폼 표시
function editComment(commentId) {
    // 기존 댓글 내용 가져오기
    var existingCommentText = $("#comment-" + commentId).data("commentText");
    var editHtml = "<textarea id='commentText-" + commentId + "' class='mod-comment'>" + existingCommentText + "</textarea>";
    editHtml += "<button class='btn btn-outline-light' onclick='updateComment(" + commentId + ")'>수정</button>";

    $("#comment-" + commentId).html(editHtml);
}

// 댓글 수정
function updateComment(commentId) {
    var updatedCommentText = $("#commentText-" + commentId).val();
    var commentData = { commentText: updatedCommentText };

    $.ajax({
        url: '/content/comments/' + commentId,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(commentData),
        success: function(response) {
            alert('댓글이 수정되었습니다.');
            location.reload();
        },
        error: function() {
            alert('댓글 수정에 실패했습니다.');
        }
    });
}

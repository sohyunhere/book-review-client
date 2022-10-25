function deleteComment(commentId) {
    if(confirm("댓글을 삭제하시겠습니까?")) {
        let header = $("meta[name='_csrf_header']").attr('content');
        let token = $("meta[name='_csrf']").attr('content');

        let postId = $("#postId").val();
        $.ajax({
            async: true,
            type : "get",
            url : "/board/delete/comment/"+commentId,
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success:
                function (result) {
                    alert("댓글이 삭제되었습니다");
                    location.href = "/board/"+ postId;
                },
            error :
                function (request, status, error){
                    alert("댓글 삭제 실패"+ "code:"+request.status+"\n"+" message : " + request.responseText +"\n"+"error:"+error);
                }
        });
    }
}
function deletePost() {
    if(confirm("게시글을 삭제하시겠습니까?")) {
        let header = $("meta[name='_csrf_header']").attr('content');
        let token = $("meta[name='_csrf']").attr('content');
        let postId = $("#postNum").val();
        $.ajax({
            async: true,
            type : "post",
            url : "/board/delete/post/"+postId,
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success:
                function (result) {
                    alert("게시글이 삭제되었습니다");
                    location.href = "/";
                },
            error :
                function (request, status, error){
                    alert("게시글 삭제 실패"+ "code:"+request.status+"\n"+" message : " + request.responseText +"\n"+"error:"+error);
                }
        });
    }
}
function writeComment() {
    let content = $("#commentContent").val()
    let postId = $("#postId").val()
    if(!checkExistData(content, "댓글을")){
        $("#commentContent").focus();
        return false;
    }
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');
    $.ajax({
        async: true,
        type : "post",
        data : JSON.stringify({
            postId : postId,
            writtenDate: moment().format(),
            content : content
        }),
        url : "/comment/write",
        contentType : "application/json; charset=UTF-8",
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:
            function (commentId) {
                alert("댓글 등록이 완료되었습니다");
                location.href = "/board/"+ postId;
            },
        error :
            function (request, status, error){
                alert("댓글 등록 실패"+ "code:"+request.status+"\n"+" message : " + request.responseText +"\n"+"error:"+error);
            }
    });
    return true;
}
function checkExistData(value, dataName) {
    value = value.trim();
    if (value == "") {
        alert(dataName + " 입력해주세요!");
        return false;
    }
    return true;
}
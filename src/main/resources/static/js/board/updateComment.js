function updateComment(commentId) {
    let commentcon = 'com' + commentId;
    $("#" + commentcon).removeAttr("readonly");

    let updatebtn = 'update' + commentId;
    $("#" + updatebtn).html('수정 완료');

    $("#" + updatebtn).removeAttr("onclick");
    $("#" + updatebtn).attr("onclick", "saveUpdateComm("+commentId+")");

}
function saveUpdateComm(commentId) {
    let commentcon = 'com' + commentId;
    let updatebtn = 'update' + commentId;
    if(confirm("댓글을 수정하시겠습니까?")) {
        let header = $("meta[name='_csrf_header']").attr('content');
        let token = $("meta[name='_csrf']").attr('content');
        let postId = $("#postId").val();
        let content = $("#" + commentcon).val();
        $.ajax({
            async: true,
            type: "post",
            data: {"content" : content},
            url: "/board/update/comment/" + commentId,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success:
                function (result) {
                    $("#" + commentcon).prop("readonly", true);
                    $("#" + updatebtn).html('수정');

                    $("#" + updatebtn).removeAttr("onclick");
                    $("#" + updatebtn).attr("onclick", "updateComment("+commentId+")");
                },
            error:
                function (request, status, error) {
                    alert("댓글 수정 실패" + "code:" + request.status + "\n" + " message : " + request.responseText + "\n" + "error:" + error);
                }
        });
    }
}


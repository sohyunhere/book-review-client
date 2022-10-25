$(document).ready(function() {
    // ajax 통신
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');

    $.ajax({
        // async: true,
        type : "get",
        // contentType: "application/json",
        url : "/main/posts",
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:
            function (data) {
                setGridData(data.posts);
            },
        error :
            function (request, status, error){
                alert("실패"+ "code:"+request.status+"\n"+" message : " + request.responseText +"\n"+"error:"+error);
            }
    });
});
function checkSearch(){
    let word = $("#search").val()
    if (!checkExistData(word, "검색어를")) {
        return false;
    }
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');
    $.ajax({
        async: true,
        type : "get",
        contentType: "application/json",
        url : "/board/search",
        data : {
            "searchType" : $("#searchType").val(),
            "search" : word
        },
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:
            function (data) {
                $("#latest").attr("disabled", "disabled");
                $("#view").attr("disabled", "disabled");
                setGridData(data.posts);
            },
        error :
            function (request, status, error){
                alert("실패"+ "code:"+request.status+"\n"+" message : " + request.responseText +"\n"+"error:"+error);
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
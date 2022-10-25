const Editor = toastui.Editor;

const editor = new Editor({
    el: document.querySelector('#editor'),
    height: '600px',
    initialEditType: 'wysiwyg',
    initialValue: $("#content").val(),
    previewStyle: 'vertical'
});

function checkAll() {
    if (!checkExistData(editor.getMarkdown(), "내용을")) {
        return false;
    }
    if(confirm("게시글을 수정하시겠습니까?")) {
        let header = $("meta[name='_csrf_header']").attr('content');
        let token = $("meta[name='_csrf']").attr('content');
        let postId = $("#postId").val();
        let content = editor.getHTML();

        $.ajax({
            async: true,
            type : "post",
            data : JSON.stringify({
                postTitle : $("#postTitle").val(),
                readDate : $("#readDate").val(),
                bookTitle : $("#bookTitle").val(),
                author : $("#author").val(),
                publisher : $("#publisher").val(),
                categoryId : $("#categoryId").val(),
                content : content,

            }),
            url : "/board/update/"+postId,
            contentType : "application/json; charset=UTF-8",
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success:
                function (postId) {
                let files;
                    if( $("#formFile2").val() === undefined){
                        let fileUpload = $("#formFile");
                        files = fileUpload[0].files;
                        if(files.length == 1){
                            //첨부파일이 존재할 경우
                            uploadFile(postId, files);
                        }
                    }else{
                        let fileUpload = $("#formFile2");
                        files = fileUpload[0].files;
                        if(files.length == 1){
                            //첨부파일이 존재할 경우
                            uploadFile(postId, files);
                        }
                    }
                    //첨부파일 없음
                    alert("게시글 수정이 완료되었습니다");
                    location.href = "/board/"+ postId;
                },
            error :
                function (request, status, error){
                    alert("게시글 수정 실패"+ "code:"+request.status+"\n"+" message : " + request.responseText +"\n"+"error:"+error);
                }
        });
    }
}

function checkExistData(value, dataName) {
    value = value.trim();
    if (value == "") {
        alert(dataName + " 입력해주세요!");
        return false;
    }
    return true;
}

function deletefile(fileId){
    console.log(fileId);
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');
    $.ajax({
        async: true,
        type : "get",
        url : "/delete/"+fileId,
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:
            function (id) {
            $("#f").remove();
            let ff = document.getElementById('ff');
            ff.style.display = 'block';
            },
        error :
            function (request, status, error){
                alert("파일 삭제 실패"+ "code:"+request.status+"\n"+" message : " + request.responseText +"\n"+"error:"+error);
            }
    });

}
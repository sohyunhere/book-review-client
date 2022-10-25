function uploadFile(postId, files){
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');

    let formData = new FormData();
    formData.append("uploadfile", files[0]);
    formData.append("postId", postId);

    console.log(formData);

    $.ajax({
        url : "/board/fileUpload",
        type : "POST",
        processData : false,
        contentType : false,
        data : formData,
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },

        success:function(response) {
            console.log(response);
        },
        error: function (jqXHR)
        {
            alert(jqXHR.responseText);
        }
    });

}
let lan = "";
let lng = "";
$(document).ready(function() {

    if (navigator.geolocation) {
        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition(function (position) {
            lan = position.coords.latitude; // 위도
            lng = position.coords.longitude; // 경도

        });
    }
});

const Editor = toastui.Editor;

const editor = new Editor({
    el: document.querySelector('#editor'),
    height: '600px',
    initialEditType: 'wysiwyg',
    initialValue: '',
    previewStyle: 'vertical',

    hooks : {
        addImageBlobHook : async (blob, callback) => {
            let upload = await uploadImage(blob);
            callback(upload, '');
        }
    }
});

function uploadImage(blob) {
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');

    let formData = new FormData();
    let fileUrlData;
    formData.append('image', blob); // 이미지를 폼데이터 file로 변경 'image'가 input name이다.
    let url = '/images/';
    $.ajax({
        url : 'image/editorUpload',
        enctype: 'multipart/form-data',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        async: false, // 비동기를 동기로 변경.
        cache: false,
        timeout: 600000,
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:
            function (data){
                url += data;
                fileUrlData = url;
            },
        error :
            function (request, status, error){
                alert("이미지 없로드 실패"+ "code:"+request.status+"\n"+" message : " + request.responseText +"\n"+"error:"+error);
            }
    });
    return fileUrlData;
}

function checkAll() {
    let fileUpload = $("#formFile");
    let files = fileUpload[0].files;
    console.log(files.length);

    if (!checkExistData(writePostForm.postTitle.value, "제목을")) {
        writePostForm.postTitle.focus();
        return false;
    }
    if(!checkReadDate(writePostForm.readDate.value)){
        writePostForm.readDate.focus();
        return false;
    }

    if (!checkExistData(writePostForm.bookTitle.value, "도서 제목을")) {
        writePostForm.bookTitle.focus();
        return false;
    }
    if (!checkExistData(writePostForm.author.value, "지은이를")) {
        writePostForm.author.focus();
        return false;
    }
    if (!checkExistData(writePostForm.publisher.value, "출판사")) {
        writePostForm.publisher.focus();
        return false;
    }
    if (!checkExistData(writePostForm.categoryId.value, "카테고리")) {
        writePostForm.category.focus();
        return false;
    }
    if(!checkExistData(editor.getMarkdown(), "내용울")){
        return false;
    }

    if(confirm("게시글을 등록하시겠습니까?")) {
        let header = $("meta[name='_csrf_header']").attr('content');
        let token = $("meta[name='_csrf']").attr('content');
        let content = editor.getHTML();

        $.ajax({
            async: true,
            type : "post",
            data : JSON.stringify({
            postTitle : $("#postTitle").val().trim(),
            readDate : $("#readDate").val(),
            bookTitle : $("#bookTitle").val().trim(),
            author : $("#author").val().trim(),
            publisher : $("#publisher").val().trim(),
            categoryId : $("#categoryId").val(),
            writtenDate : moment().format(),
            content : content,
            lat : lan,
            lng : lng
            }),
            url : "/board/write",
            contentType : "application/json; charset=UTF-8",
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success:
                function (postId) {
                    if(files.length == 1){
                        //첨부파일이 존재할 경우
                        uploadFile(postId, files);
                    }
                    //첨부파일 없음
                    alert("게시글 등록이 완료되었습니다");
                    location.href = "/board/"+ postId;

                },
            error :
                function (request, status, error){
                    alert("게시글 등록 실패"+ "code:"+request.status+"\n"+" message : " + request.responseText +"\n"+"error:"+error);
                }
          });
    }
    return true;
}

function checkReadDate(value){
    if (!checkExistData(value, "읽은 날짜")) {
        return false;
    }

    if(value){
        let today = moment().format();
        if(value > today){
            alert("책을 읽은 날짜는 오늘 날짜까지 선택 가능합니다.")
            return false;
        }
    }
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
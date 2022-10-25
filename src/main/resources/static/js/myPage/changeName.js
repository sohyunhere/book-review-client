function checkNickname() {
    let v = $("#nickname").val().trim();
    if (!checkExistData(v, "닉네임을")) {
        return false;
    }
    if(!checkSameNickname(v)){
        return false;
    }

    if(confirm("닉네임을 변경하시겠습니까?")) {
        let header = $("meta[name='_csrf_header']").attr('content');
        let token = $("meta[name='_csrf']").attr('content');

        $.ajax({
            async: true,
            type : "post",
            data : {
                "nickname" : v
            },
            url : "/member/mypage/nickname",
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success:
                function (result) {
                    alert("닉네임 수정이 완료되었습니다");
                    location.href = "mypage";
                },
            error :
                function (request, status, error){
                    alert("닉네임 수정 실패"+ "code:"+request.status+"\n"+" message : " + request.responseText +"\n"+"error:"+error);
                }
        });
    }
    return true;
}

function checkExistData(value, dataName) {
    if (value == "") {
        alert(dataName + " 입력해주세요!");
        return false;
    }
    return true;
}

function checkSameNickname(v){
    let name = changeNameForm.name.value;
    let change = v;

    if(name == change){
        alert("닉네임이 달라지지 않았습니다.")
        return false;
    }
    return true;
}
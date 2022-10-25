
function checkAll() {
    if (!checkExistData(changePasswordForm.originPassword.value, "현재 비밀번호를")) {
        return false;
    }

    if (!checkPassword(changePasswordForm.newPassword.value, changePasswordForm.newPassword2.value)) {
        return false;
    }
    if(confirm("비밀번호를 변경하시겠습니까?")){
        let header = $("meta[name='_csrf_header']").attr('content');
        let token = $("meta[name='_csrf']").attr('content');
        $.ajax({
            async: true,
            type : "post",
            data : {
                "originPassword" : $("#originPassword").val(),
                "newPassword" : $("#newPassword").val()
            },
            url : "/member/mypage/password",
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success:
                function (result) {
                    if (result == 1) {
                        alert("비밀번호 변경이 완료되었습니다");
                        location.href = "/member/mypage";
                    } else {
                        alert("현재 비밀번호가 일치하지 않습니다.")

                    }
                },
            error :
                function (request, status, error){
                    alert("비밀번호 변경 실패"+ "code:"+request.status+"\n"+" message : " + request.responseText +"\n"+"error:"+error);
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

function checkPassword(password1, password2){
    if (!checkExistData(password1, "새 비밀번호를"))
        return false;
    //비밀번호 확인이 입력되었는지 확인하기
    if (!checkExistData(password2, "새 비밀번호 확인을"))
        return false;
    var pw = password1;

    var reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,32}$/;
    var hangulcheck = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;

    if(false === reg.test(pw)) {
        alert('비밀번호는 8자 이상 32자 이하 이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.');
        changePasswordForm.newPassword.focus();
        return false;
    }else if(/(\w)\1\1\1/.test(pw)){
        alert('같은 문자를 4번 이상 사용하실 수 없습니다.');
        changePasswordForm.newPassword.focus();
        return false;
    }else if(pw.search(/\s/) != -1){
        alert("비밀번호는 공백 없이 입력해주세요.");
        changePasswordForm.newPassword.focus();
        return false;
    }else if(hangulcheck.test(pw)){
        alert("비밀번호에 한글을 사용 할 수 없습니다.");
        changePasswordForm.newPassword.focus();
        return false;
    }else if(!checkDifferent(password1, changePasswordForm.originPassword.value)){
        return false;
    }
    else if(!checkIt(password1, password2)){
        return false;
    }else{
        return true;
    }
    return true;
}

function checkIt(password1, password2){

    if (password1 != password2) {
        alert("비밀번호가 일치하지 않습니다");
        changePasswordForm.newPassword2.focus();
        return false;
    }
    if(changePasswordForm.newPassword.value.length <8 || changePasswordForm.newPassword2.value.length > 32){
        alert("비밀번호를 8~32자 사이로 입력하세요");
        return false;
    }

    return true;
}
function checkDifferent(newPass, originPass){
    if(newPass == originPass){
        alert("현재 비밀번호와 새로운 비밀번호가 동일합니다.");
        changePasswordForm.newPassword.focus();
        return false;
    }
    return true;
}
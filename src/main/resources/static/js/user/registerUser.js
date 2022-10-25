function checkAll() {
    if (!checkMail(registerForm.email.value)) {
        return false;
    }
    if (!checkPassword(registerForm.password.value, registerForm.password2.value)) {
        return false;
    }
    let v = $("#nickname").val().trim();
    if (!checkExistData(v, "닉네임")) {
        return false;
    }
    if(confirm("회원가입을 하시겠습니까?")) {
        let header = $("meta[name='_csrf_header']").attr('content');
        let token = $("meta[name='_csrf']").attr('content');
        $.ajax({
            async: true,
            type : "post",
            data : JSON.stringify({
                email : $("#email").val(),
                password : $("#password").val(),
                nickname : v
            }),
            url : "/member/register",
            contentType : "application/json; charset=UTF-8",
            beforeSend : function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success :
                function(result){
                    alert("회원가입이 완료되었습니다. 감사합니다.");
                    location.href = "/member/login";
            },
            error :
                function (request, status, error){
                    alert("회원 가입 실패"+ "code:"+request.status+"\n"+" message : " + request.responseText +"\n"+"error:"+error);
                }
        });
    }

}
function checkMail(mail) {
    //mail이 입력되었는지 확인하기
    if (!checkExistData(mail, "이메일을"))
        return false;

    var emailRegExp = /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;
    if (!emailRegExp.test(mail)) {
        alert("이메일 형식이 올바르지 않습니다!");
        registerForm.email.value = "";
        registerForm.email.focus();
        return false;
    }
    return true; //확인이 완료되었을 때
}
function checkExistData(value, dataName) {
    if (value == "") {
        alert(dataName + " 입력해주세요!");
        return false;
    }
    return true;
}
function checkPassword(password1, password2){
    if (!checkExistData(password1, "비밀번호를"))
        return false;
    //비밀번호 확인이 입력되었는지 확인하기
    if (!checkExistData(password2, "확인 비밀번호를"))
        return false;

    var pw = password1;

    var reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,32}$/;
    var hangulcheck = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;

    if(false === reg.test(pw)) {
        alert('비밀번호는 8자 이상 32자 이하 이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.');
        registerForm.password.focus();
        return false;
    }else if(/(\w)\1\1\1/.test(pw)){
        alert('같은 문자를 4번 이상 사용하실 수 없습니다.');
        registerForm.password.focus();
        return false;
    }else if(pw.search(/\s/) != -1){
        alert("비밀번호는 공백 없이 입력해주세요.");
        registerForm.password.focus();
        return false;
    }else if(hangulcheck.test(pw)){
        alert("비밀번호에 한글을 사용 할 수 없습니다.");
        registerForm.password.focus();
        return false;
    }else if(!checkIt(password1, password2)){
        return false;
    }else{
        return true;
    }
    return true;
}

function checkIt(password1, password2){

    if (password1 != password2) {
        alert("비밀번호가 일치하지 않습니다");
        return false;
    }
    if(registerForm.password.value.length <8 || registerForm.password.value.length > 32){
        alert("비밀번호를 8~32자 사이로 입력하세요");
        return false;
    }

    return true;
}
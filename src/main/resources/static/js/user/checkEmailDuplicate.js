function emailCheck(){
    let email = $("#email").val();

    if(email.search(/\s/) != -1){
        alert("이메일에는 공백이 들어갈 수 없습니다.");
    }else{
        if(email.trim().length != 0){
            // ajax 통신
            let header = $("meta[name='_csrf_header']").attr('content');
            let token = $("meta[name='_csrf']").attr('content');
            $.ajax({
                async: true,
                type : "post",
                data : {"email" : email},
                url : "/member/emailCheck",

                beforeSend: function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success: [
                    function (checking) {
                        if (checking == 0) {//중복아님
                            alert("사용 가능한 이메일입니다.");
                            $("#email").attr("readonly",true);
                            $("#submit").removeAttr("disabled");
                        } else {
                            alert("해당 이메일이 이미 존재합니다." + email);
                            $("#submit").attr("disabled", "disabled");
                            window.location.reload();
                        }
                    }],
                error : [
                    function (request, status, error){
                        alert("이메일을 입력해주세요.--"+ "code:"+request.status+"\n"+" message : " + request.responseText +"\n"+"error:"+error);

                    }]
            });
        }
        else{
            alert("이메일을 입력해주세요.@@");
        }
    }
}
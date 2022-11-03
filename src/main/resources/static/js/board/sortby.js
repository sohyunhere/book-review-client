function sortByLatest() {
    // ajax 통신
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');
    $.ajax({
        async: true,
        type: "get",
        contentType: "application/json",
        url: "/latest",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success:
            function (data) {
                $("#latest").attr("disabled", "disabled");
                $("#latest").addClass("btn-primary");
                $("#latest").removeClass("btn-outline-primary");
                $("#view").removeAttr("disabled");
                $("#view").removeClass("btn-secondary");
                $("#view").addClass("btn-outline-secondary");
                setGridData(data.posts);
            },
        error:
            function (request, status, error) {
                alert("실패" + "code:" + request.status + "\n" + " message : " + request.responseText + "\n" + "error:" + error);
            }
    });
}

function sortByView() {
    // ajax 통신
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');
    $.ajax({
        async: true,
        type: "get",
        contentType: "application/json",
        url: "/popular",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success:
            function (data) {
                $("#latest").removeAttr("disabled");
                $("#latest").removeClass("btn-primary");
                $("#latest").addClass("btn-outline-primary");
                $("#view").attr("disabled", "disabled");
                $("#view").addClass("btn-secondary");
                $("#view").removeClass("btn-outline-secondary");
                setGridData(data.posts);
            },
        error:
            function (request, status, error) {
                alert("실패" + "code:" + request.status + "\n" + " message : " + request.responseText + "\n" + "error:" + error);
            }
    });
}


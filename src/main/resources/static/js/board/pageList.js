function changePageCnt(){
    let pageSelect = document.getElementById("pageCnt");

    // select element에서 선택된 option의 value가 저장된다.
    let selectValue = pageSelect.options[pageSelect.selectedIndex].value;

    if(selectValue == '1'){
        setPerPage(10);
    }else if(selectValue == '2'){
        setPerPage(20);
    }else {
        setPerPage(50);
    }


}
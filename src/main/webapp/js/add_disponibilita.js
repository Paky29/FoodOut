function disableThisOpens(check){

    var parent=check.parentElement.parentElement;
    var opens=parent.getElementsByClassName("open");
    if(check.checked) {
        for (let i = 0; i < opens.length; i++) {
            opens[i].setAttribute("disabled", true);
            opens[i].value=null;
        }
    }
    else{
        for (let i = 0; i < opens.length; i++) {
            opens[i].removeAttribute("disabled");
        }
    }
}
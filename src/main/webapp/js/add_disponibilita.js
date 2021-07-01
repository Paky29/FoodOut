function disasbleInputs() {
    var check=document.getElementById("chiuso_Lunedi");
    if(check.checked) {
        document.getElementById("apLunedi").setAttribute('disabled', true);
        document.getElementById("cLunedi").setAttribute('disabled', true);
        document.getElementById("cLunedi").value=null;

    }
    else {
        document.getElementById("apLunedi").removeAttribute('disabled');
        document.getElementById("cLunedi").removeAttribute('disabled');
    }

}

function disableOpen(check){
    if(check.checked){
        var parent= check.parentElement.parentElement;
        var opens=parent.getElementsByClassName("open");
        opens.forEach(open => { open.setAttribute("disabled", true); open.value=null;})
    }
    else{
        var parent= check.parentElement.parentElement;
        var opens=parent.getElementsByClassName("open");
        opens.forEach(open => open.removeAttribute("disabled"));
    }
}

function disableInputs2(){
    var checks=document.getElementsByClassName("closed");
    checks.forEach(check => disableOpen(check));
}

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
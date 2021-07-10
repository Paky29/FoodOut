function search(tip){
    var input=document.getElementsByTagName("input")[0].value;
    var tipologia=tip.getElementsByClassName("box")[0].innerHTML;
    if(input.match(/(\w|\s|[èàòùìÀÒÈÙÌ]|'){1,30}/)) {
        window.location.href = "/FoodOut/ristorante/zona?citta="+input+"&tipologie="+tipologia;
    }
    else{
        alert("Inserire il nome di una citta'");
    }
}
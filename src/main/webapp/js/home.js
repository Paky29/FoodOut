function search(){
    var input=document.getElementsByTagName("input")[0].value;
    if(input.match(/(\w|\s|[èàòùìÀÒÈÙÌ]|'){1,30}/)) {
        alert(input);
        window.location.href = "/FoodOut/ristorante/zona?citta="+input;
    }
    else{
        alert("Inserire il nome di una citta'");
    }
}
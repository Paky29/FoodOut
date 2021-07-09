function showProdMenuDetails(element){
    var amount=element.previousElementSibling;
    amount.style.display="block";
}


function closeAmount(element){
    var amount=element.parentElement.parentElement;
    amount.style.display="none";
}

function removeItem(element){
    var parent=element.parentElement;
    var tipo =parent.getElementsByClassName("item-cod")[0].getAttribute("name");
    var id=parent.getElementsByClassName("item-cod")[0].getAttribute("value");
    var conf=confirm("Sicuro di voler elimare questo prodotto dal carrello?");
    if(conf==true) {
        if (tipo == "codiceMenu")
            window.location.href = "/FoodOut/ordineItem/remove-menu-item?id=" + id;
        else
            window.location.href = "/FoodOut/ordineItem/remove-prodotto-item?id=" + id;
    }

}

function goBack(){
    window.location.href = "/FoodOut/ristorante/zona"
}

function addToPrefs(idRis, idUtente){
    window.location.href="/FoodOut/ristorante/add-pref?id=" + idUtente + "&idRis=" + idRis;
}

function removeFromPrefs(idRis, idUtente){
    window.location.href="/FoodOut/ristorante/remove-pref?id=" + idUtente + "&idRis=" + idRis;
}

function toProfile(){
    window.location.href = "/FoodOut/utente/profile";
}
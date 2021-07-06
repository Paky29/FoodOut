function deleteRid(rid){
    var parent=rid.parentElement;
    var id=parent.getElementsByClassName("blank")[0].getAttribute("value");

    var conferma = confirm("Sicuro di voler eliminare l'ordine?");
    if(conferma){
        window.location.href = "/FoodOut/ordine/delete?id=" + id;
    }
};
function deleteOrd(ordId){
    var conferma = confirm("Sicuro di voler eliminare l'ordine?");
    if(conferma){
        window.location.href = "/FoodOut/ordine/delete?id=" + ordId;
    }
};
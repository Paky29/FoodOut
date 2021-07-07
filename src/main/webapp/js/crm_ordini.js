function deleteOrd(ord){
    var id=document.getElementById("id").textContent;
    var conferma = confirm("Sicuro di voler eliminare l'ordine?");
    if(conferma){
        window.location.href = "/FoodOut/ordine/delete?id=" + id;
    }
};
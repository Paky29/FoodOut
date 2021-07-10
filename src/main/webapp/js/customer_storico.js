function addRecensione(id, isAbsent){
    if(isAbsent)
        window.location.href = "/FoodOut/ordine/add-recensione?id=" + id;
    else
        alert("Recensione già inserita");
}
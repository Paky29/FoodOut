function addRecensione(id, isAbsent){
    if(isAbsent)
        window.location.href = "/FoodOut/ordine/recensione?id=" + id;
    else
        alert("Recensione già inserita");
}
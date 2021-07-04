function showDetails(element){
    id=element.lastElementChild.value;
    idRis= document.getElementById("idRis").value
    window.location.href = "/FoodOut/prodotto/update?id="+id + "&idRis=" + idRis;
}


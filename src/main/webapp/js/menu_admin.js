function showProdDetails(element){
    id=element.lastElementChild.value;
    idRis= document.getElementById("idRis").value;
    window.location.href = "/FoodOut/prodotto/update?id="+id + "&idRis=" + idRis;
}

function showMenuDetails(element){
    id=element.lastElementChild.value;
    idRis= document.getElementById("idRis").value;
    window.location.href = "/FoodOut/menu/update?id="+id + "&idRis=" + idRis;
}

function addProdMenu(element){
    idRis= document.getElementById("idRis").value;
    window.location.href = "/FoodOut/ristorante/add-prodmenu?id=" + idRis + "&function=1";
}

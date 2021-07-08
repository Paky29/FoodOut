function showRisDetails(){
    var id= document.getElementById("id").value;
    window.location.href = "/FoodOut/ristorante/show-menu?id="+id;
}

function toProfile(element){
    var id=element.lastElementChild.getAttribute("value");
    window.location.href = "/FoodOut/utente/show?id="+id;
}
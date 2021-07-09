function  showRisDetails(element){
    var id= element.lastElementChild.value;
    window.location.href = "/FoodOut/ristorante/show-menu?id="+id;

}

function toProfile(){
    window.location.href = "/FoodOut/utente/profile";
}

function goToIndex(){
    window.location.href = "/FoodOut/index.jsp";
}
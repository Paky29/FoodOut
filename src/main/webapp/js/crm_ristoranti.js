const add=document.getElementsByClassName("searchbar")[0].getElementsByTagName("svg")[0];
add.addEventListener('click',function(){
    window.location.href="/FoodOut/ristorante/add";
})

add.addEventListener('mouseover', function (){
    add.style.cursor="pointer";
})

function changeValidita(ris){
    var parent=ris.parentElement;
    var id=parent.getElementsByClassName("blank")[0].getAttribute("value");

    var conferma = confirm("Sicuro di voler cambiare la validità del ristorante?");
    if(conferma){
        window.location.href = "/FoodOut/ristorante/update-validita?id=" + id;
    }
};
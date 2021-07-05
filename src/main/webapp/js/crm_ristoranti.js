const add=document.getElementsByClassName("searchbar")[0].getElementsByTagName("svg")[0];
add.addEventListener('click',function(){
    window.location.href="/FoodOut/ristorante/add";
})

add.addEventListener('mouseover', function (){
    add.style.cursor="pointer";
})

/*const dels=document.getElementsByTagName("tbody")[0].getElementsByClassName("delete");
for(var i=0; i<dels.length; ++i)
{
    var del=dels[i].firstElementChild;
    var elem = document.getElementsByTagName("tbody")[0].getElementsByClassName("blank")[i].getAttribute("value");

    del.addEventListener('click', function () {
        var conferma = confirm("Sicuro di voler eliminare il ristorante?");
        if (conferma) {
            window.location.href = "/FoodOut/ristorante/delete?id=" + elem;
        }
    });
}*/

function changeValidita(ris){
    var parent=ris.parentElement;
    var id=parent.getElementsByClassName("blank")[0].getAttribute("value");

    var conferma = confirm("Sicuro di voler cambiare la validità del ristorante?");
    if(conferma){
        window.location.href = "/FoodOut/ristorante/update-validita?id=" + id;
    }
};
const add=document.getElementsByClassName("searchbar")[0].getElementsByTagName("svg")[0];
add.addEventListener('click',function(){
    window.location.href="/FoodOut/tipologia/create?function=0";
})


add.addEventListener('mouseover', function (){
    add.style.cursor="pointer";
})

/*const dels=document.getElementsByTagName("tbody")[0].getElementsByClassName("delete");
for(var i=0; i<dels.length; ++i)
{
    var del=dels[i].firstElementChild;
    var elem = document.getElementsByTagName("tbody")[0].getElementsByClassName("nome")[i].getAttribute("value");

    del.addEventListener('click', function () {
        var conferma = confirm("Sicuro di voler eliminare il ristorante?");
        if (conferma) {
            window.location.href = "/FoodOut/tipologia/delete?nome=" + elem;
        }
    });
}*/


function deleteTip(tip){
    var parent=tip.parentElement;
    var name=parent.getElementsByClassName("nome")[0].getAttribute("value")

    var conferma = confirm("Sicuro di voler eliminare la tipologia?");
    if(conferma){
        window.location.href = "/FoodOut/tipologia/delete?nome=" + name;
    }
};

function editTip(tip){

    var parent=tip.parentElement;
    var name=parent.getElementsByClassName("nome")[0].getAttribute("value");
    var descrizione=parent.getElementsByClassName("descrizione")[0].getAttribute("value")

    window.location.href = "/FoodOut/tipologia/update?function=1&nome=" + name;

}
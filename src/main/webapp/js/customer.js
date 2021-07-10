const menu = document.getElementsByClassName("topbar")[0].firstElementChild;
menu.addEventListener('click', function() {
    const sidebar =document.getElementsByClassName("sidebar")[0];
    const content =document.getElementsByClassName("content")[0];
    sidebar.classList.toggle("collapse");
    content.classList.toggle("full-width");
})

const home = document.getElementsByClassName("menu")[0].firstElementChild;
home.addEventListener('click', function (){
    window.location.href="/FoodOut/ristorante/zona";
});

home.addEventListener('mouseover', function (){
   home.style.cursor="pointer";
});

function confirmDelete(){
    var conferma = confirm("Sicuro di voler eliminare il profilo?");
    if(conferma){
        window.location.href = "/FoodOut/utente/delete";
    }
}
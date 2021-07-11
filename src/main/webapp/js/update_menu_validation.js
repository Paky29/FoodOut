const formMenu=document.getElementsByTagName("form")[0];

const nomeMenu = document.getElementById('nome');
const prezzoMenu = document.getElementById('prezzo');
const scontoMenu = document.getElementById('sconto');
const prodottiMenu=document.getElementById('prodotti');

nomeMenu.addEventListener('input', function (event) {

    if (nomeMenu.validity.valid) {
        nomeMenu.nextElementSibling.textContent = '';
    } else {
        showErrorMenu();
    }
});

prezzoMenu.addEventListener('input', function (event) {

    if (prezzoMenu.validity.valid) {
        prezzoMenu.nextElementSibling.textContent = '';
    } else {
        showErrorMenu();
    }
});

scontoMenu.addEventListener('input', function (event) {

    if (scontoMenu.validity.valid) {
        scontoMenu.nextElementSibling.textContent = '';
    } else {
        showErrorMenu();
    }
});

formMenu.addEventListener('submit', function (event) {
    if(!nomeMenu.validity.valid) {
        showErrorMenu();
        event.preventDefault();
    }

    if(!prezzoMenu.validity.valid) {
        showErrorMenu();
        event.preventDefault();
    }

    if(!scontoMenu.validity.valid) {
        showErrorMenu();
        event.preventDefault();
    }

    var count_selected=0;
    for(let i=0; i<prodottiMenu.length; i++){
        if(prodottiMenu[i].selected==true)
            count_selected+=1;
    }

    if(count_selected<2){
        alert("il menu deve essere composto da almeno due prodotti");
        event.preventDefault();
    }

});

function showErrorMenu() {
    if(nomeMenu.validity.valueMissing) {
        nomeMenu.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(nomeMenu.validity.typeMismatch) {
        nomeMenu.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(nomeMenu.validity.tooLong) {
        nomeMenu.nextElementSibling.textContent = 'nome deve essere al massimo di 30 caratteri';
    } else if(nomeMenu.validity.patternMismatch) {
        nomeMenu.nextElementSibling.textContent = 'il nome non deve contenere  caratteri speciali ';
    }

    if(prezzoMenu.validity.valueMissing) {
        prezzoMenu.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(prezzoMenu.validity.typeMismatch) {
        prezzoMenu.nextElementSibling.textContent = 'è necessario inserire un numero intero';
    } else if(prezzoMenu.validity.rangeUnderflow) {
        prezzoMenu.nextElementSibling.textContent = 'prezzo deve essere un numero maggiore di 0';
    } else if(prezzoMenu.validity.patternMismatch){
        prezzoMenu.nextElementSibling.textContent = 'prezzo deve essere un numero intero o decimale positivo';
    }

    if(scontoMenu.validity.valueMissing) {
        scontoMenu.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(scontoMenu.validity.typeMismatch) {
        scontoMenu.nextElementSibling.textContent = 'è necessario inserire un numero intero';
    } else if(scontoMenu.validity.rangeUnderflow) {
        scontoMenu.nextElementSibling.textContent = 'sconto deve essere un numero maggiore o uguale a 0';
    } else if(scontoMenu.validity.patternMismatch){
        scontoMenu.nextElementSibling.textContent = 'sconto deve essere un numero intero o decimale positivo';
    }


}
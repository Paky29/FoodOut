const form=document.getElementsByTagName("form")[0];

const voto = document.getElementById('voto');
const giudizio = document.getElementById('giudizio');


voto.addEventListener('input', function (event) {

    if (voto.validity.valid) {
        voto.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});

giudizio.addEventListener('input', function (event) {

    if (giudizio.validity.valid) {
        giudizio.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});

form.addEventListener('submit', function (event) {
    if(!voto.validity.valid) {
        showError();
        event.preventDefault();
    }
    if(!giudizio.validity.valid) {
        showError();
        event.preventDefault();
    }
});

function showError() {
    if(voto.validity.valueMissing) {
        voto.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(voto.validity.typeMismatch) {
        voto.nextElementSibling.textContent = 'è necessario inserire un numero intero';
    } else if(voto.validity.rangeUnderflow){
        voto.nextElementSibling.textContent = 'voto deve essere compreso tra 1 e 5';
    } else if(voto.validity.rangeOverflow){
        voto.nextElementSibling.textContent = 'voto deve essere compreso tra 1 e 5';
    }

    if(giudizio.validity.valueMissing) {
        giudizio.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(giudizio.validity.typeMismatch) {
        giudizio.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(giudizio.validity.tooLong) {
        giudizio.nextElementSibling.textContent = 'giudizio deve essere al massimo di 50 caratteri';
    }

}
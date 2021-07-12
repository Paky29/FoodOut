const form=document.getElementsByTagName("form")[0];

const nome=document.getElementById('nome');
const descrizione=document.getElementById('descrizione');

nome.addEventListener('input', function (event) {

    if (nome.validity.valid) {
        nome.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});


descrizione.addEventListener('input', function (event) {

    if (descrizione.validity.valid) {
        descrizione.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});


form.addEventListener('submit', function (event) {
    if(!nome.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!descrizione.validity.valid) {
        showError();
        event.preventDefault();
    }


});

function showError() {
    if(nome.validity.valueMissing) {
        nome.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(nome.validity.typeMismatch) {
        nome.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(nome.validity.tooLong) {
        nome.nextElementSibling.textContent = 'nome deve essere al massimo di 30 caratteri';
    } else if(nome.validity.patternMismatch) {
        nome.nextElementSibling.textContent = 'il nome non deve contenere numeri o caratteri speciali ';
    }

    if(descrizione.validity.valueMissing) {
        descrizione.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(descrizione.validity.typeMismatch) {
        descrizione.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(descrizione.validity.tooLong) {
        descrizione.nextElementSibling.textContent = 'descrizione deve essere al massimo di 200 caratteri';
    }else if(descrizione.validity.patternMismatch) {
        descrizione.nextElementSibling.textContent = 'descrizione non deve contenere caratteri speciali ';
    }


}
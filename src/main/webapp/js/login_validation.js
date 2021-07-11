const form=document.getElementsByTagName("form")[0];

const email = document.getElementById('email');
const pw = document.getElementById('pw');


email.addEventListener('input', function (event) {

    if (email.validity.valid) {
        email.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});

pw.addEventListener('input', function (event) {

    if (pw.validity.valid) {
        pw.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});

form.addEventListener('submit', function (event) {
    if(!email.validity.valid) {
        showError();
        event.preventDefault();
    }
    if(!pw.validity.valid) {
        showError();
        event.preventDefault();
    }
});

function showError() {
    if(email.validity.valueMissing) {
        email.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(email.validity.typeMismatch) {
        email.nextElementSibling.textContent = 'è necessario inserire un indirizzo email';
    } else if(email.validity.patternMismatch){
        email.nextElementSibling.textContent = 'email deve rispettare il pattern adeguato';
    } else if(email.validity.tooLong) {
        email.nextElementSibling.textContent = 'email deve essere al massimo di 50 caratteri';
    }

    if(pw.validity.valueMissing) {
        pw.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(pw.validity.patternMismatch) {
        pw.nextElementSibling.textContent = 'la password deve essere di almeno 8 caratteri e contenere una maiuscola, una minuscola, un numero ed un carattere speciale';
    } else if(pw.validity.tooShort) {
        pw.nextElementSibling.textContent = 'la password deve essere al minimo di 8 characters';
    }

}
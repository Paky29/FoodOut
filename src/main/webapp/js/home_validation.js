const form=document.getElementsByTagName("form")[0];

const citta = document.getElementById('citta');

citta.addEventListener('input', function (event) {

    if (citta.validity.valid) {
        citta.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});


form.addEventListener('submit', function (event) {
    if(!citta.validity.valid) {
        showError();
        event.preventDefault();
    }
});

function showError() {
    if(citta.validity.valueMissing) {
        citta.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(citta.validity.typeMismatch) {
        citta.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(citta.validity.patternMismatch){
        citta.nextElementSibling.textContent = 'citta non deve contenere numeri o caratteri speciali';
    } else if(citta.validity.tooLong) {
        citta.nextElementSibling.textContent = 'citta deve essere al massimo di 30 caratteri';
    }


}
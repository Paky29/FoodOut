const form=document.getElementsByTagName("form")[0];

const saldo = document.getElementById('deposito');

saldo.addEventListener('input', function (event) {

    if (saldo.validity.valid) {
        saldo.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});


form.addEventListener('submit', function (event) {
    if(!saldo.validity.valid) {
        showError();
        event.preventDefault();
    }
});

function showError() {
    if(saldo.validity.valueMissing) {
        saldo.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(saldo.validity.typeMismatch) {
        saldo.nextElementSibling.textContent = 'è necessario inserire un numero';
    } else if(saldo.validity.rangeUnderflow){
        saldo.nextElementSibling.textContent = 'saldo deve essere un numero positivo ';
    } else if(saldo.validity.patternMismatch){
        saldo.nextElementSibling.textContent = 'saldo deve essere un numero intero o un numero con la virgola positivo ';
    }


}
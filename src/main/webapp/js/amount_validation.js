const formProd=document.getElementsByTagName("form")[1];
const formMenu=document.getElementsByTagName("form")[2];

const qProd = document.getElementById('quantitaProd');
const qMenu = document.getElementById('quantitaMenu');

qProd.addEventListener('input', function (event) {

    if (qProd.validity.valid) {
        qProd.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});

qMenu.addEventListener('input', function (event) {

    if (qMenu.validity.valid) {
        qMenu.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});


formProd.addEventListener('submit', function (event) {
    if(!qProd.validity.valid) {
        showError();
        event.preventDefault();
    }
});

formMenu.addEventListener('submit', function (event) {
    if(!qMenu.validity.valid) {
        showErrorProd();
        event.preventDefault();
    }
});

function showErrorProd() {
    if(qProd.validity.valueMissing) {
        qProd.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(qProd.validity.typeMismatch) {
        qProd.nextElementSibling.textContent = 'è necessario inserire un numero';
    } else if(qProd.validity.rangeUnderflow){
        qProd.nextElementSibling.textContent = 'saldo deve essere un numero positivo ';
    } else if(qProd.validity.rangeOverflow){
        qProd.nextElementSibling.textContent = 'saldo deve essere un numero intero o un numero con la virgola positivo ';
    }

}

function showErrorProd() {
    if(qMenu.validity.valueMissing) {
        qMenu.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(qMenu.validity.typeMismatch) {
        qMenu.nextElementSibling.textContent = 'è necessario inserire un numero';
    } else if(qMenu.validity.rangeUnderflow){
        qMenu.nextElementSibling.textContent = 'saldo deve essere un numero positivo ';
    } else if(qMenu.validity.rangeOverflow){
        qMenu.nextElementSibling.textContent = 'saldo deve essere un numero intero o un numero con la virgola positivo ';
    }

}
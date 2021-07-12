const form=document.getElementsByTagName("form")[0];

const cash=document.getElementById('cash');
const saldo=document.getElementById('saldo');
const nota=document.getElementById("nota");

nota.addEventListener('input', function (event) {

    if (nota.validity.valid) {
        nota.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});

form.addEventListener('submit', function (event) {
    if(!nota.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!cash.checked && !saldo.checked){
        alert("è necessario selezionare un metodo di pagamento");
        event.preventDefault();
    }

     if(nota.validity.typeMismatch) {
        nota.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(nota.validity.tooLong) {
        nota.nextElementSibling.textContent = 'nota deve essere al massimo di 150 caratteri';
    }else if(nota.validity.patternMismatch) {
        nota.nextElementSibling.textContent = 'nota non deve contenere caratteri speciali ';
    }


});

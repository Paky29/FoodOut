const form=document.getElementsByTagName("form")[0];
const old_pw = document.getElementById('old_pw');
const new_pw = document.getElementById('new_pw');
const conf_pw = document.getElementById('conf_pw');

old_pw.addEventListener('input', function (event) {

    if (old_pw.validity.valid) {
        old_pw.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});

new_pw.addEventListener('input', function (event) {

    if (new_pw.validity.valid) {
        new_pw.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});

conf_pw.addEventListener('input', function (event) {

    if (conf_pw.validity.valid) {
        conf_pw.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});

form.addEventListener('submit', function (event) {

    if(!old_pw.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!new_pw.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!conf_pw.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(conf_pw.value!=new_pw.value){
        alert("Nuova password diversa da conferma password");
        event.preventDefault();
    }
});

function showError() {

    if(old_pw.validity.valueMissing) {
        old_pw.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(old_pw.validity.patternMismatch) {
        old_pw.nextElementSibling.textContent = 'la password deve essere di almeno 8 caratteri e contenere una maiuscola, una minuscola, un numero';
    } else if(old_pw.validity.tooShort) {
        old_pw.nextElementSibling.textContent = 'la password deve essere almeno di 8 caratteri';
    }

    if(new_pw.validity.valueMissing) {
        new_pw.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(new_pw.validity.patternMismatch) {
        new_pw.nextElementSibling.textContent = 'la password deve essere di almeno 8 caratteri e contenere una maiuscola, una minuscola, un numero';
    } else if(new_pw.validity.tooShort) {
        new_pw.nextElementSibling.textContent = 'la password deve essere almeno di 8 caratteri';
    }

    if(conf_pw.validity.valueMissing) {
        conf_pw.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(conf_pw.validity.patternMismatch) {
        conf_pw.nextElementSibling.textContent = 'la password deve essere di almeno 8 caratteri e contenere una maiuscola, una minuscola, un numero';
    } else if(conf_pw.validity.tooShort) {
        conf_pw.nextElementSibling.textContent = 'la password deve essere almeno di 8 caratteri';
    }

}
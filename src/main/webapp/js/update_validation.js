const form=document.getElementsByTagName("form")[0];
const nome=document.getElementById('nome');
const cognome=document.getElementById('cognome');
const provincia=document.getElementById('provincia');
const citta=document.getElementById('citta');
const via=document.getElementById('via');
const civico=document.getElementById('civico');
const email = document.getElementById('email');

nome.addEventListener('input', function (event) {

    if (nome.validity.valid) {
        nome.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});


cognome.addEventListener('input', function (event) {

    if (cognome.validity.valid) {
        cognome.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});

provincia.addEventListener('input', function (event) {

    if (provincia.validity.valid) {
        provincia.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});


citta.addEventListener('input', function (event) {

    if (citta.validity.valid) {
        citta.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});


via.addEventListener('input', function (event) {

    if (via.validity.valid) {
        via.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});


civico.addEventListener('input', function (event) {

    if (civico.validity.valid) {
        civico.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});


email.addEventListener('input', function (event) {

    if (email.validity.valid) {
        email.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});


form.addEventListener('submit', function (event) {
    if(!nome.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!cognome.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!provincia.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!citta.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!via.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!civico.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!email.validity.valid) {
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

    if(cognome.validity.valueMissing) {
        cognome.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(cognome.validity.typeMismatch) {
        cognome.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(cognome.validity.tooLong) {
        nome.nextElementSibling.textContent = 'cognome deve essere al massimo di 30 caratteri';
    } else if(cognome.validity.patternMismatch) {
        cognome.nextElementSibling.textContent = 'cognome non deve contenere numeri o caratteri speciali ';
    }

    if(provincia.validity.valueMissing) {
        provincia.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(provincia.validity.typeMismatch) {
        provincia.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(provincia.validity.tooLong) {
        provincia.nextElementSibling.textContent = 'provincia deve essere al massimo di 30 caratteri';
    }else if(provincia.validity.patternMismatch) {
        provincia.nextElementSibling.textContent = 'provincia non deve contenere numeri o caratteri speciali ';
    }

    if(citta.validity.valueMissing) {
        citta.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(citta.validity.typeMismatch) {
        citta.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(citta.validity.tooLong) {
        citta.nextElementSibling.textContent = 'citta deve essere al massimo di 30 caratteri';
    }else if(citta.validity.patternMismatch) {
        citta.nextElementSibling.textContent = 'citta non deve contenere numeri o caratteri speciali ';
    }


    if(via.validity.valueMissing) {
        via.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(via.validity.typeMismatch) {
        via.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(via.validity.tooLong) {
        via.nextElementSibling.textContent = 'via deve essere al massimo di 50 caratteri';
    } else if(via.validity.patternMismatch) {
        via.nextElementSibling.textContent = 'via non deve contenere caratteri speciali ';
    }

    if(civico.validity.valueMissing) {
        civico.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(civico.validity.typeMismatch) {
        civico.nextElementSibling.textContent = 'è necessario inserire un numero intero';
    } else if(civico.validity.rangeUnderflow) {
        civico.nextElementSibling.textContent = 'civico deve essere un numero maggiore di 0';
    }

    if(email.validity.valueMissing) {
        email.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(email.validity.typeMismatch) {
        email.nextElementSibling.textContent = 'è necessario inserire un indirizzo email';
    } else if(email.validity.patternMismatch){
        email.nextElementSibling.textContent = 'email deve rispettare il pattern adeguato';
    } else if(email.validity.tooLong) {
        email.nextElementSibling.textContent = 'email deve essere al massimo di 50 caratteri';
    }
}
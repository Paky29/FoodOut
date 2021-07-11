const form=document.getElementsByTagName("form")[0];
const nome=document.getElementById('nome');
const provincia=document.getElementById('provincia');
const citta=document.getElementById('citta');
const via=document.getElementById('via');
const civico=document.getElementById('civico');
const sm=document.getElementById("spesaMinima");
const tc=document.getElementById("tassoConsegna");
const url=document.getElementById("urlImmagine");
const info=document.getElementById("info")

nome.addEventListener('input', function (event) {

    if (nome.validity.valid) {
        nome.nextElementSibling.textContent = '';
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

sm.addEventListener('input', function (event) {

    if (sm.validity.valid) {
        sm.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});

tc.addEventListener('input', function (event) {

    if (tc.validity.valid) {
        tc.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});


url.addEventListener('input', function (event) {

    if (url.validity.valid) {
        url.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});

info.addEventListener('input', function (event) {

    if (info.validity.valid) {
        info.nextElementSibling.textContent = '';
    } else {
        showError();
    }
});

form.addEventListener('submit', function (event) {
    if(!nome.validity.valid) {
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

    if(!sm.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!tc.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!url.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!info.validity.valid) {
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

    if(sm.validity.valueMissing) {
        sm.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(sm.validity.typeMismatch) {
        sm.nextElementSibling.textContent = 'è necessario inserire un numero intero';
    } else if(sm.validity.rangeUnderflow) {
        sm.nextElementSibling.textContent = 'spesa minima deve essere un numero maggiore o uguale a 0';
    } else if(sm.validity.patternMismatch){
        sm.nextElementSibling.textContent = 'spesa minima deve essere un numero intero o decimale positivo';
    }

    if(tc.validity.valueMissing) {
        tc.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(tc.validity.typeMismatch) {
        tc.nextElementSibling.textContent = 'è necessario inserire un numero intero';
    } else if(tc.validity.rangeUnderflow) {
        tc.nextElementSibling.textContent = 'tasso consegna deve essere un numero maggiore o uguale a 0';
    } else if(tc.validity.patternMismatch){
        tc.nextElementSibling.textContent = 'tasso consegna deve essere un numero intero o decimale positivo';
    }

    if(url.validity.valueMissing) {
        url.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(url.validity.typeMismatch) {
        info.nextElementSibling.textContent = "è necessario inserire un'immagine";
    }

    if(info.validity.valueMissing) {
        info.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(info.validity.typeMismatch) {
        info.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(info.validity.tooLong) {
        info.nextElementSibling.textContent = 'info deve essere al massimo di 200 caratteri';
    } else if(info.validity.patternMismatch) {
        info.nextElementSibling.textContent = 'info non deve contenere caratteri speciali ';
    }


}
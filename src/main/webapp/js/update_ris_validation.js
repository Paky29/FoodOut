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

form.addEventListener('submit', function (event) {
    if(!form.checkValidity()){
        showError();
        event.preventDefault();
    }


});

function showError() {
    if(nome.validity.valueMissing) {
        alert('nome campo obbligatorio');
    } else if(nome.validity.typeMismatch) {
        alert('è necessario inserire una stringa per nome');
    } else if(nome.validity.tooLong) {
        alert('nome deve essere al massimo di 30 caratteri');
    } else if(nome.validity.patternMismatch) {
        alert('nome non deve contenere numeri o caratteri speciali ');
    }

    if(provincia.validity.valueMissing) {
        alert('provincia campo obbligatorio');
    } else if(provincia.validity.typeMismatch) {
        alert('è necessario inserire una stringa per provincia');
    } else if(provincia.validity.tooLong) {
        alert('provincia deve essere al massimo di 30 caratteri');
    }else if(provincia.validity.patternMismatch) {
        alert('provincia non deve contenere numeri o caratteri speciali ');
    }

    if(citta.validity.valueMissing) {
        alert('citta campo obbligatorio');
    } else if(citta.validity.typeMismatch) {
        alert('è necessario inserire una stringa per citta');
    } else if(citta.validity.tooLong) {
        alert('citta deve essere al massimo di 30 caratteri');
    }else if(citta.validity.patternMismatch) {
        alert('citta non deve contenere numeri o caratteri speciali ');
    }


    if(via.validity.valueMissing) {
        alert('via campo obbligatorio');
    } else if(via.validity.typeMismatch) {
        alert('è necessario inserire una stringa per via');
    } else if(via.validity.tooLong) {
        alert('via deve essere al massimo di 50 caratteri');
    } else if(via.validity.patternMismatch) {
        alert('via non deve contenere caratteri speciali ');
    }

    if(civico.validity.valueMissing) {
        alert('civico campo obbligatorio');
    } else if(civico.validity.typeMismatch) {
        alert('è necessario inserire un numero intero per civico');
    } else if(civico.validity.rangeUnderflow) {
        alert('civico deve essere un numero maggiore di 0');
    }

    if(sm.validity.valueMissing) {
        alert('campo obbligatorio');
    } else if(sm.validity.typeMismatch) {
        alert('è necessario inserire un numero intero');
    } else if(sm.validity.rangeUnderflow) {
        alert('spesa minima deve essere un numero maggiore o uguale a 0');
    } else if(sm.validity.patternMismatch){
        alert('spesa minima deve essere un numero intero o decimale positivo');
    }

    if(tc.validity.valueMissing) {
        alert('campo obbligatorio');
    } else if(tc.validity.typeMismatch) {
        alert('è necessario inserire un numero intero');
    } else if(tc.validity.rangeUnderflow) {
        alert('tasso consegna deve essere un numero maggiore o uguale a 0');
    } else if(tc.validity.patternMismatch){
        alert('tasso consegna deve essere un numero intero o decimale positivo');
    }

     if(url.validity.typeMismatch) {
        alert('è necessario inserire file di tipo immagine');
    }

    if(info.validity.valueMissing) {
        alert('info campo obbligatorio');
    } else if(info.validity.typeMismatch) {
        alert('è necessario inserire una stringa');
    } else if(info.validity.tooLong) {
        alert('info deve essere al massimo di 200 caratteri');
    } else if(info.validity.patternMismatch) {
        alert('info non deve contenere caratteri speciali ');
    }


}
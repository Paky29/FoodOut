const formProd=document.getElementsByTagName("form")[0];
const formMenu=document.getElementsByTagName("form")[1];

const nomeProd = document.getElementById('nomeProd');
const prezzoProd = document.getElementById('prezzoProd');
const scontoProd = document.getElementById('scontoProd');
const url = document.getElementById('urlImmagine');
const ingredienti = document.getElementById('ingredienti');
const info = document.getElementById('info');

const nomeMenu = document.getElementById('nomeMenu');
const prezzoMenu = document.getElementById('prezzoMenu');
const scontoMenu = document.getElementById('scontoMenu');
const prodottiMenu=document.getElementById('prodotti');


nomeProd.addEventListener('input', function (event) {

    if (nomeProd.validity.valid) {
        nomeProd.nextElementSibling.textContent = '';
    } else {
        showErrorProd();
    }
});

prezzoProd.addEventListener('input', function (event) {

    if (prezzoProd.validity.valid) {
        prezzoProd.nextElementSibling.textContent = '';
    } else {
        showErrorProd();
    }
});

scontoProd.addEventListener('input', function (event) {

    if (scontoProd.validity.valid) {
        scontoProd.nextElementSibling.textContent = '';
    } else {
        showErrorProd();
    }
});

url.addEventListener('submit', function (event) {
    if (url.validity.valid) {
        url.nextElementSibling.textContent = '';
    } else {
        showErrorProd();
    }
});

ingredienti.addEventListener('submit', function (event) {
    if (ingredienti.validity.valid) {
        ingredienti.nextElementSibling.textContent = '';
    } else {
        showErrorProd();
    }
});

info.addEventListener('submit', function (event) {
    if (info.validity.valid) {
        info.nextElementSibling.textContent = '';
    } else {
        showErrorProd();
    }
});

nomeMenu.addEventListener('input', function (event) {

    if (nomeMenu.validity.valid) {
        nomeMenu.nextElementSibling.textContent = '';
    } else {
        showErrorMenu();
    }
});

prezzoMenu.addEventListener('input', function (event) {

    if (prezzoMenu.validity.valid) {
        prezzoMenu.nextElementSibling.textContent = '';
    } else {
        showErrorMenu();
    }
});

scontoMenu.addEventListener('input', function (event) {

    if (scontoMenu.validity.valid) {
        scontoMenu.nextElementSibling.textContent = '';
    } else {
        showErrorMenu();
    }
});

formProd.addEventListener('submit', function (event) {
    if(!nomeProd.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!prezzoProd.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!scontoProd.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!url.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!ingredienti.validity.valid) {
        showError();
        event.preventDefault();
    }

    if(!info.validity.valid) {
        showError();
        event.preventDefault();
    }

});

formMenu.addEventListener('submit', function (event) {
    if(!nomeMenu.validity.valid) {
        showErrorMenu();
        event.preventDefault();
    }

    if(!prezzoMenu.validity.valid) {
        showErrorMenu();
        event.preventDefault();
    }

    if(!scontoMenu.validity.valid) {
        showErrorMenu();
        event.preventDefault();
    }

    var count_selected=0;
    for(let i=0; i<prodottiMenu.length; i++){
        if(prodottiMenu[i].selected==true)
            count_selected+=1;
    }

    if(count_selected<2){
        alert("il menu deve essere composto da almeno due prodotti");
        event.preventDefault();
    }



});

function showErrorProd() {
    if(nomeProd.validity.valueMissing) {
        nomeProd.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(nomeProd.validity.typeMismatch) {
        nomeProd.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(nomeProd.validity.tooLong) {
        nomeProd.nextElementSibling.textContent = 'nome deve essere al massimo di 30 caratteri';
    } else if(nomeProd.validity.patternMismatch) {
        nomeProd.nextElementSibling.textContent = 'il nome non deve contenere caratteri speciali ';
    }

    if(prezzoProd.validity.valueMissing) {
        prezzoProd.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(prezzoProd.validity.typeMismatch) {
        prezzoProd.nextElementSibling.textContent = 'è necessario inserire un numero intero';
    } else if(prezzoProd.validity.rangeUnderflow) {
        prezzoProd.nextElementSibling.textContent = 'prezzo deve essere un numero maggiore di 0';
    } else if(prezzoProd.validity.patternMismatch){
        prezzoProd.nextElementSibling.textContent = 'prezzo deve essere un numero intero o decimale positivo';
    }

    if(scontoProd.validity.valueMissing) {
        scontoProd.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(scontoProd.validity.typeMismatch) {
        scontoProd.nextElementSibling.textContent = 'è necessario inserire un numero intero';
    } else if(scontoProd.validity.rangeUnderflow) {
        scontoProd.nextElementSibling.textContent = 'sconto deve essere un numero maggiore o uguale a 0';
    } else if(scontoProd.validity.patternMismatch){
        scontoProd.nextElementSibling.textContent = 'sconto deve essere un numero intero o decimale positivo';
    }

    if(url.validity.valueMissing) {
        url.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(url.validity.typeMismatch) {
        info.nextElementSibling.textContent = "è necessario inserire un'immagine";
    }

    if(ingredienti.validity.valueMissing) {
        ingredienti.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(ingredienti.validity.typeMismatch) {
        ingredienti.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(ingredienti.validity.tooLong) {
        ingredienti.nextElementSibling.textContent = 'ingredienti deve essere al massimo di 100 caratteri';
    }

    if(info.validity.valueMissing) {
        info.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(info.validity.typeMismatch) {
        info.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(info.validity.tooLong) {
        info.nextElementSibling.textContent = 'info deve essere al massimo di 100 caratteri';
    }

}

function showErrorMenu() {
    if(nomeMenu.validity.valueMissing) {
        nomeMenu.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(nomeMenu.validity.typeMismatch) {
        nomeMenu.nextElementSibling.textContent = 'è necessario inserire una stringa';
    } else if(nomeMenu.validity.tooLong) {
        nomeMenu.nextElementSibling.textContent = 'nome deve essere al massimo di 30 caratteri';
    } else if(nomeMenu.validity.patternMismatch) {
        nomeMenu.nextElementSibling.textContent = 'il nome non deve contenere  caratteri speciali ';
    }

    if(prezzoMenu.validity.valueMissing) {
        prezzoMenu.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(prezzoMenu.validity.typeMismatch) {
        prezzoMenu.nextElementSibling.textContent = 'è necessario inserire un numero intero';
    } else if(prezzoMenu.validity.rangeUnderflow) {
        prezzoMenu.nextElementSibling.textContent = 'prezzo deve essere un numero maggiore di 0';
    } else if(prezzoMenu.validity.patternMismatch){
        prezzoMenu.nextElementSibling.textContent = 'prezzo deve essere un numero intero o decimale positivo';
    }

    if(scontoMenu.validity.valueMissing) {
        scontoMenu.nextElementSibling.textContent = 'campo obbligatorio';
    } else if(scontoMenu.validity.typeMismatch) {
        scontoMenu.nextElementSibling.textContent = 'è necessario inserire un numero intero';
    } else if(scontoMenu.validity.rangeUnderflow) {
        scontoMenu.nextElementSibling.textContent = 'sconto deve essere un numero maggiore o uguale a 0';
    } else if(scontoMenu.validity.patternMismatch){
        scontoMenu.nextElementSibling.textContent = 'sconto deve essere un numero intero o decimale positivo';
    }


}
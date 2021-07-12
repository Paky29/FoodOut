const form=document.getElementsByTagName("form")[0];


form.addEventListener('submit', function (event) {
    const giorni = ["LUNEDI", "MARTEDI", "MERCOLEDI", "GIOVEDI", "VENERDI", "SABATO", "DOMENICA"]
    for(let i=0; i<7; i++){
        var ap=document.getElementById("apertura"+i).value;
        var ch=document.getElementById("chiusura"+i).value;
        var chiuso=document.getElementById("chiuso"+i).checked;

        if(chiuso==false && (ap=='' || ch=='')){
            alert(giorni[i] + ": ora Apertura e ora Chiusura sono obbligatorie se il ristorante non è chiuso");
            event.preventDefault();
        }

        if(chiuso==false && ap>=ch){
            alert(giorni[i]  + ": ora Apertura deve essere precedente ad ora Chiusura");
            event.preventDefault();
        }


    }


});
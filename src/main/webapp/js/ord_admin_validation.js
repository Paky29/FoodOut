const form=document.getElementsByTagName("form")[0];



form.addEventListener('submit', function (event) {
    const oraPartenza=document.getElementById("oraPartenza").value;
    const oraArrivo=document.getElementById("oraArrivo").value;

    if(oraPartenza=='' && oraArrivo!=''){
            alert("oraArrivo non può essere inserita senza oraPartenza");
            event.preventDefault();
        }

        if(oraPartenza>oraArrivo){
            alert("ora Partenza deve essere precedente ad ora Arrivo");
            event.preventDefault();
        }


});
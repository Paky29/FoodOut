function dictionary(){
    return {
        'patternMismatch': 'Formato non valido',
        'rangeOverFlow': 'Il valore è più grande di %s',
        'rangeUnderFlow': 'Il valore è più piccolo di %s',
        'tooLong': 'Non deve superare lunghezza %s caratteri',
        'stepMismatch': 'Deve avere uno step di %s',
        'valueMissing': 'Campo obbligatorio'
    };
}

function validateForm(form){
    const d = dictionary();

    function _reportError(event){
        const el=event.target;
        const errors = [];
        if(el.validity.tooShort){
            errors.push(d['tooShort'].replace("%s", el.getAttribute("minlength")));
        }

        if(el.validity.tooLong){
            errors.push(d['tooLong'].replace("%s", el.getAttribute("maxLength")));
        }

        if(el.validity.patternMismatch){
            errors.push(d["patternMismatch"]);
        }

        if(el.validity.valueMissing){
            errors.push(d["valueMissing"]);
        }

        if(el.validity.rangeUnderflow){
            errors.push(d["rangeUnderFlow"].replace("%s", el.getAttribute("min")));
        }

        if(el.validity.rangeOverflow){
            errors.push(d["rangeOverFlow"].replace("%s", el.getAttribute("max")));
        }

        if(el.validity.stepMismatch){
            errors.push(d["stepMismatch"].replace("%s", el.getAttribute("step")));
        }

        /*var err_statement=null;
        for(let i=0; i<errors.length; i++){
            err_statement=err_statement+"|"+ errors[i];
        }
        el.parentElement.nextElementSibling.textContent = err_statement;*/

        el.parentElement.nextElementSibling.textContent = errors.join('|');
    }

    function reset(event){
        const el=event.target;
        el.parentElement.nextElementSibling.textContent  = '';
    }

    form.setAttribute("novalidate", "true");
    const inputs = form.elements;
    for(let i=0; i<inputs.length; i++){
        let isValidInput = inputs[i].nodeName.match("INPUT|TEXTAREA|SELECT");
        if(inputs[i].willValidate!='undefined' && isValidInput){
            inputs[i].addEventListener('invalid', _reportError);
            inputs[i].addEventListener('focus', reset);
        }
    }

    form.addEventListener('submit', function (event){
        if(form.checkValidity()){
            form.submit();
        }
        else{
            event.preventDefault();
        }
    });

}


var KEYCODE_ESC = 27;
var KEYCODE_ENTER = 13;
 
function showErro(msg){
    $('#centralMessage').addClass("erroneous").show(500).children().text(msg);
}

function showInfo(msg){
    $('#centralMessage').addClass("informe").show(500).children().text(msg);
}

function showWarning(msg){
    $('#centralMessage').addClass("warning").show(500).children().text(msg);
}

function hideMessage(){
    $('#centralMessage').hide();
}

function disableButton(id){
    $(id).attr("disabled", "disabled");
}

function enableButton(id){
    $(id).removeAttr("disabled");
}

function maskare(id, mask){
    jQuery(function($){
        $(id).mask(mask);
    });
    
}

function initULightBox(){
    uLightBox.init({
        override:true,
        background: 'black',
        centerOnResize: true,
        fade: true
    });
}

function initLoginBox(){
    uLoginBox.init({
        });
}

function hasError(str){
    return str.indexOf("ERRO:") != -1;
}

function errorValue(str){
    return str.substr(str.indexOf("ERRO:") + 5);
}

function disableInput(id){
    $(id).attr("disabled", true);
    $(id).addClass("inputDisabled");
}

function enableInput(id){
    $(id).attr("disabled", false);
    $(id).removeClass("inputDisabled");
}

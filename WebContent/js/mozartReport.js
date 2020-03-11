function loading(){
       var divLoading = "<div id=\"divLoading\" style=\"height:50px; width:310px;\"class=\"divLoading\"><img src=\"imagens/loading.gif\" width=\"30px\" height=\"30px\" /><p>Carregando, por favor aguarde.</p></div>";
       showModal (divLoading);   
}

function showModal(div){
	
	killModal();
    if (div.indexOf('#') == 0){
        $(div).modal({close: false});
    }else{
        if ($.modal != null){
            $.modal(div);
        }
    }
}


function killModal(){
	 if ($.modal!=null){
            $.modal.close();
      }
}




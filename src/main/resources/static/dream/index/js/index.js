$("li a").click(function(){
    var url = $(this).attr("url");
    if(url == null)return ;
    var f = $("#iFramePage");
    switch (url){
        case "nxgc":
            f.attr("src","/dream/autoCreateFile/autoCreateFile.html");
            break;
    }
});

// include('https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js');
// include('https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css');
/***********************  添加css ******************************/
include('http://at.alicdn.com/t/font_757872_vmz32fwh15k.css'); //阿里巴巴iconfont
include('/layui-2.3.0/css/layui.css'); //layui
include('/css/onlyDream.css');

/***********************  添加js ******************************/
include("/js/jquery-3.3.1.min.js"); //jQuery
include('/layui-2.3.0/layui.js'); //layui

function include(file) {
    var patrn = /.js$/;
    if(patrn.exec(file)){
        document.write('<script type="text/javascript" src="'+file+'"></script>');
    }
    patrn = /.css$/;
    if(patrn.exec(file)){
        document.write('<link type="text/css" rel="stylesheet" href="'+file+'">');
    }

}
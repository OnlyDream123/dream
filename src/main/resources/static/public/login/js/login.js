var layer;
layui.use('layer', function(){
    layer = layui.layer;
});
layui.use('form', function(){
    var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功

});
var u = navigator.userAgent;
if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {//安卓手机
    $(".pc").remove();$(".iphone").show();
} else if (u.indexOf('iPhone') > -1) {//苹果手机
    $(".pc").remove();$(".iphone").show();
} else if (u.indexOf('Windows Phone') > -1) {//winphone手机
    $(".pc").remove();$(".iphone").show();
}else{
    $(".iphone").remove();$(".pc").show();
}
/*******************  动态背景-代码开始 ************************/
var bgM,bgN=0;
$(function(){
    bgM = $(".bg").length;
    dy();
    setInterval(function () {
        dy();
    },20100);
});
function dy() {
    var bg = $(".showBg");
    var wValue=1.5 * bg.width();
    var hValue=1.5 * bg.height();
    bg.animate({width: wValue,
        height: hValue,
        left:("-"+(0.5 * bg.width())/2),
        top:("-"+(0.5 * bg.height())/2)}, 17000);
    setTimeout(function () {
        bgN++;
        if(bgN === bgM)bgN=0;
        $(".bg").eq(bgN).fadeIn(2000).addClass("showBg").siblings(".bg").fadeOut(2000).removeClass("showBg");
        setTimeout(function () {

            bg.css({width: "100%",
                height: "100%",
                left:"0px",
                top:"0px"});
        },2000);
        // $(".bg").eq(bgN).sibling().hide().removeClass("showBg");
        // bg.animate({width: "100%",
        //     height: "100%",
        //     left:"0px",
        //     top:"0px"}, 10000 );

    },17050)
}
/*******************  动态背景-代码结束 ************************/
/***  显示登录界面 ***/
function showLogin() {

    var register = $(".register");
    var login = $(".login");

    login.animate({"right": register.css("right")});
    register.animate({"right":"-400px"});
}

/***  显示注册界面 ***/
function showRegister() {
    var register = $(".register");
    var login = $(".login");
    register.animate({"right": login.css("right")});
    login.animate({"right":"-400px"});
}

/********  登录 *********/
function login() {
    var userName = $("#userName").val();
    var userPassword = $("#userPassword").val();
    if(userName === "admin" && userPassword === "123456"){
        layer.alert('登录成功', {icon: 6});
    }else{
        layer.alert('登录失败', {icon: 2});
    }
}

/******** 注册 *********/
function register() {
    layer.alert('注册成功', {icon: 6});
}
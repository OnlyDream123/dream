/*** 获取xml配置 **/
function getData() {
    $.get("/dream/autoCreateFile", function (result) {
        if (result.code === 0) {
            var r = result.data;
            $("input[name='driverClass']").val(r.driverClass);
            $("input[name='connectionURL']").val(r.connectionURL);
            $("input[name='userId']").val(r.userId);
            $("input[name='password']").val(r.password);
            $("input[name='database']").val(r.database);
            $("input[name='tableName']").val(r.tableName);
            $("input[name='pathModel']").val(r.pathModel);
            $("input[name='pathController']").val(r.pathController);
            $("input[name='pathDao']").val(r.pathDao);
            $("input[name='pathService']").val(r.pathService);
            $("input[name='pathServiceImpl']").val(r.pathServiceImpl);
            $("input[name='pathMapper']").val(r.pathMapper);
            $("input[name='pathHtml']").val(r.pathHtml);
            $("input[name='pathFtl']").val(r.pathFtl);
            layer.close(loading);
        } else {
            layer.close(loading);
            layer.alert(result.msg);
        }
    });
}
var layer;
var loading;
var form;
layui.use('layer', function(){
    layer = layui.layer;
    //iframe层-禁滚动条

    loading = layer.open({
        type: 2,
        title: false,
        closeBtn: 0,
        skin: "background-none",
        area: ['100%', '100%'],
        // skin: 'layui-layer-rim', //加上边框
        content: ['/html/loading.html', 'no']
    });
    getData();
    // layer.close(loading);

});
layui.use('form', function () {
    var form = layui.form;

});

function getModel() {
    this.driverClass = $("input[name='driverClass']").val();
    this.connectionURL = $("input[name='connectionURL']").val();
    this.userId = $("input[name='userId']").val();
    this.password = $("input[name='password']").val();
    this.database = $("input[name='database']").val();
    this.tableName = $("input[name='tableName']").val();
    this.pathModel = $("input[name='pathModel']").val();
    this.pathController = $("input[name='pathController']").val();
    this.pathDao = $("input[name='pathDao']").val();
    this.pathService = $("input[name='pathService']").val();
    this.pathServiceImpl = $("input[name='pathServiceImpl']").val();
    this.pathMapper = $("input[name='pathMapper']").val();
    this.pathHtml = $("input[name='pathHtml']").val();
    this.pathFtl = $("input[name='pathFtl']").val();
    this.pathControllerIf=$("#pathControllerIf").is(":checked");
    this.pathServiceIf=$("#pathServiceIf").is(":checked");
    this.pathServiceImplIf=$("#pathServiceImplIf").is(":checked");
    this.pathHtmlIf=$("#pathHtmlIf").is(":checked");
}
/**** 测试连接 ****/
function testConnect() {
    var model = new getModel();
    $.get("/dream/autoCreateFile/testConnect",{"connectionURL":model.connectionURL,"userId":model.userId,"password":model.password},function (r) {
        if (r.code === 0) {
            layer.alert("连接成功",{icon:1});
        }else{
            layer.alert(r.msg,{icon:2});
        }
    })
}

/** 获取表结构 */
function getDatabaseModel() {
    var model = new getModel();
    $.get("/dream/autoCreateFile/getDatabaseModel",{"connectionURL":model.connectionURL,"userId":model.userId,"password":model.password,"database":model.database,"tableName":model.tableName},function (r) {
        if (r.code === 0) {
            $("#databaseModelData").empty();
            var data = r.data;
            for(var i=0; i<data.length; i++) {
                var d = data[i];
                $("#databaseModelData").append("<tr><td>"+d.name+"</td><td>"+d.type+"</td><td>"+d.comment+"</td>")
            }

            layer.open({
                type: 1,
                title: false,
                closeBtn: 0,
                shadeClose: true,
                area: ['500px', '300px'],
                content: $("#databaseModel").html()
            });
        }else{
            layer.alert(r.msg,{icon:2});
        }
    })
}

/** 保存配置 */
function saveModel() {
    var serialize = $("#xmlData").serialize();
    var loading1 = layer.open({
        type: 2,
        title: false,
        closeBtn: 0,
        skin: "background-none",
        area: ['100%', '100%'],
        // skin: 'layui-layer-rim', //加上边框
        content: ['/html/loading.html', 'no']
    });
    $.get("/dream/autoCreateFile/saveModel?"+serialize,function (r) {
        if(r.code === 0){
            layer.alert("保存成功",{icon:1})
        }else{
            layer.alert(r.msg,{icon:2});
        }
        layer.close(loading1);
    })
}

/** 开始生成文件 */
function createFile() {
    var mo = new getModel();
    var d = "&pathControllerIf="+mo.pathControllerIf+"&pathServiceIf="+mo.pathServiceIf+"&pathServiceImplIf="+mo.pathServiceImplIf+"&pathHtmlIf="+mo.pathHtmlIf;
    var serialize = $("#xmlData").serialize()+d;
    var loading1 = layer.open({
        type: 2,
        title: false,
        closeBtn: 0,
        skin: "background-none",
        area: ['100%', '100%'],
        // skin: 'layui-layer-rim', //加上边框
        content: ['/html/loading.html', 'no']
    });
    $.get("/dream/autoCreateFile/createFile?"+serialize,function (r) {
        if(r.code === 0){
            layer.alert("成功",{icon:1})
        }else{
            layer.alert(r.msg,{icon:2});
        }
        layer.close(loading1);
    })
}
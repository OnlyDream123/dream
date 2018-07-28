<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>OnlyDream</title>
    <script src="/OnlyDream.js"></script>
</head>
<body>
<table class="layui-hide" id="dreamTable"></table>
</body>
<script>
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem: '#dreamTable'
            ,url:'/dream/${tableNameNoDream}'
            ,page:true
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,id: 'dreamTable'
            ,cols: [[
                {type:'checkbox'}
                <#list tableList as t>
                ,{field:'${t.nameLower}',  title: '${t.comment!}', sort: true,edit:true}
                </#list>
            ]]
        });
    });
</script>
</html>
<!-- OnlyDream ${.now} -->
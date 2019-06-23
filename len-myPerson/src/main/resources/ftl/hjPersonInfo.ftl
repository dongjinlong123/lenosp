<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>花椒人员信息</title>

    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/lenos/main.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js"
            charset="utf-8"></script>
</head>
<body>
<table id="personList" class="layui-hide" lay-filter="hjPerson"></table>
<script>
    layui.use('table', function () {
        var table = layui.table;
        //方法级渲染
        table.render({
            id: 'personList',
            elem: '#personList'
            , url: '/hjPerson/showHjPersonList'
            , cols: [[
                {checkbox: true, fixed: true, width: '5%'}
                , {
                    field: 'userName',
                    title: '用户名',
                    width: '10%',
                    sort: true,
                    style: 'background-color: #009688; color: #fff;'
                }
                , {field: 'sex', title: '性别', width: '17%'}
                , {field: 'studyYear', title: '入学年份', width: '20%', sort: true}
                , {field: 'classNum', title: '班级', width: '13%'}
                , {field: 'liveCity', title: '现居城市', width: '13%'}
                , {field: 'telphone', title: '手机', width: '20%'}
            ]]
            , page: true,
            height: 'full-83'
        });
    });
</script>
</body>
</html>
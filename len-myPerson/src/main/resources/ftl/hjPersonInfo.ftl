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
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
    <script type="text/javascript"  src="${re.contextPath}/plugin/common.js" charset="utf-8"></script>
</head>
<body>

<div class="lenos-search">
    <div class="select">
        <div class="layui-form" >
            <div class="layui-inline">
                <label class="layui-form-label">用户名：</label>
                <div class="layui-input-inline">
                    <input class="layui-input" height="20px" id="userName" autocomplete="off">
                 </div>
            </div>

        <div class="layui-inline">
            <label class="layui-form-label">入学年份：</label>
            <div class="layui-input-inline">
                <select name="studyYear" lay-verify="required" id="studyYear" class="layui-input">
                    <option value=""></option>

                </select>
            </div>
        </div>

        <button class="select-on layui-btn layui-btn-sm" data-type="select"><i class="layui-icon"></i>
        </button>
        <button class="layui-btn layui-btn-sm icon-position-button" id="refresh" style="float: right;"
                data-type="reload">
            <i class="layui-icon">ဂ</i>
        </button>
        </div>
    </div>
</div>

<@shiro.hasPermission name="hjPerson:add">
<div class="layui-col-md12" style="height:40px;margin-top:3px;">
    <div class="layui-btn-group">
        <button class="layui-btn layui-btn-normal" data-type="add">
            <i class="layui-icon">&#xe608;</i>新增
        </button>
<   </div>
</div>
</@shiro.hasPermission>

<table id="personList" class="layui-hide" lay-filter="hjPerson"></table>

<script type="text/html" id="toolBar">
    <@shiro.hasPermission name="hjPerson:select">
      <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
     </@shiro.hasPermission>
    <@shiro.hasPermission name="hjPerson:update">
      <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="hjPerson:del">
      <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="delete"> <i class="layui-icon">&#xe640;</i>删除</a>
    </@shiro.hasPermission>
</script>

<script>
    $(function () {
        initStudyYear();
    })
    layui.use(['form','table'], function () {
        var table = layui.table;
        //方法级渲染
        table.render({
            id: 'personList',
            elem: '#personList'
            , url: '/hjPerson/showHjPersonList'
            , cols: [[
                {checkbox: true, fixed: true, width: '5%'}
                , {
                    field: 'useName',
                    title: '用户名',
                    width: '8%',
                    style: 'background-color: #009688; color: #fff;'
                }
                , {field: 'sex', title: '性别', width: '5%'}
                , {field: 'studyYear', title: '入学年份', width: '8%'}
                , {field: 'classNum', title: '班级', width: '8%', templet:'<div>{{ d.classNum+"班" }}</div>'}
                , {field: 'liveCity', title: '现居城市', width: '10%'}
                , {field: 'telphone', title: '手机', width: '10%'}
                ,{field: 'profession', title: '在职行业', width: '15%'}
                , {field: 'remark', title: '操作', width: '20%', toolbar: "#toolBar"}
            ]]
            , page: true,
            height: 'full-83'
        });

        var $ = layui.$, active = {
            select: function () {
                var userName = $('#userName').val();
                var studyYear = $('#studyYear').val();
                table.reload('personList', {
                    where: {
                        userName: userName,
                        studyYear: studyYear
                    }
                });
            },
            add: function () {
                //openMyWindow('添加任务', '/hjPerson/showAddHjPerson', 800, 450);
                //window.location.href="/hjPerson/showAddHjPerson";
                //新增一个Tab项
                openTab();
            }
            ,reload:function(){
                $('#userName').val('');
                $('#studyYear').val('');
                table.reload('personList', {
                    where: {
                        userName: null,
                        studyYear: null
                    }
                });
            },
        };
        $('.layui-col-md12 .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        $('.select .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        //监听工具条
        table.on('tool(hjPerson)', function (obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                openMaxWindow("update-hjPersonInfo",'查看角色', '/hjPerson/showHjPersonDetail?detail=true&id=' + data.userNum, 600, 400,true);
            }
            if (obj.event === 'edit') {
                openMaxWindow("update-hjPersonInfo",'编辑角色', '/hjPerson/showHjPersonDetail?detail=false&id=' + data.userNum, 600, 400,true);
            }
            if(obj.event ==='delete'){
                del(data.userNum);
            }
        });

    });

    document.onkeydown = function (e) { // 回车提交表单
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == 13) {
            $(".select .select-on").click();
        }
    }
    /**批量删除id*/
    function del(userNum) {
        layer.confirm('确定要删除?', {icon: 3, title:'提示'}, function(index){
            layer.close(index);
            $.ajax({
                url: "/hjPerson/del",
                type: "post",
                data: {"userNum": userNum},
                dataType: "json", traditional: true,
                success: function (data) {
                    layer.msg(data.msg, {icon: 6});
                    layui.table.reload('personList');
                }
            });

        });

    }
    function initStudyYear(){
        $.ajax({
            url: "/hjPerson/getAllStudyYear",
            type: "get",
            data: null,
            dataType: "json",
            traditional: true,
            success: function (data) {
                console.log(data)
                var arr = data.data;
                for(var i =0;i<arr.length;i++){
                    $("#studyYear")[0].options.add(new Option(arr[i],arr[i]));
                }
                //渲染select
                layui.form.render('select');
            }
        });
    }

    function openTab(){
        openMaxWindow("add-hjPerson","添加校友信息",'/hjPerson/showAddHjPerson',600,400,true)
    }

</script>
</body>
</html>
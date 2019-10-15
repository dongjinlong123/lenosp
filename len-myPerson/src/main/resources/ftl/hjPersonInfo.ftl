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
                <label class="">用户名：</label>
                <div class="layui-input-inline">
                    <input class="layui-input" height="20px" id="userName" autocomplete="off">
                 </div>
            </div>

        <div class="layui-inline">
            <label class="">入学年份：</label>
            <div class="layui-input-inline">
                <select name="studyYear"  id="studyYear" class="layui-input"  lay-filter="search">
                    <option value=""></option>

                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="">省份：</label>
            <div class="layui-input-inline">
                <select name="province"  id="province" class="layui-input"  lay-filter="cityChange">
                    <option value=""></option>

                </select>
            </div>
        </div>

        <div class="layui-inline">
            <label class="">城市：</label>
            <div class="layui-input-inline">
                <select name="city"  id="city" class="layui-input"  lay-filter="search">
                    <option value=""></option>

                </select>
            </div>
        </div>


        <button class="select-on layui-btn layui-btn-sm"  data-type="select"><i class="layui-icon"></i>
        </button>
        <button class="layui-btn layui-btn-sm icon-position-button" id="refresh" style="float: right;"
                data-type="reload">
            <i class="layui-icon">ဂ</i>
        </button>
        </div>
    </div>
</div>

<@shiro.hasPermission name="hjPerson:add">
<div class="layui-row" style="height:40px;margin-top:3px;">
    <div class="layui-btn-group">
        <button class="layui-btn layui-btn-normal" data-type="add">
            <i class="layui-icon">&#xe608;</i>新增
        </button>
        <button class="layui-btn layui-btn-warm" data-type="showMap">
            <i class="layui-icon layui-icon-location"></i>查看人员分布
        </button>
   </div>
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
      <a class="layui-btn layui-btn-xs  layui-btn-danger" lay-event="delete"> <i class="layui-icon">&#xe640;</i>删除</a>
    </@shiro.hasPermission>
</script>

<script>
    $(function () {
        initStudyYear();
        initAllProvince();
        initAllCity("");
    });
    layui.config({
        base: '${re.contextPath}/plugin/build/js/',
        version: '1.0.1'
    })
    layui.use(['form','table'], function () {
        var table = layui.table;
        var form = layui.form;

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
                , {field: 'liveCity', title: '现居城市', width: '20%'}
                , {field: 'telphone', title: '手机', width: '10%'}
                ,{field: 'profession', title: '在职行业', width: '15%'}
                , {field: 'remark', title: '操作', width: '20%', toolbar: "#toolBar"}
            ]]
            , page: true,
            height: 'full-83'
        });
        //监听表格点击事件
        table.on('rowDouble(hjPerson)',function(obj){
            console.log(obj)
            layer.open({
                id:"update-hjPersonInfo",
                type: 2,
                fix: false,
                area: ["100%","100%"],
                maxmin: true,
                shadeClose: false,
                shade: 0.4,
                title: '查看人员信息',
                content: '/hjPerson/showHjPersonDetail?detail=true&id=' + obj.data.userNum,
                success: function(layero,index){
                    //在回调方法中的第2个参数“index”表示的是当前弹窗的索引。
                    //通过layer.full方法将窗口放大。
                    layer.full(index);
                }
            });
        })
        var $ = layui.$,
                active = {
            select: function () {
                var userName = $('#userName').val();
                var studyYear = $('#studyYear').val();
                var province = $("#province").val();
                var city = $("#city").val();
                table.reload('personList', {
                    where: {
                        userName: userName,
                        studyYear: studyYear,
                        province:province,
                        city:city
                    }
                });
            },
            add: function () {
                layer.open({
                    id:"add-hjPerson",
                    type: 2,
                    fix: false,
                    area: ["100%","100%"],
                    maxmin: true,
                    shadeClose: false,
                    shade: 0.4,
                    title: '添加人员',
                    content: "/hjPerson/showAddHjPerson",
                    success: function(layero,index){
                        //在回调方法中的第2个参数“index”表示的是当前弹窗的索引。
                        //通过layer.full方法将窗口放大。
                        layer.full(index);
                    }
                });
            },
            showMap:function () {
                //添加选项卡
                var t =  {url: "/hjPerson/showHjPersonMap", icon: "", title: "人员分布", id: "999"}
                parent.tab.tabAdd(t)
            }
            ,reload:function(){
                $('#userName').val('');
                $('#studyYear').val('');
                $('#province').val('');
                $('#city').val('');
                table.reload('personList', {
                    where: {
                        userName: null,
                        studyYear: null,
                        province:null,
                        city:null
                    }
                });
                //重新渲染一下
                layui.form.render('select');
            },
        };

        form.on('select(search)',function (data) {
            active.select();
        })

        form.on('select(cityChange)',function(data){
            console.log(data.elem); //得到select原始DOM对象
            console.log(data.value); //得到被选中的值
            console.log(data.othis); //得到美化后的DOM对象
            initAllCity(data.value);

            //重新渲染一下
            $('#city').val('');
            active.select();
        })

        $('.layui-row .layui-btn').on('click', function () {
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

                layer.open({
                    id:"update-hjPersonInfo",
                    type: 2,
                    fix: false,
                    area: ["100%","100%"],
                    maxmin: true,
                    shadeClose: false,
                    shade: 0.4,
                    title: '查看人员信息',
                    content: '/hjPerson/showHjPersonDetail?detail=true&id=' + data.userNum,
                    success: function(layero,index){
                        //在回调方法中的第2个参数“index”表示的是当前弹窗的索引。
                        //通过layer.full方法将窗口放大。
                        layer.full(index);
                    }
                });
                //openMaxWindow("update-hjPersonInfo",'查看人员信息', '/hjPerson/showHjPersonDetail?detail=true&id=' + data.userNum, 600, 400,true);
            }
            if (obj.event === 'edit') {
                layer.open({
                    id:"update-hjPersonInfo",
                    type: 2,
                    fix: false,
                    area: ["100%","100%"],
                    maxmin: true,
                    shadeClose: false,
                    shade: 0.4,
                    title: '编辑人员信息',
                    content: '/hjPerson/showHjPersonDetail?detail=false&id=' + data.userNum,
                    success: function(layero,index){
                        layer.full(index);
                    }
                });
                //openMaxWindow("update-hjPersonInfo",'编辑人员信息', '/hjPerson/showHjPersonDetail?detail=false&id=' + data.userNum, 600, 400,true);
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
    function initAllProvince(){
        $.ajax({
            url: "/hjPerson/getAllProvince",
            type: "get",
            data: null,
            dataType: "json",
            traditional: true,
            success: function (data) {
                console.log(data)
                var arr = data.data;
                for(var i =0;i<arr.length;i++){
                    $("#province")[0].options.add(new Option(arr[i],arr[i]));
                }
                //渲染select
                layui.form.render('select');
            }
        });
    }

    function initAllCity(province){
        console.log("加载省份")
        $.ajax({
            url: "/hjPerson/getAllCityByProvince?province="+ province,
            type: "get",
            data: null,
            dataType: "json",
            traditional: true,
            success: function (data) {
                $("#city")[0].options.length = 1;
                var arr = data.data;
                for(var i =0;i<arr.length;i++){
                    $("#city")[0].options.add(new Option(arr[i],arr[i]));
                }
                //渲染select
                layui.form.render('select');
            }
        });
    }

</script>
</body>
</html>
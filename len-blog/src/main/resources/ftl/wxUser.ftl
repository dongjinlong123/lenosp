<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>

    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/lenos/main.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/common.js" charset="utf-8"></script>
</head>
<body>
<div class="content">
    <div class="lenos-search">
        <div class="select">
            用户昵称：
            <div class="layui-inline">
                <input class="layui-input" height="20px" id="nickName" autocomplete="off">
            </div>

            <button class="select-on layui-btn layui-btn-sm" data-type="select"><i class="layui-icon"></i>
            </button>
            <button class="layui-btn layui-btn-sm icon-position-button" id="refresh" style="float: right;"
                    data-type="reload">
                <i class="layui-icon">ဂ</i>
            </button>
        </div>

    </div>

    <table id="wxUserList" class="layui-hide" lay-filter="wxUserList"></table>
    <script type="text/html" id="toolBar">
        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
    </script>
    <div id="userMain" style="display:  none;">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
            <legend>用户关联信息</legend>
        </fieldset>

        <div class="layui-tab layui-tab-card">
            <ul class="layui-tab-title">
                <li class="layui-this">用户评论</li>
                <li>用户点赞</li>
                <li>用户收藏</li>
            <#--<li>用户消息</li>-->
                <li>用户签到</li>
            </ul>
            <div class="layui-tab-content" style="height: 400px;">
                <div class="layui-tab-item layui-show">
                    <table id="articleCommentList" class="layui-hide" lay-filter="articleCommentList"></table>

                </div>
                <div class="layui-tab-item" style="width: 70%;">
                    <div class="layui-tab-item layui-show">
                        <table id="articlePraiseList" class="layui-hide" lay-filter="articlePraiseList"></table>

                    </div>

                </div>
                <div class="layui-tab-item" style="width: 70%;">
                    <div class="layui-tab-item layui-show">
                        <table id="articleSaveList" class="layui-hide" lay-filter="articleSaveList"></table>

                    </div>

                </div>
            <#--<div class="layui-tab-item">4</div>-->
                <div class="layui-tab-item" style="width: 70%;">
                    <div class="layui-tab-item layui-show">
                        <table id="userSignList" class="layui-hide" lay-filter="userSignList"></table>

                    </div>
                </div>
            </div>
        </div>
        <a name="001"></a>

    </div>

</div>
<script>
    document.onkeydown = function (e) { // 回车提交表单
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == 13) {
            $(".select .select-on").click();
        }
    }

    layui.use('table', function () {
        var table = layui.table;
        var $ = layui.$;
        //方法级渲染
        table.render({
            id: 'wxUserList',
            elem: '#wxUserList'
            , url: '/wxUser/showWxUserList'
            , cols: [[
                {type: "numbers", title: "序号", width: '5%'}
                , {field: 'openId', title: '用户openId', width: '10%'}
                , {field: 'nickName', title: '用户昵称', width: '10%'}
                , {
                    field: 'gender', title: '性别', width: '10%', templet: function (value) {
                        if (value == "1") {
                            return "男";
                        }
                        if (value == "2") {
                            return "女";
                        }
                        return "未知";
                    }
                }
                , {field: 'province', title: '省份', width: '10%'}
                , {field: 'city', title: '城市', width: '10%'}
                , {field: 'country', title: '区域', width: '10%'}
                ,{
                    field: 'createTime', title: '创建时间', width: '15%', templet: function (value) {
                        return formatDateTime(value.createTime);
                    }
                }
                , {field: 'remark', title: '操作', width: '10%', toolbar: "#toolBar"}
            ]]
            , page: true
            , height: '315'
        });
        var active = {
            select: function () {
                var id = $('#id').val();
                var openId = $('#openId').val();
                var nickName = $('#nickName').val();
                var gender = $('#gender').val();
                var province = $('#province').val();
                var city = $('#city').val();
                var country = $('#country').val();
                table.reload('wxUserList', {
                    where: {
                        id: id,
                        openId: openId,
                        nickName: nickName,
                        gender: gender,
                        province: province,
                        city: city,
                        country: country,
                    }
                });
            },
            reload: function () {
                $('#id').val('');
                $('#openId').val('');
                $('#nickName').val('');
                $('#gender').val('');
                $('#province').val('');
                $('#city').val('');
                $('#country').val('');
                table.reload('wxUserList', {
                    where: {
                        id: null,
                        openId: null,
                        nickName: null,
                        gender: null,
                        province: null,
                        city: null,
                        country: null,
                    }
                });
            }
        };
        //监听行单击事件
        table.on('rowDouble(wxUserList)', function (obj) {
            var d = obj.data;
            showDetail(d.id);
        });
        table.on('tool(wxUserList)', function (obj) {
            var data = obj.data;
            console.log(data.id)
            if (obj.event === 'detail') {
                showDetail(data.id);
            }
        });
        $('.layui-row .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        $('.select .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        function showDetail(id) {
            showCommentDetail(id);
            showPraiseDetail(id);
            showSaveDetail(id);
            showUserSignDetail(id);
        }
        table.render({
            id: 'articleCommentList',
            elem: '#articleCommentList'
            , url: '/article/showArticleCommentList'
            , cols: [[
                {field: 'replyer', title: '用户昵称', width: '10%'}
                , {field: 'title', title: '文章标题', width: '15%'}
                , {field: 'excerpt', title: '文章简介', width: '25%'}
                , {
                    field: 'createdAt', title: '评论时间', width: '15%', templet: function (value) {
                        return formatDateTime(value.createdAt);
                    }
                }
                , {field: 'userName', title: '被评论者', width: '10%'}
                , {field: 'content', title: '评论内容', width: '25%'}
            ]]
            , page: true
            , height: '315'
        });
        function showCommentDetail(id) {

            //方法级渲染
            table.reload('articleCommentList', {
                where: {
                    replyerId: id,
                }
            });
            $("#userMain").show();
            window.location.href = "#001"
        }
        table.render({
            id: 'articlePraiseList',
            elem: '#articlePraiseList'
            , url: '/article/showArticlePraiseList'
            , cols: [[
                {field: 'userName', title: '用户昵称', width: '10%'}
                , {field: 'title', title: '文章标题', width: '15%'}
                , {field: 'excerpt', title: '文章简介', width: '15%'}
                , {
                    field: 'createdAt', title: '操作时间', width: '15%', templet: function (value) {
                        return formatDateTime(value.createdAt);
                    }
                }
                , {
                    field: 'flag', title: '点赞', width: '10%', templet: function (value) {
                        if (value == 0) {
                            return "已点赞";
                        } else {
                            return "取消点赞";
                        }
                    }
                }
            ]]
            , page: true
            , height: '315'
        });
        function showPraiseDetail(id) {
            //方法级渲染

            table.reload('articlePraiseList', {
                where: {
                    id: id,
                }
            });
            $("#userMain").show();
            window.location.href = "#001"
        }
        table.render({
            id: 'articleSaveList',
            elem: '#articleSaveList'
            , url: '/article/showArticleSaveList'
            , cols: [[
                {field: 'userName', title: '用户昵称', width: '10%'}
                , {field: 'title', title: '文章标题', width: '15%'}
                , {field: 'excerpt', title: '文章简介', width: '15%'}
                , {
                    field: 'createdAt', title: '操作时间', width: '15%', templet: function (value) {
                        return formatDateTime(value.createdAt);
                    }
                }
                , {
                    field: 'flag', title: '收藏', width: '10%', templet: function (value) {
                        if (value == 0) {
                            return "已收藏";
                        } else {
                            return "取消收藏";
                        }
                    }
                }
            ]]
            , page: true
            , height: '315'
        });
        function showSaveDetail(id) {
            //方法级渲染

            table.reload('articleSaveList', {
                where: {
                    id: id,
                }
            });
            $("#userMain").show();
            window.location.href = "#001"
        }
        table.render({
            id: 'userSignList',
            elem: '#userSignList'
            , url: '/userSign/showUserSignList'
            , cols: [[
                {field: 'userName', title: '用户昵称', width: '10%'}
                , {
                    field: 'createdAt', title: '签到时间', width: '25%', templet: function (value) {
                        return formatDateTime(value.createdAt);
                    }
                }
            ]]
            , page: true
            , height: '315'
        });
        function showUserSignDetail(id) {
            //方法级渲染

            table.reload('userSignList', {
                where: {
                    wxUserId: id,
                }
            });
            $("#userMain").show();
            window.location.href = "#001"
        }

    });

</script>
</body>
</html>

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
    <script type="text/javascript"  src="${re.contextPath}/plugin/common.js" charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js"></script>
</head>
<body>
<div class="lenos-search">
    <div class="select">
        <div class="layui-form" >


        <div class="layui-inline">
            <label>  文章标题：</label>
            <div class="layui-input-inline">
                <input class="layui-input" height="20px" id="title" autocomplete="off">
            </div>
        </div>

        <div class="layui-inline">
            <label> 作者：</label>
            <div class="layui-input-inline">
                <input class="layui-input" height="20px" id="author" autocomplete="off">
            </div>
        </div>


        <div class="layui-inline">
             <label>类别：</label>
            <div class="layui-input-inline">
            <select name="category"  id="category" class="layui-input"  lay-filter="search">
                <option value=""></option>
                <#list categoryList as category>
                 <option value="${category}">${category}</option>
                </#list>
            </select>
            </div>
        </div>


        <div class="layui-inline">
            <label> 状态：</label>
            <div class="layui-input-inline">
            <select name="status"  id="status" class="layui-input"  lay-filter="search">
                <option value="">全部</option>
                <option value="0">草稿</option>
                <option value="1">发布</option>
                <option value="2">下架</option>
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
<div class="layui-row" style="height:40px;margin-top:3px;">
    <div class="layui-btn-group">
      <@shiro.hasPermission name="article:add">
          <button class="layui-btn layui-btn-normal" data-type="add">
              <i class="layui-icon">&#xe608;</i>新增
          </button>
      </@shiro.hasPermission>
    </div>
</div>
<table id="articleList" class="layui-hide" lay-filter="articleList"></table>

<script type="text/html" id="toolBar">
    <@shiro.hasPermission name="article:select">
  <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
    </@shiro.hasPermission>
<@shiro.hasPermission name="article:update">
  <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
</@shiro.hasPermission>
<@shiro.hasPermission name="article:del">
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</@shiro.hasPermission>

    <@shiro.hasPermission name="article:update">
    {{# if(d.status == 0 || d.status == 2 ){ }}
        <a class="layui-btn layui-btn-xs  layui-bg-orange" lay-event="up">上架</a>
    {{# } else if(d.status == 1){ }}
        <a class="layui-btn layui-btn-xs  layui-bg-cyan" lay-event="down">下架</a>
    {{# } }}

     {{# if(d.topFlag == 0 ){ }}
     <a class="layui-btn layui-btn-xs  layui-bg-blue" lay-event="top">置顶</a>
    {{# } else if(d.topFlag == 1){ }}
        <a class="layui-btn layui-btn-xs  layui-bg-black" lay-event="unTop">取消置顶</a>
    {{# } }}

    </@shiro.hasPermission>
</script>
<script>
    document.onkeydown = function (e) { // 回车提交表单
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == 13) {
            $(".select .select-on").click();
        }
    };
    layui.use(['table', "layer", "jquery","form"], function () {
        var table = layui.table;
        var $ = layui.jquery,
                form = layui.form,
                layer = layui.layer;

        //方法级渲染
        table.render({
            id: 'articleList',
            elem: '#articleList'
            , url: '/article/showArticleList'
            , cols: [[
                {field: 'id', title: '主键', width: '8%', sort: true, hide: true}
                , {field: 'title', title: '文章标题', width: '8%'}
                , {field: 'readCounts', title: '阅读量', width: '8%', sort: true}
                , {field: 'excerpt', title: '简介', width: '15%'}
                , {field: 'author', title: '作者', width: '8%'}
                , {
                    field: 'createdAt', title: '发布日期', width: '15%', sort: true, templet: function (value) {
                        return formatDateTime(value.createdAt);
                    }
                }, {
                    field: 'status', title: '状态', width: '8%', sort: true, templet: function (value) {
                        if(value.status == 0){
                            return "草稿";
                        }
                        if(value.status == 1){
                            return "<span style='color:#FFB800'>发布</span>";
                        }
                        if(value.status == 2){
                            return "<span style='color:#2F4056'>下架</span>";
                        }
                    }
                }
                , {field: 'topFlag', title: '置顶', width: '8%', templet: function (value) {
                        if(value.topFlag == 0){
                            return "普通";
                        }
                        if(value.topFlag == 1){
                            return "<span style='color:lightseagreen'>置顶</span>";
                        }

                    }}

                , {field: 'category', title: '类别', width: '8%'}
                , {field: 'remark', title: '操作', width: '22%', toolbar: "#toolBar"}
            ]]
            , page: true
            , height: '315'
        });

        //监听行单击事件
        table.on('row(articleList)', function (obj) {
            var d = obj.data;
            console.log(d)
        });
        var active = {
            select: function () {
                var id = $('#id').val();
                var title = $('#title').val();
                var readCounts = $('#readCounts').val();
                var excerpt = $('#excerpt').val();
                var author = $('#author').val();
                var createdAt = $('#createdAt').val();
                var category = $('#category').val();
                var status = $('#status').val();
                console.log(status);
                table.reload('articleList', {
                    where: {
                        id: id,
                        title: title,
                        readCounts: readCounts,
                        excerpt: excerpt,
                        author: author,
                        createdAt: createdAt,
                        category: category,
                        status:status
                    }
                });
            },
            reload: function () {
                $('#id').val('');
                $('#title').val('');
                $('#readCounts').val('');
                $('#excerpt').val('');
                $('#author').val('');
                $('#createdAt').val('');
                $('#category').val('');
                $('#status').val('');
                table.reload('articleList', {
                    where: {
                        id: null,
                        title: null,
                        readCounts: null,
                        excerpt: null,
                        author: null,
                        createdAt: null,
                        status:null
                    }
                });
                //重新渲染一下
                layui.form.render('select');
            },
            add: function () {
                add('添加文章', '/article/showArticleAdd');
            }
        }

        //监听工具条
        table.on('tool(articleList)', function (obj) {
            var data = obj.data;
            console.log(data.id)
            if (obj.event === 'detail') {
                detail('查看', '/article/showArticleDetail?detail=true&id=' + data.id);
            } else if (obj.event === 'del') {
                layer.confirm('确定删除?', function () {
                    del(data.id);
                });
            } else if (obj.event === 'edit') {
                update('编辑', '/article/showArticleDetail?detail=false&id=' + data.id);
            }else if(obj.event === 'up'){
                var param = {"id":data.id,"status":1};
                postAjaxre('/article/updateArticleStatus', param, 'articleList');
            }else if(obj.event === 'down'){
                var param = {"id":data.id,"status":2};
                postAjaxre('/article/updateArticleStatus', param, 'articleList');
            }else if(obj.event === 'top'){
                var param = {"id":data.id,"topFlag":1};
                postAjaxre('/article/updateArticleStatus', param, 'articleList');
            }else if(obj.event === 'unTop'){
                var param = {"id":data.id,"topFlag":0};
                postAjaxre('/article/updateArticleStatus', param, 'articleList');
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
        form.on('select(search)',function (data) {
            active.select();
        })
        form.render('select');
    });

    function del(id) {
        $.ajax({
            url: "/article/del",
            type: "post",
            data: {id: id},
            success: function (d) {
                if (d.msg) {
                    layer.msg(d.msg, {icon: 6, offset: 'rb', area: ['120px', '80px'], anim: 2});
                    layui.table.reload('articleList');
                } else {
                    layer.msg(d.msg, {icon: 5, offset: 'rb', area: ['120px', '80px'], anim: 2});
                }
            }
        });
    }

    function detail(title, url, w, h) {
        layer.open({
            id: 'article-detail',
            type: 2,
            fix: false,
            area: ["100%","100%"],
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: title,
            content: url,
            success: function(layero,index){
                //在回调方法中的第2个参数“index”表示的是当前弹窗的索引。
                //通过layer.full方法将窗口放大。
                layer.full(index);
            }
        });
    }

    /**
     * 更新用户
     */
    function update(title, url, w, h) {
        layer.open({
            id: 'article-update',
            type: 2,
            fix: false,
            area: ["100%","100%"],
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: title,
            content: url,
            success: function(layero,index){
                //在回调方法中的第2个参数“index”表示的是当前弹窗的索引。
                //通过layer.full方法将窗口放大。
                layer.full(index);
            }
        });
    }

    function add(title, url, w, h) {
        layer.open({
            id: 'article-add',
            type: 2,
            fix: false,
            area: ["100%","100%"],
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: title,
            content: url,
            success: function(layero,index){
                //在回调方法中的第2个参数“index”表示的是当前弹窗的索引。
                //通过layer.full方法将窗口放大。
                layer.full(index);
            }
        });
    }</script>
</body>
</html>

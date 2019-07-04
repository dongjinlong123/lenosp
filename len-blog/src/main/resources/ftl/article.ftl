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
</head>
<body>
<div class="lenos-search">
    <div class="select">

        文章标题：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="title" autocomplete="off">
        </div>
        作者：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="author" autocomplete="off">
        </div>

        类别：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="category" autocomplete="off">
        </div>

        <button class="select-on layui-btn layui-btn-sm" data-type="select"><i class="layui-icon"></i>
        </button>
        <button class="layui-btn layui-btn-sm icon-position-button" id="refresh" style="float: right;"
                data-type="reload">
            <i class="layui-icon">ဂ</i>
        </button>
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
</script>
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
            id: 'articleList',
            elem: '#articleList'
            , url: '/article/showArticleList'
            , cols: [[
                  {field: 'id', title: '主键', width: '8%', sort: true,hide:true}
                , {field: 'title', title: '文章标题', width: '15%'}
                , {field: 'readCounts', title: '阅读量', width: '8%', sort: true}
                , {field: 'excerpt', title: '简介', width: '15%'}
                , {field: 'author', title: '作者', width: '15%'}
                , {field: 'createdAt', title: '发布日期', width: '15%', sort: true,templet:function(value){
                    return formatDateTime(value.createdat);
                    }}
                , {field: 'category', title: '类别', width: '8%'}
                , {field: 'remark', title: '操作', width: '20%', toolbar: "#toolBar"}
            ]]
            , page: true
            ,  height: '315'
        });

        //监听行单击事件
        table.on('row(articleList)', function(obj){
           var d = obj.data;
           console.log(d)
        });
        var active = {
            select: function () {
                var id = $('#id').val();
                var title = $('#title').val();
                var readcounts = $('#readcounts').val();
                var excerpt = $('#excerpt').val();
                var author = $('#author').val();
                var createdat = $('#createdat').val();
                var category = $('#category').val();
                var listpic = $('#listpic').val();
                var mdcontent = $('#mdcontent').val();
                var sharecode = $('#sharecode').val();
                table.reload('articleList', {
                    where: {
                        id: id,
                        title: title,
                        readcounts: readcounts,
                        excerpt: excerpt,
                        author: author,
                        createdat: createdat,
                        category: category,
                        listpic: listpic,
                        mdcontent: mdcontent,
                        sharecode: sharecode,
                    }
                });
            },
            reload:function(){
                $('#id').val('');
                $('#title').val('');
                $('#readcounts').val('');
                $('#excerpt').val('');
                $('#author').val('');
                $('#createdat').val('');
                $('#category').val('');
                $('#listpic').val('');
                $('#mdcontent').val('');
                $('#sharecode').val('');
                table.reload('articleList', {
                    where: {
                        id: null,
                        title: null,
                        readcounts: null,
                        excerpt: null,
                        author: null,
                        createdat: null,
                        category: null,
                        listpic: null,
                        mdcontent: null,
                        sharecode: null,
                    }
                });
            },
            add: function () {
                add('添加', '/article/showArticleAdd', 700, 450);
            }
        }

        //监听工具条
        table.on('tool(articleList)', function (obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                detail('查看','/article/showArticleDetail?detail=true&id=' + data[0].id, 700, 450);
            } else if (obj.event === 'del') {
                layer.confirm('确定删除?', function(){
                    del(data.id);
                });
            } else if (obj.event === 'edit') {
                update('编辑', '/article/showArticleDetail?detail=false&id=' + data[0].id, 700, 450);
            }
        });
        $('.layui-row .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        $('.select .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });});
    function del(id) {
        $.ajax({
            url: "/article/del",
            type: "post",
            data: {id: id},
            success: function (d) {
                if(d.msg){
                    layer.msg(d.msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});
                    layui.table.reload('articleList');
                }else{
                    layer.msg(d.msg,{icon:5,offset: 'rb',area:['120px','80px'],anim:2});
                }
            }
        });
    }
    function detail(title, url, w, h) {
        layer.open({
            id: 'article-detail',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: true,
            shade: 0.4,
            title: title,
            content: url ,
            // btn:['关闭']
        });
    }
    /**
     * 更新用户
     */
    function update(title, url, w, h) {
        layer.open({
            id: 'article-update',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: title,
            content: url
        });
    }

    function add(title, url, w, h) {
        layer.open({
            id: 'article-add',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: title,
            content: url
        });
    }</script>
</body>
</html>

<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>添加文章信息</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/build/css/city.css">
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/modules/laydate/default/laydate.css">
  <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
    <script src="${re.contextPath}/plugin/layui/lay/modules/laydate.js" charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/common.js" charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js"></script>

    <link rel="stylesheet" href="${re.contextPath}/plugin/edit/themes/default/default.css"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/edit/plugins/code/prettify.css"/>
    <script charset="utf-8" src="${re.contextPath}/plugin/edit/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${re.contextPath}/plugin/edit/lang/zh-CN.js"></script>

    <script charset="utf-8" src="${re.contextPath}/plugin/edit/plugins/code/prettify.js"></script>
    <link rel="stylesheet" href="${re.contextPath}/plugin/markdown/css/editormd.css" />
    <script src="${re.contextPath}/plugin/markdown/editormd.js"></script>
    <style>
        /*重写label 的宽度*/

         input {
             border: 1px solid #b2b2b2 !important;
         }
         .form-edit {
             position: relative;
             left: 50%;
             width: 50%;
             transform: translate(-50%);
         }
        .hidden{
            display: none;
        }
    </style>
</head>

<body>

<div class="x-body">
    <form class="layui-form layui-form-pane">



        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
            <legend style="font-size:16px;">文章标题</legend>
        </fieldset>
        <div class="layui-form-item  form-edit">
            <div class="layui-input-block">
                <input type="text" name="title" placeholder="请输入文章标题" autocomplete="off" class="layui-input" id="title"
                       lay-verify="title">
            </div>
        </div>

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
            <legend style="font-size:16px;">文章内容</legend>
        </fieldset>
        <input type="hidden" id="editor-flag" value="KindEditor">

        <div id="editor_div">

            <textarea id="editor_id" name="mdcontent" style="width:99%;height:968px;"></textarea>
        </div>
        <div id="md-editor" class="hidden">
            <textarea style="display:none;"></textarea>
        </div>



        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
            <legend style="font-size:16px;">文章信息</legend>
        </fieldset>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">简介</label>
                <div class="layui-input-inline">
                    <input type="text" name="excerpt" lay-verify="excerpt" maxlength="45" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">作者</label>
                <div class="layui-input-inline">
                    <input type="text" name="author" lay-verify="author" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">类别</label>
                <input type="hidden"  lay-verify="category" class="layui-input" name="category" id="category" >
                <div class="layui-input-block" id="categoryList">
                <#list categoryList as category>
                    <input type="checkbox" class="category" title="${category}" lay-filter="category" value="${category}">
                </#list>

                    <button style=" margin-left: 10px;" class="layui-btn layui-btn-radius"
                            type="button" id="addCategory"><i class="layui-icon layui-icon-add-1"></i>
                        <button>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                <legend style="font-size:16px;">图片上传</legend>
            </fieldset>
            <div class="layui-input-inline">
                <div class="layui-upload-drag" style="margin-left:10%;" id="test10">
                    <i style="font-size:30px;" class="layui-icon"></i>
                    <p style="font-size: 10px">点击上传，或将文件拖拽到此处</p>
                </div>
            </div>
            <div class="layui-input-inline">

                <div id="demo2" style="margin-top: 10px;">

                </div>
                <input id="listPic" name="listPic" type="hidden" lay-verify="listPic"  class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="margin-bottom: 100px;">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                <legend style="font-size:16px;">海报分享二维码</legend>
            </fieldset>
            <div class="layui-inline">
                <label class="layui-form-label">分享URL</label>
                <div class="layui-input-inline">
                    <input type="text"  autocomplete="off"  class="layui-input" id="shareCodeText">
                </div>
                <div class="layui-input-inline">
                    <button style=" margin-left: 10px;" class="layui-btn layui-btn-radius"
                            type="button" id="getErWeiMa">生成二维码
                        <button>
                </div>
                <div class="layui-input-inline">
                    <div id="shareCodePng" style="margin: 10px">

                    </div>
                    <input id="shareCode" name="shareCode" type="hidden" lay-verify="shareCode"  class="layui-input">
                </div>
            </div>
        </div>



        <div class="layui-form-item">
           <#-- <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                <legend style="font-size:16px;">操作</legend>
            </fieldset>-->
            <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
    position: fixed;bottom: 1px;margin-left:-20px;z-index:999">
                <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">
                    <button  class="layui-btn layui-btn-normal" id="changeMd" >
                        切换编辑器
                    </button>
                    <button  class="layui-btn layui-btn-normal" lay-filter="add" lay-submit>
                        增加
                    </button>
                    <button  class="layui-btn layui-btn-primary" id="close">
                        取消
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>

<!-- 弹出层 -->
<form class="layui-form layui-form-pane" id="categoryForm" style="display: none">
    <div class="layui-form-item" style="margin: 20px">
        <div class="layui-input-line">
            <input type="text" placeholder="请输入类型" autocomplete="off" class="layui-input" id="categoryInput"
                   lay-verify="required|addCategory" name="categoryInput">
        </div>
        <div class="layui-form-item">
            <div class="layui-input-line">
                <button class="layui-btn" lay-submit="" lay-filter="categoryCommit" style="width: 100%">立即提交</button>
            </div>
        </div>
    </div>
</form>

<script>
    var editor =
    KindEditor.ready(function (K) {
        editor = K.create('#editor_id', {
            uploadJson: '../editor/upload/file',
            fileManagerJson: '../editor/upload/downfile',
            allowFileManager: true,
            imageSizeLimit: '2MB,', //批量上传图片单张最大容量
            imageUploadLimit: 5, //批量上传图片同时上传最多个数
            items: ['source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
                'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                'anchor', 'link', 'unlink', '|', 'about']
        });
    });
</script>
<script type="text/javascript">
    var mdEditor ;
    $(function() {
        mdEditor = editormd("md-editor", {
            width: "99%",
            height: 968,
           // theme : "dark",
           // previewTheme : "dark",
          //  editorTheme : "pastel-on-dark",
            //markdown : "初始值",
            codeFold : true,
            saveHTMLToTextarea : true,    // 保存 HTML 到 Textarea
            searchReplace : true,
            path   : "${re.contextPath}/plugin/markdown/lib/",
            //watch : false,                // 关闭实时预览
            htmlDecode : "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
            toolbar  : true,             //关闭工具栏
            //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
            emoji : true,
            taskList : true,
            tocm            : true,         // Using [TOCM]
            tex : true,                   // 开启科学公式TeX语言支持，默认关闭
            flowChart : true,             // 开启流程图支持，默认关闭
            sequenceDiagram : true,       // 开启时序/序列图支持，默认关闭,
            //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
            //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
            //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
            //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
            //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
            imageUpload : true,
            imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL : "../editor/upload/file",
            onload : function() {
                console.log('onload', this);
                //this.fullscreen();
                //this.unwatch();
                //this.watch().fullscreen();

                //this.setMarkdown("#PHP");
                //this.width("100%");
                //this.height(480);
                //this.resize("100%", 640);
            }
        });

        $('#changeMd').click(function(){
            if($("#editor_div").hasClass("hidden")){
                $("#editor_div").removeClass("hidden");
                $("#md-editor").addClass("hidden");
                //使用KindEditor
                var content = mdEditor.getMarkdown();
                editor.html(content);
                $("#editor-flag").val("KindEditor");
            }else{
                $("#editor_div").addClass("hidden");
                $("#md-editor").removeClass("hidden");
                // 取得HTML内容
                var html = editor.html();
                mdEditor.setValue(html)
                $("#editor-flag").val("MdEditor");
            }

            return false;
        });
    });


</script>
<script>

  layui.use(['form','layer','jquery', 'upload'], function(){
    var $ = layui.jquery;
    var form = layui.form,
         upload = layui.upload
        ,layer = layui.layer;

      upload.render({
          elem: '#test10'
          , url: '/user/upload'
          , before: function (obj) {
              //预读，不支持ie8
              obj.preview(function (index, file, result) {
                  $('#demo2').find('img').remove();
                  $('#demo2').append('<img src="' + result + '" alt="' + file.name + '" width="100px" height="100px" class="layui-upload-img">');
              });
          }, done: function (res) {
              if (!res.flag) {
                  layer.msg(res.msg, {icon: 5, anim: 6});
              } else {
                  $("#listPic").val(res.msg);
                  console.info($('#listPic').val());
              }
          }
      });

      form.render();
      form.verify({
          addCategory: function (value) {
              if (value.trim() == "") {
                  return "类型不能为空";
              }
          },
          listPic: function (value) {
              if (value.trim() == "") {
                  return "请上传主图";
              }
          },
          title: function (value) {
              if (value.trim() == "") {
                  return "标题不能为空";
              }
          },
          excerpt: function (value) {
              if (value.trim() == "") {
                  return "简介不能为空";
              }
          },
          author: function (value) {
              if (value.trim() == "") {
                  return "作者不能为空";
              }
          },
          category: function (value) {
              var r =$(".category");
              var categorys=[];
              for(var i=0;i<r.length;i++){
                  if(r[i].checked){
                      categorys.push(r[i].value);
                  }
              }
              if (categorys.length == 0) {
                  return "类别不能为空";
              }
          },

          // shareCode: function (value) {
          //     if (value.trim() == "") {
          //         return "生成分享的二维码";
          //     }
          // },
          mdcontent: function (value) {
              if (value.trim() == "") {
                  return "内容不能为空";
              }
          }
      })

      $("#addCategory").click(function () {

          layer.open({
              title: '添加类型',
              type: 1,
              content: $("#categoryForm"),
              success: function (layero, index) {
                  form.on('submit(categoryCommit)', function (data) {
                      console.log(JSON.stringify(data.field))
                      var categoryInput = data.field.categoryInput;
                      //判断类型是否存在
                      var len = $("#categoryList input[name='category']");

                      for (var i = 0; i < len.length; i++) {
                          if ($("#categoryList input[name='category']")[i].title == categoryInput) {

                              layer.msg(categoryInput + "已经存在！")
                              return false;
                          }
                      }
                      $("#addCategory").before('<input type="checkbox"  class="category" value="'+categoryInput+'" checked name="category"  lay-filter="category"  title="' + categoryInput + '">');
                      form.render();
                      layer.close(index);
                      $("#categoryInput").val("");
                      return false;
                  });
              }

          });
          return false;

      });



      $('#close').click(function(){
          var index = parent.layer.getFrameIndex(window.name);
          parent.layer.close(index);
      });
      //生成二维码
      $("#getErWeiMa").click(function () {
          var text = $("#shareCodeText").val();
          if (text.trim() == "") {
              layer.msg("分享URL不能为空！");
              return;
          }
          var listPic = $("#listPic").val()
          if (listPic.trim() == "") {
              layer.msg("请先上传主图");
              return;
          }

          $.ajax({
              url: "/article/getErWeiMa",
              type: "post",
              data: {text: text, listPic: listPic},
              success: function (d) {
                  $("#shareCode").val(d.data);
                  $('#shareCodePng').html('<img src="' + d.data + '" width="100px" height="100px" class="layui-upload-img">');
                  layer.msg(d.msg);
              }
          });
      });
        //监听提交
      form.on('submit(add)', function(data){

          var html = editor.html();
          if("MdEditor" ==  $("#editor-flag").val()){
              //markDown 格式
              html = mdEditor.getMarkdown();
              data.field.editType = 1; //markdown编辑器
          }else{
              data.field.editType = 0;
          }
          data.field.status = 0; //状态默认草稿状态
          data.field.mdcontent = html;
          layerAjax('/article/addArticle', data.field, 'articleList');
          return false;
      });
      form.on('checkbox(category)', function(data){
          // console.log(data.elem); //得到checkbox原始DOM对象
          // console.log(data.elem.checked); //是否被选中，true或者false
          // console.log(data.value); //复选框value值，也可以通过data.elem.value得到
          // console.log(data.othis); //得到美化后的DOM对象
          var r =$(".category");
          var categorys=[];
          for(var i=0;i<r.length;i++){
              if(r[i].checked){
                  categorys.push(r[i].value);
              }
          }
          console.log(categorys.join(","))
          $("#category").val(categorys.join(","));
      });
  });
</script>
</body>

</html>

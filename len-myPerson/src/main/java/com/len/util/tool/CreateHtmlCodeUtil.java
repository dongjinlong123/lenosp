package com.len.util.tool;

import java.util.List;

import static com.len.util.tool.CreateCodeUtil.toFirstCharUpCase;

/**
 * 创建前端代码
 */
public class CreateHtmlCodeUtil {
    public static String createFtlFile(String classPath, List<CreateCodeUtil.ColumnModel> columnModelList, String beanName, String tableName) {
        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title></title>\n" +
                    "\n" +
                    "    <meta name=\"renderer\" content=\"webkit\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n" +
                    "    <meta name=\"viewport\"\n" +
                    "          content=\"width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi\"/>\n" +
                    "    <link rel=\"stylesheet\" href=\"${re.contextPath}/plugin/layui/css/layui.css\">\n" +
                    "    <link rel=\"stylesheet\" href=\"${re.contextPath}/plugin/lenos/main.css\">\n" +
                    "    <script type=\"text/javascript\" src=\"${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js\"></script>\n" +
                    "    <script type=\"text/javascript\" src=\"${re.contextPath}/plugin/layui/layui.all.js\" charset=\"utf-8\"></script>\n" +
                    "    <script type=\"text/javascript\"  src=\"${re.contextPath}/plugin/common.js\" charset=\"utf-8\"></script>\n" +
                    "</head>\n" +
                    "<body>\n");
            sb.append("<div class=\"lenos-search\">\n" +
                    "  <div class=\"select\">\n");
            for(CreateCodeUtil.ColumnModel c:columnModelList){
                sb.append(
                        "    "+c.getRemarks()+"：\n" +
                                "    <div class=\"layui-inline\">\n" +
                                "      <input class=\"layui-input\" height=\"20px\" id=\""+c.getFieldName()+"\" autocomplete=\"off\">\n" +
                                "    </div>\n");
            }


             sb.append(
                    "    <button class=\"select-on layui-btn layui-btn-sm\" data-type=\"select\"><i class=\"layui-icon\">\uE615</i>\n" +
                    "    </button>\n" +
                    "    <button class=\"layui-btn layui-btn-sm icon-position-button\" id=\"refresh\" style=\"float: right;\"\n" +
                    "            data-type=\"reload\">\n" +
                    "      <i class=\"layui-icon\">ဂ</i>\n" +
                    "    </button>\n" +
                    "  </div>\n" +
                    "\n" +
                    "</div>\n");
            sb.append("<div class=\"layui-row\" style=\"height:40px;margin-top:3px;\">\n" +
                    "  <div class=\"layui-btn-group\">\n" +
                    "      <@shiro.hasPermission name=\""+beanName+":add\">\n" +
                    "      <button class=\"layui-btn layui-btn-normal\" data-type=\"add\">\n" +
                    "      <i class=\"layui-icon\">&#xe608;</i>新增\n" +
                    "    </button>\n" +
                    "      </@shiro.hasPermission>\n" +
                    "    <@shiro.hasPermission name=\""+beanName+":update\">\n" +
                    "    <button class=\"layui-btn layui-btn-normal\" data-type=\"update\">\n" +
                    "      <i class=\"layui-icon\">&#xe642;</i>编辑\n" +
                    "    </button>\n" +
                    "    </@shiro.hasPermission>\n" +
                    "<@shiro.hasPermission name=\""+beanName+":select\">\n" +
                    "    <button class=\"layui-btn layui-btn-normal\" data-type=\"detail\">\n" +
                    "      <i class=\"layui-icon\">&#xe605;</i>查看\n" +
                    "    </button>\n" +
                    "</@shiro.hasPermission>\n" +
                    "  </div>\n" +
                    "</div>\n");
            sb.append("<table id=\""+beanName+"List\" class=\"layui-hide\" lay-filter=\""+beanName+"List\"></table>\n" +
                    "<script type=\"text/html\" id=\"toolBar\">\n" +
                    "  <@shiro.hasPermission name=\""+beanName+":select\">\n" +
                    "  <a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"detail\">查看</a>\n" +
                    "  </@shiro.hasPermission>\n" +
                    "<@shiro.hasPermission name=\""+beanName+":update\">\n" +
                    "  <a class=\"layui-btn layui-btn-xs  layui-btn-normal\" lay-event=\"edit\">编辑</a>\n" +
                    "</@shiro.hasPermission>\n" +
                    "<@shiro.hasPermission name=\""+beanName+":del\">\n" +
                    "  <a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\">删除</a>\n" +
                    "</@shiro.hasPermission>\n" +
                    "</script>\n");
            sb.append("<script>\n");
            sb.append("   document.onkeydown = function (e) { // 回车提交表单\n" +
                    "       var theEvent = window.event || e;\n" +
                    "       var code = theEvent.keyCode || theEvent.which;\n" +
                    "       if (code == 13) {\n" +
                    "           $(\".select .select-on\").click();\n" +
                    "        }\n" +
                    "     }\n");
            sb.append("layui.use('table', function () {\n");
            sb.append("   var table = layui.table;\n" +
                    "     var $ = layui.$;\n" +
                    "    //方法级渲染\n" +
                    "    table.render({\n" +
                    "      id: '"+beanName+"List',\n" +
                    "      elem: '#"+beanName+"List'\n" +
                    "      , url: '/"+ beanName+"/show"+toFirstCharUpCase(beanName)+"List'\n" +
                    "      , cols: [[\n" +
                    "        {checkbox: true, fixed: true, width: '5%'}\n") ;
            for(CreateCodeUtil.ColumnModel c:columnModelList){
                sb.append(

                        "        , {field: '"+c.getFieldName()+"', title: '"+c.getRemarks()+"', width: '"+80/columnModelList.size()+"%', sort: true}\n"
                );
            }
            sb.append(
                    "        , {field: 'remark', title: '操作', width: '20%', toolbar: \"#toolBar\"}\n" +
                    "      ]]\n" +
                    "      , page: true\n" +
                    "      ,  height: 'full-83'\n" +
                    "    });\n");

            sb.append("var active = {");
            sb.append("     select: function () {\n");
            for(CreateCodeUtil.ColumnModel c:columnModelList) {
                sb.append(
                        "        var "+c.getFieldName()+" = $('#"+c.getFieldName()+"').val();\n"
                );
            }
            sb.append(
                    "        table.reload('"+beanName+"List', {\n" +
                    "          where: {\n");
            for(CreateCodeUtil.ColumnModel c:columnModelList) {
                sb.append(
                        "            "+c.getFieldName()+": "+c.getFieldName()+",\n"
                );
            }
            sb.append(
                    "          }\n" +
                    "        });\n" +
                    "      },\n" +
                    "      reload:function(){\n" );
            for(CreateCodeUtil.ColumnModel c:columnModelList) {
                sb.append(
                        "        $('#" + c.getFieldName() + "').val('');\n"
                );
            }
            sb.append(
                    "        table.reload('"+beanName+"List', {\n" +
                    "          where: {\n");
            for(CreateCodeUtil.ColumnModel c:columnModelList) {
                sb.append(
                        "            "+c.getFieldName()+": null,\n"
                );
            }
            sb.append(
                    "          }\n" +
                    "        });\n" +
                    "      },\n" +
                    "      add: function () {\n" +
                    "        add('添加', '/"+beanName+"/show"+toFirstCharUpCase(beanName)+"Add', 700, 450);\n" +
                    "      },\n" +
                    "      update: function () {\n" +
                    "        var checkStatus = table.checkStatus('"+beanName+"List')\n" +
                    "            , data = checkStatus.data;\n" +
                    "        if (data.length != 1) {\n" +
                    "          layer.msg('请选择一行编辑,已选['+data.length+']行', {icon: 5});\n" +
                    "          return false;\n" +
                    "        }\n" +
                    "        update('编辑', '/"+beanName+"/show"+toFirstCharUpCase(beanName)+"Detail?detail=false&id=' + data[0].id, 700, 450);\n" +
                    "      },\n" +
                    "      detail: function () {\n" +
                    "        var checkStatus = table.checkStatus('"+beanName+"List')\n" +
                    "            , data = checkStatus.data;\n" +
                    "        if (data.length != 1) {\n" +
                    "          layer.msg('请选择一行查看,已选['+data.length+']行', {icon: 5});\n" +
                    "          return false;\n" +
                    "        }\n" +
                    "        detail('查看','/"+beanName+"/show"+toFirstCharUpCase(beanName)+"Detail?detail=true&id=' + data[0].id, 700, 450);\n" +
                    "      }\n");
            sb.append("}\n");

            sb.append("//监听表格复选框选择\n" +
                    "    table.on('checkbox("+beanName+"List)', function (obj) {\n" +
                    "        //console.log(obj)\n" +
                    "    });\n");

            sb.append(" //监听工具条\n" +
                    "    table.on('tool("+beanName+"List)', function (obj) {\n" +
                    "      var data = obj.data;\n" +
                    "      if (obj.event === 'detail') {\n" +
                    "        detail('查看','/"+beanName+"/show"+toFirstCharUpCase(beanName)+"Detail?detail=true&id=' + data[0].id, 700, 450);\n" +
                    "      } else if (obj.event === 'del') {\n" +
                    "        layer.confirm('确定删除?', function(){\n" +
                    "          del(data.id);\n" +
                    "        });\n" +
                    "      } else if (obj.event === 'edit') {\n" +
                    "        update('编辑', '/"+beanName+"/show"+toFirstCharUpCase(beanName)+"Detail?detail=false&id=' + data[0].id, 700, 450);\n" +
                    "      }\n" +
                    "    });\n");
            sb.append(" $('.layui-row .layui-btn').on('click', function () {\n" +
                    "      var type = $(this).data('type');\n" +
                    "      active[type] ? active[type].call(this) : '';\n" +
                    "    });\n" +
                    "    $('.select .layui-btn').on('click', function () {\n" +
                    "      var type = $(this).data('type');\n" +
                    "      active[type] ? active[type].call(this) : '';\n" +
                    "    });");
            sb.append("});\n");

            sb.append("function del(id) {\n" +
                    "    $.ajax({\n" +
                    "      url: \"/"+beanName+"/del\",\n" +
                    "      type: \"post\",\n" +
                    "      data: {id: id},\n" +
                    "      success: function (d) {\n" +
                    "        if(d.msg){\n" +
                    "          layer.msg(d.msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});\n" +
                    "          layui.table.reload('"+beanName+"List');\n" +
                    "        }else{\n" +
                    "          layer.msg(d.msg,{icon:5,offset: 'rb',area:['120px','80px'],anim:2});\n" +
                    "        }\n" +
                    "      }\n" +
                    "    });\n" +
                    "  }\n" +
                    "  function detail(title, url, w, h) {\n" +
                    "    layer.open({\n" +
                    "      id: '"+beanName+"-detail',\n" +
                    "      type: 2,\n" +
                    "      area: [w + 'px', h + 'px'],\n" +
                    "      fix: false,\n" +
                    "      maxmin: true,\n" +
                    "      shadeClose: true,\n" +
                    "      shade: 0.4,\n" +
                    "      title: title,\n" +
                    "      content: url ,\n" +
                    "      // btn:['关闭']\n" +
                    "    });\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * 更新用户\n" +
                    "   */\n" +
                    "  function update(title, url, w, h) {\n" +

                    "    layer.open({\n" +
                    "      id: '"+beanName+"-update',\n" +
                    "      type: 2,\n" +
                    "      area: [w + 'px', h + 'px'],\n" +
                    "      fix: false,\n" +
                    "      maxmin: true,\n" +
                    "      shadeClose: false,\n" +
                    "      shade: 0.4,\n" +
                    "      title: title,\n" +
                    "      content: url \n" +
                    "    });\n" +
                    "  }\n" +
                    "\n" +

                    "  function add(title, url, w, h) {\n" +

                    "    layer.open({\n" +
                    "      id: '"+beanName+"-add',\n" +
                    "      type: 2,\n" +
                    "      area: [w + 'px', h + 'px'],\n" +
                    "      fix: false,\n" +
                    "      maxmin: true,\n" +
                    "      shadeClose: false,\n" +
                    "      shade: 0.4,\n" +
                    "      title: title,\n" +
                    "      content: url\n" +
                    "    });\n" +
                    "  }");
            sb.append("</script>\n" +
                    "</body>\n" +
                    "</html>\n");

        }catch (Exception e){

        }
        return sb.toString();
    }
}

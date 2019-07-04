package com.len.util.tool;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.len.util.tool.CreateCodeUtil.toFirstCharUpCase;

/**
 * 创建java代码
 */
public class CreateJavaCodeUtil {
    /**
     * 从表结构中去生成javabean
     *
     * @param columnModelList
     * @param beanName
     * @return
     */
    public static String genJavaBeanFromTableStructure(String classPath,List<CreateCodeUtil.ColumnModel> columnModelList, String beanName, String tableName) {
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("package "+classPath + ".entity;\n" +
                    "import lombok.Data;\n" +
                    "import javax.persistence.Column;\n" +
                    "import javax.persistence.Id;\n" +
                    "import javax.persistence.Table;\n" +
                    "import java.util.Date;\n");

            sb.append("@Table(name=\""+tableName+"\")\n");
            sb.append("@Data\n");

            sb.append("public class " + toFirstCharUpCase(beanName) + " {\r\n");
            int idx = 0;
            for (CreateCodeUtil.ColumnModel columnModel : columnModelList) {
                if (StringUtils.isNotBlank(columnModel.getRemarks())) {
                    sb.append(" //" + columnModel.getRemarks() + " \r\n");
                }
                if(idx ==0){
                    sb.append("@Id\n");
                }
                sb.append("@Column(name = \""+columnModel.getFieldOldName()+"\")\n");
                sb.append(" private " + columnModel.getFieldType() + " " + columnModel.getFieldName() + ";\r\n");
                idx++;
            }
            sb.append("\r\n");
            //get set
//            for (ColumnModel columnModel : columnModelList) {
//                sb.append(
//                        "\tpublic String get" + toFirstCharUpCase((String) columnModel.getFieldName()) + "() {\r\n" +
//                                "\t\treturn " + columnModel.getFieldName() + ";\r\n" +
//                                "\t}\r\n" +
//                                "\r\n" +
//                                "\tpublic void set" + toFirstCharUpCase((String) columnModel.getFieldName()) + "(String " + columnModel.getFieldName() + ") {\r\n" +
//                                "\t\t" + columnModel.getFieldName() + " = " + columnModel.getFieldName() + ";\r\n" +
//                                "\t}\r\n\r\n");
//            }
            sb.append("}\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static String createJavaMapper(String classPath,List<CreateCodeUtil.ColumnModel> columnModelList, String beanName, String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("package "+classPath + ".mapper;\n")
           .append("import com.len.base.BaseMapper;\n" +
                   "import "+classPath+".entity."+toFirstCharUpCase(beanName)+";\n")
                .append("public interface "+toFirstCharUpCase(beanName)+"Mapper extends BaseMapper<"+toFirstCharUpCase(beanName)+", String> {\n")
                .append("}");
        return sb.toString();
    }

    public static String createJavaService(String classPath,List<CreateCodeUtil.ColumnModel> columnModelList, String beanName, String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("package "+classPath + ".service;\n")
                .append("import com.len.base.BaseService;\n" +
                        "import "+classPath+".entity."+toFirstCharUpCase(beanName)+";\n")
                .append("public interface "+toFirstCharUpCase(beanName)+"Service extends BaseService<"+toFirstCharUpCase(beanName)+", String> {\n")
                .append("}");
        return sb.toString();
    }
    public static String createJavaServiceImpl(String classPath,List<CreateCodeUtil.ColumnModel> columnModelList, String beanName, String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("package "+classPath + ".service.impl;\n")
                .append("import com.len.base.BaseMapper;\n"+
                        "import com.len.base.impl.BaseServiceImpl;\n" +
                        "import "+classPath+".entity."+toFirstCharUpCase(beanName)+";\n"+
                        "import "+classPath+".mapper."+toFirstCharUpCase(beanName)+"Mapper;\n" +
                        "import "+classPath+".service."+toFirstCharUpCase(beanName)+"Service;\n" +
                        "import org.springframework.beans.factory.annotation.Autowired;\n" +
                        "import org.springframework.stereotype.Service;\n")
                .append("@Service\n")
                .append("public class "+toFirstCharUpCase(beanName)+"ServiceImpl extends BaseServiceImpl<"+toFirstCharUpCase(beanName)+", String> implements "+toFirstCharUpCase(beanName)+"Service {\n")
                .append("\t@Autowired\n")
                .append("\tprivate "+toFirstCharUpCase(beanName)+"Mapper "+beanName+"Mapper;\n")
                .append("    @Override\n" +
                        "    public BaseMapper<"+toFirstCharUpCase(beanName)+", String> getMappser() {\n" +
                        "        return "+beanName+"Mapper;\n" +
                        "    }\n")
                .append("}");
        return sb.toString();
    }

    public static String createJavaController(String classPath,List<CreateCodeUtil.ColumnModel> columnModelList, String beanName, String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("package "+classPath + ".controller;\n")
                .append("import com.github.pagehelper.Page;\n" +
                        "import com.github.pagehelper.PageHelper;\n" +
                        "import com.len.base.BaseController;\n" +
                        "import java.util.List;\n" +
                        "import "+classPath+".entity."+toFirstCharUpCase(beanName)+";\n"+
                        "import com.len.exception.MyException;\n" +
                        "import "+classPath+".service."+toFirstCharUpCase(beanName)+"Service;\n"+
                        "import com.len.util.JsonUtil;\n" +
                        "import com.len.util.ReType;\n" +
                        "import lombok.extern.slf4j.Slf4j;\n" +
                        "import org.apache.commons.lang3.StringUtils;\n" +
                        "import org.apache.shiro.authz.annotation.RequiresPermissions;\n" +
                        "import org.springframework.beans.factory.annotation.Autowired;\n" +
                        "import org.springframework.stereotype.Controller;\n" +
                        "import org.springframework.ui.Model;\n" +
                        "import org.springframework.web.bind.annotation.*;\n"+
                        "import javax.servlet.http.HttpServletRequest;\n" +
                        "import javax.servlet.http.HttpServletResponse;\n"
                )
                .append("@CrossOrigin\n" +
                        "@Controller\n" +
                        "@Slf4j\n" +
                        "@RequestMapping(\"/"+beanName+"\")\n" +
                        "public class "+toFirstCharUpCase(beanName)+"Controller extends BaseController {\n")
                        .append("    @Autowired\n" +
                                "    private "+toFirstCharUpCase(beanName)+"Service "+beanName+"Service;\n\n" +
                                "    /**\n" +
                                "     *展示首页\n" +
                                "     */\n" +
                                "    @GetMapping(\"/show"+toFirstCharUpCase(beanName)+"\")\n" +
                                "    @RequiresPermissions(\""+beanName+":show\")\n" +
                                "    public String "+beanName+"ListPage(HttpServletRequest req, HttpServletResponse resp) {\n" +
                                "        return \"/"+beanName+"\";\n" +
                                "    }\n")
                        .append("    /**\n" +
                                "     * 分页\n" +
                                "     */\n" +
                                "    @GetMapping(value = \"/show"+toFirstCharUpCase(beanName)+"List\")\n" +
                                "    @ResponseBody\n" +
                                "    @RequiresPermissions(\""+beanName+":show\")\n" +
                                "    public ReType show"+toFirstCharUpCase(beanName)+"List("+toFirstCharUpCase(beanName)+" "+beanName+", String page, String limit) {\n" +
                                "        List<"+toFirstCharUpCase(beanName)+"> tList = null;\n" +
                                "        Page<"+toFirstCharUpCase(beanName)+"> tPage = PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(limit));\n" +
                                "        try {\n" +
                                "            tList = "+beanName+"Service.selectListByPage("+beanName+");\n" +
                                "        } catch (MyException e) {\n" +
                                "            log.error(\"class:"+ toFirstCharUpCase(beanName)+"Controller ->method:show"+toFirstCharUpCase(beanName)+"List->message:\" + e.getMessage());\n" +
                                "            e.printStackTrace();\n" +
                                "        }\n" +
                                "        return new ReType(tPage.getTotal(), tList);\n" +
                                "    }\n")
                        .append("/**\n" +
                                "     * 展示新增页面\n" +
                                "     */\n" +
                                "    @GetMapping(value = \"/show"+toFirstCharUpCase(beanName)+"Add\")\n" +
                                "    @RequiresPermissions(\""+beanName+":add\")\n" +
                                "    public String show"+toFirstCharUpCase(beanName)+"Add(Model model) {\n" +
                                "        return \"/add-"+beanName+"\";\n" +
                                "    }\n")
                        .append(" /**\n" +
                                "     * 展示修改|查看页面\n" +
                                "     */\n" +
                                "    @GetMapping(value = \"/sho"+toFirstCharUpCase(beanName)+"Detail\")\n" +
                                "    @RequiresPermissions(\""+beanName+":select\")\n" +
                                "    public String show"+toFirstCharUpCase(beanName)+"Detail(String id, Model model, boolean detail) {\n" +
                                "        if (StringUtils.isNotEmpty(id)) {\n" +
                                "            "+toFirstCharUpCase(beanName)+" "+beanName +" = "+beanName+"Service.selectByPrimaryKey(id);\n" +
                                "            model.addAttribute(\""+beanName+"\", "+beanName+");\n" +
                                "        }\n" +
                                "        model.addAttribute(\"detail\", detail);\n" +
                                "        return \"/update-"+beanName+"\";\n" +
                                "    }\n")
                        .append("/**\n" +
                                "     * 添加\n" +
                                "     */\n" +
                                "    @PostMapping(value = \"/add"+toFirstCharUpCase(beanName)+"\")\n" +
                                "    @RequiresPermissions(\""+beanName+":add\")\n" +
                                "    @ResponseBody\n" +
                                "    public JsonUtil add"+toFirstCharUpCase(beanName)+"("+toFirstCharUpCase(beanName)+" "+beanName+") {\n" +
                                "        log.info(\"入参\" + "+beanName+");\n" +
                                "        JsonUtil j = new JsonUtil();\n" +
                                "        String msg = \"添加成功\";\n" +
                                "        try {\n" +
                                "            "+beanName+"Service.insertSelective("+beanName+");\n" +
                                "        } catch (MyException e) {\n" +
                                "            msg = \"添加失败\";\n" +
                                "            j.setFlag(false);\n" +
                                "            e.printStackTrace();\n" +
                                "        }\n" +
                                "        j.setMsg(msg);\n" +
                                "        return j;\n" +
                                "    }\n")
                        .append("/**\n" +
                                "     * 更新\n" +
                                "     */\n" +
                                "    @PostMapping(value = \"/update"+toFirstCharUpCase(beanName)+"\")\n" +
                                "    @ResponseBody\n" +
                                "    @RequiresPermissions(\""+beanName+":update\")\n" +
                                "    public JsonUtil update"+toFirstCharUpCase(beanName)+"("+toFirstCharUpCase(beanName)+" "+beanName+") {\n" +
                                "        JsonUtil j = new JsonUtil();\n" +
                                "        if ("+beanName+" == null) {\n" +
                                "            j.setFlag(false);\n" +
                                "            j.setMsg(\"获取数据失败\");\n" +
                                "            return j;\n" +
                                "        }\n" +
                                "        if ("+beanName+"Service.updateByPrimaryKeySelective("+beanName+") > 0) {\n" +
                                "            j.setFlag(true);\n" +
                                "            j.setMsg(\"更新成功\");\n" +
                                "        } else {\n" +
                                "            j.setFlag(false);\n" +
                                "            j.setMsg(\"更新失败\");\n" +
                                "        }\n" +
                                "        return j;\n" +
                                "    }\n")
                        .append("@PostMapping(\"/del\")\n" +
                                "    @ResponseBody\n" +
                                "    @RequiresPermissions(\""+beanName+":del\")\n" +
                                "    public JsonUtil update"+toFirstCharUpCase(beanName)+"(Integer userNum) {\n" +
                                "        JsonUtil j = new JsonUtil();\n" +
                                "        if (userNum == null) {\n" +
                                "            j.setFlag(false);\n" +
                                "            j.setMsg(\"刪除数据失败\");\n" +
                                "            return j;\n" +
                                "        }\n" +
                                "        if ("+beanName+"Service.deleteByPrimaryKey(userNum) > 0) {\n" +
                                "            j.setFlag(true);\n" +
                                "            j.setMsg(\"刪除成功\");\n" +
                                "        } else {\n" +
                                "            j.setFlag(false);\n" +
                                "            j.setMsg(\"刪除失败\");\n" +
                                "        }\n" +
                                "        return j;\n" +
                                "    }")
                .append("}");
        return sb.toString();
    }

}

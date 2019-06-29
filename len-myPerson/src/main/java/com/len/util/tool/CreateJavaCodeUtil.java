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
                        "import com.len.core.shiro.Principal;\n" +
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
                                "    @GetMapping(\"/show"+toFirstCharUpCase(beanName)+"\")\n" +
                                "    @RequiresPermissions(\""+beanName+":show\")\n" +
                                "    public String "+beanName+"ListPage(HttpServletRequest req, HttpServletResponse resp) {\n" +
                                "        return \"/"+beanName+"\";\n" +
                                "    }\n")
                .append("}");
        return sb.toString();
    }

}

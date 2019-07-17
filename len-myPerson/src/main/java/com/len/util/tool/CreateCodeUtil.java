package com.len.util.tool;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 自动生成代码工具类
 */
public class CreateCodeUtil {
    public static void main(String[] args) {
        //在控制台输出DTO
       saveCode("F://code//","com.len",null,"boke_user_sign","userSign");
       System.out.println("输出文件成功");
    }

    /**
     * 保存自动生成的代码
     * @param saveDir 代码输出的目录
     * @param packageName 包名
     * @param childPackage 子包名：为空表示不生成子包
     * @param tableName 表名
     * @param beanName  生成的bean名称
     */
    public static void saveCode(String saveDir,String packageName,String childPackage,String tableName,String beanName){
        //获取表结构信息
        List<ColumnModel> columnModelList = getTableStructure(tableName);
        //类全路径
        String classPath = packageName.trim();
        if(StringUtils.isNotEmpty(childPackage)){
            classPath = classPath + "." + childPackage.trim();
        }
        String dtoStr = CreateJavaCodeUtil.genJavaBeanFromTableStructure(classPath,columnModelList, beanName,tableName);
        //System.out.println(dtoStr);
        String dto = saveDir + toFirstCharUpCase(beanName)+".java";
        saveAsFile(dto,dtoStr);
        //Mapper.xml文件
        String  xmlStr= CreateMapperXMLUtil.createMapperXMLUtil(classPath,columnModelList,beanName,tableName);
        //System.out.println(xmlStr);
        String xml = saveDir + toFirstCharUpCase(beanName)+"Mapper.xml";
        saveAsFile(xml,xmlStr);
        //mapper.java
        String mapperStr = CreateJavaCodeUtil.createJavaMapper(classPath,columnModelList,beanName,tableName);
        String mapper = saveDir + toFirstCharUpCase(beanName)+"Mapper.java";
        saveAsFile(mapper,mapperStr);
        //service.java
        String serviceStr = CreateJavaCodeUtil.createJavaService(classPath,columnModelList,beanName,tableName);
        String service = saveDir + toFirstCharUpCase(beanName)+"Service.java";
        saveAsFile(service,serviceStr);
        //serviceImpl.java
        String serviceImplStr = CreateJavaCodeUtil.createJavaServiceImpl(classPath,columnModelList,beanName,tableName);
        String serviceImpl = saveDir + toFirstCharUpCase(beanName)+"ServiceImpl.java";
        saveAsFile(serviceImpl,serviceImplStr);
        //controller.java
        String controllerStr = CreateJavaCodeUtil.createJavaController(classPath,columnModelList,beanName,tableName);
        String controller = saveDir + toFirstCharUpCase(beanName)+"Controller.java";
        saveAsFile(controller,controllerStr);

        String htmlListStr = CreateHtmlCodeUtil.createFtlFile(classPath,columnModelList,beanName,tableName);
        String articleFtl = saveDir + beanName+".ftl";
        saveAsFile(articleFtl,htmlListStr);
    }
    private static void saveAsFile(String file ,String cotent){
        byte[] contentInBytes = cotent.getBytes();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(contentInBytes);
            fos.flush();
            fos.close();
        } catch (Exception e) {

        }


    }
    /**
     * 获取表结构
     *
     * @param tableName
     * @return
     */
    public static List<ColumnModel> getTableStructure(String tableName) {
        List<ColumnModel> columnModelList = new ArrayList<ColumnModel>();
        try {
            //TODO 表相关
            //ResultSet tableSet = metaData.getTables(null, "%",tableName,new String[]{"TABLE"});
            //TODO 字段相关
            ResultSet columnSet = getConnection().getMetaData().getColumns(null, "%", tableName, "%");
            ColumnModel columnModel = null;
            while (columnSet.next()) {
                columnModel = new ColumnModel();
                columnModel.setColumnName(columnSet.getString("COLUMN_NAME"));
                columnModel.setColumnSize(columnSet.getInt("COLUMN_SIZE"));
                columnModel.setDataType(columnSet.getString("DATA_TYPE"));
                columnModel.setRemarks(columnSet.getString("REMARKS"));
                columnModel.setTypeName(columnSet.getString("TYPE_NAME"));
                String columnClassName = ColumnTypeEnum.getColumnTypeEnumByDBType(columnModel.getTypeName());
                String fieldName = getFieldName(columnModel.getColumnName());
                String fileOldName = columnModel.getColumnName();
                String fieldType = Class.forName(columnClassName).getSimpleName();
                columnModel.setFieldName(fieldName);
                columnModel.setFieldOldName(fileOldName);
                columnModel.setColumnClassName(columnClassName);
                columnModel.setFieldType(fieldType);
                columnModelList.add(columnModel);
                //System.out.println(columnModel.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnModelList;
    }

    /**
     * 将数据库字段转换成bean属性(针对驼峰类型的写法)
     *
     * @param columnName
     * @return
     */
    private static String getFieldName(String columnName) {
        // char[] columnCharArr = columnName.toLowerCase().toCharArray();
        char[] columnCharArr = columnName.toCharArray();
        StringBuffer sb = new StringBuffer();
        int ad = -1;
        for (int i = 0; i < columnCharArr.length; i++) {
            char cur = columnCharArr[i];
            if (cur == '_') {
                ad = i;
            } else {
                if ((ad + 1) == i && ad != -1) {
                    sb.append(Character.toUpperCase(cur));
                } else {
                    sb.append(cur);
                }
                ad = -1;
            }
        }
        return sb.toString();
    }

    /**
     * 获取连接
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/boke?useUnicode=true&characterEncoding=UTF-8";
            String user = "root";
            String password = "123456";
            Properties properties = new Properties();
            properties.put("user", user);
            properties.put("password", password);
            properties.put("remarksReporting", "true");//想要获取数据库结构中的注释，这个值是重点
            return DriverManager.getConnection(url, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将首字母变大写
     *
     * @param str
     * @return
     */
    public static String toFirstCharUpCase(String str) {
        char[] columnCharArr = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < columnCharArr.length; i++) {
            char cur = columnCharArr[i];
            if (i == 0) {
                sb.append(Character.toUpperCase(cur));
            } else {
                sb.append(cur);
            }
        }
        return sb.toString();
    }


    //列模型
    @Data
    static class ColumnModel {
        private String columnName;//数据字段名称
        private String dataType;//数据类型编码
        private String typeName;//数据类型
        private String columnClassName;//java类型
        private String fieldName;//java属性名称
        private String fieldType;//java属性类型
        private int columnSize;//长度
        private String columnDef;//默认值
        private String remarks;//描述
        private String fieldOldName;//数据字段名称
        @Override
        public String toString() {
            return "ColumnModel [columnName=" + columnName + ", dataType="
                    + dataType + ", typeName=" + typeName + ", columnClassName="
                    + columnClassName + ", fieldName=" + fieldName + ", fieldType="
                    + fieldType + ", columnSize=" + columnSize + ", columnDef="
                    + columnDef + ", remarks=" + remarks + "，fieldOldName="+fieldOldName+"]";
        }
    }
}

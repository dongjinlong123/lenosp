package com.len.util.tool;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 自动读取数据库表字段描述生成DTO
 */
public class CreateDTOUtil {
    public static void main(String[] args) {
        System.out.println(getConnection());
        //获取表结构信息
        List<ColumnModel> columnModelList = getTableStructure("ecjtu_ie_user_info");
        //在控制台输出DTO
        System.out.println(genJavaBeanFromTableStructure(columnModelList, "HJPerson"));
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
                String fieldType = Class.forName(columnClassName).getSimpleName();
                columnModel.setFieldName(fieldName);
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
     * 将数据库字段转换成bean属性
     *
     * @param columnName
     * @return
     */
    private static String getFieldName(String columnName) {
        char[] columnCharArr = columnName.toLowerCase().toCharArray();
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
     * 从表结构中去生成javabean
     *
     * @param columnModelList
     * @param beanName
     * @return
     */
    public static String genJavaBeanFromTableStructure(List<ColumnModel> columnModelList, String beanName) {
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("public class " + toFirstCharUpCase(beanName) + " {\r\n");
            for (ColumnModel columnModel : columnModelList) {
                if (StringUtils.isNotBlank(columnModel.getRemarks())) {
                    sb.append(" //" + columnModel.getRemarks() + " \r\n");
                }
                sb.append(" private " + columnModel.getFieldType() + " " + columnModel.getFieldName() + ";\r\n");
            }
            sb.append("\r\n");
            //get set
            for (ColumnModel columnModel : columnModelList) {
                sb.append(
                        "\tpublic String get" + toFirstCharUpCase((String) columnModel.getFieldName()) + "() {\r\n" +
                                "\t\treturn " + columnModel.getFieldName() + ";\r\n" +
                                "\t}\r\n" +
                                "\r\n" +
                                "\tpublic void set" + toFirstCharUpCase((String) columnModel.getFieldName()) + "(String " + columnModel.getFieldName() + ") {\r\n" +
                                "\t\t" + columnModel.getFieldName() + " = " + columnModel.getFieldName() + ";\r\n" +
                                "\t}\r\n\r\n");
            }
            sb.append("}\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
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
        private String columnName;
        private String dataType;
        private String typeName;
        private String columnClassName;
        private String fieldName;
        private String fieldType;
        private int columnSize;
        private String columnDef;
        private String remarks;

        @Override
        public String toString() {
            return "ColumnModel [columnName=" + columnName + ", dataType="
                    + dataType + ", typeName=" + typeName + ", columnClassName="
                    + columnClassName + ", fieldName=" + fieldName + ", fieldType="
                    + fieldType + ", columnSize=" + columnSize + ", columnDef="
                    + columnDef + ", remarks=" + remarks + "]";
        }
    }
}

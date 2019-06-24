package com.len.util.tool;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 根据dto创建mapper文件
 */
public class CreateMapperXMLUtil {
    /**
     * 创建常用的mapper xml文件
     * @param columnModelList 表结构信息
     * @param beanName 对于的dto的名字
     */
    public static String createMapperXMLUtil( List<CreateDTOUtil.ColumnModel> columnModelList,String beanName,String tableName){
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                    "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
            sb.append("<mapper namespace=\"com.len.mapper."+beanName+"Mapper\">\n");//引入对于的javaMapper文件

            //创建BaseResultMap
            sb = createBaseResultMap(sb,columnModelList,beanName,tableName);

            //创建分页sql（依赖BaseResultMap）
            sb = createPageSql(sb,columnModelList,beanName,tableName);

            //添加
           sb = addSql(sb,columnModelList,beanName,tableName);

            sb.append("</mapper>\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static StringBuffer createPageSql(StringBuffer sb, List<CreateDTOUtil.ColumnModel> columnModelList, String beanName, String tableName) {
        sb.append("\t<!--分页 -->\n");
        sb.append("\t <select id=\"selectListByPage\" parameterType=\"com.len.entity."+beanName+"\" resultMap=\"BaseResultMap\">\n");
        sb.append("\t\tselect * from "+tableName+"\n");
        sb.append("\t\t <include refid=\"whereSql\" />\n");//默认查询的sql
        sb.append("\t</select>\n");

        sb.append("\t<select id=\"count\" resultType=\"java.lang.Integer\">\n");
        sb.append("\t\tselect count(1) from "+tableName+"\n");
        sb.append("\t\t <include refid=\"whereSql\" />\n");//默认查询的sql
        sb.append("\t</select>\n");

        //默认查询的sql
        sb.append("\t<!--默认查询的sql -->\n");
        sb.append("\t<sql id=\"whereSql\">\n");
        sb.append("\t\t<where>\n");
        for (CreateDTOUtil.ColumnModel columnModel : columnModelList) {
            sb.append("\t\t\t<if test=\""+columnModel.getFieldName()+" !=null and "+columnModel.getFieldName()+" !=''\"> and "+columnModel.getFieldOldName()+"=#{"+columnModel.getFieldName()+"}</if>\n");
        }
        sb.append("\t\t</where>\n");
        sb.append("\t</sql>\n");
        return sb;
    }

    private static StringBuffer createBaseResultMap(StringBuffer sb, List<CreateDTOUtil.ColumnModel> columnModelList, String beanName, String tableName) {
        //BaseResultMap
        sb.append("\t<resultMap id=\"BaseResultMap\" type=\"com.len.entity."+beanName+"\">\n");//对于的dto
        int idx = 0;
        for (CreateDTOUtil.ColumnModel columnModel : columnModelList) {
            //INT -- INTEGER
            String typeName = columnModel.getTypeName();
            if("INT".equals(typeName)){
                typeName = "INTEGER";
            }
            if("DATETIME".equals(typeName)){
                typeName="TIMESTAMP";
            }

            if(idx == 0){
                sb.append("\t\t<id column=\""+columnModel.getFieldOldName() +"\" jdbcType=\""+ typeName+"\" property=\""+  columnModel.getFieldName()+"\" />\n");
            }else{
                sb.append("\t\t<result column=\""+columnModel.getFieldOldName() +"\" jdbcType=\""+typeName+"\" property=\""+  columnModel.getFieldName()+"\" />\n");
            }
            idx++;
        }
        sb.append("\t</resultMap>\n");
        return sb;
    }

    private static StringBuffer addSql(StringBuffer sb,List<CreateDTOUtil.ColumnModel> columnModelList,String beanName,String tableName) {
        //添加
        sb.append("\t<!-- 添加 -->\n");
        sb.append("\t<insert id=\"add\" parameterType=\"com.len.entity."+beanName+"\">\n");
        sb.append("\t\t<selectKey  keyProperty=\""+columnModelList.get(0).getFieldName()+"\" order=\"AFTER\" resultType=\"java.lang.Integer\">\n");
        sb.append("\t\t\t  select LAST_INSERT_ID()\n");
        sb.append("\t\t </selectKey>\n");
        sb.append("\t\t insert into "+tableName+"( <include refid=\"dmlColumn\"/>) values (<include refid=\"dmlValue\"/>)");
        sb.append("\t</insert>\n");
        //dmlColumn 和 dmlValue sql片段
        sb.append("\t<sql id=\"dmlColumn\">\n");
        sb.append("\t\t  <trim suffix=\"\" suffixOverrides=\",\">\n");
        for (CreateDTOUtil.ColumnModel columnModel : columnModelList) {
            sb.append("\t\t\t  <if test=\""+ columnModel.getFieldName()+"!=null\">\n");
            sb.append("\t\t\t\t "+columnModel.getFieldName()+",\n");
            sb.append("\t\t\t </if>\n");
        }

        sb.append("\t\t </trim>\n");
        sb.append("\t</sql>\n");


        sb.append("\t<sql id=\"dmlValue\">\n");
        sb.append("\t\t  <trim suffix=\"\" suffixOverrides=\",\">\n");
        for (CreateDTOUtil.ColumnModel columnModel : columnModelList) {
            sb.append("\t\t\t  <if test=\""+ columnModel.getFieldName()+"!=null\">\n");
            sb.append("\t\t\t\t #{"+columnModel.getFieldName()+"},\n");
            sb.append("\t\t\t </if>\n");
        }

        sb.append("\t\t </trim>\n");
        sb.append("\t</sql>\n");
        return sb;
    }

}

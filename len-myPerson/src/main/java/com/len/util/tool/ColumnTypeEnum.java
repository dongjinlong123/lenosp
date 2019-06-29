package com.len.util.tool;

/**
 * 数据库类型枚举
 */
public enum ColumnTypeEnum {
    VARCHAR("VARCHAR", "java.lang.String"),
    BIGINT("BIGINT", "java.lang.Integer"),
    DATE("DATETIME", "java.util.Date"),
    TINYINT("TINYINT","java.lang.Integer"),
    INT("INT", "java.lang.Integer");

    private String dbType;
    private String javaType;

    ColumnTypeEnum(String dbType, String javaType) {
        this.dbType = dbType;
        this.javaType = javaType;
    }

    public static String getColumnTypeEnumByDBType(String dbType) {
        for (ColumnTypeEnum columnTypeEnum : ColumnTypeEnum.values()) {
            if (columnTypeEnum.getDbType().equals(dbType)) {
                return columnTypeEnum.getJavaType();
            }
        }
        return "";
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
}  
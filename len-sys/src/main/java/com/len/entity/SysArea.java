package com.len.entity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name="sys_area")
@Data
public class SysArea {
 //地区Id 
@Id
@Column(name = "area_id")
 private Integer areaId;
 //地区编码 
@Column(name = "area_code")
 private String areaCode;
 //地区名 
@Column(name = "area_name")
 private String areaName;
 //地区级别（1:省份province,2:市city,3:区县district,4:街道street） 
@Column(name = "level")
 private Integer level;
 //城市编码 
@Column(name = "city_code")
 private String cityCode;
 //城市中心点（即：经纬度坐标） 
@Column(name = "center")
 private String center;
 //地区父节点 
@Column(name = "parent_id")
 private Integer parentId;

}

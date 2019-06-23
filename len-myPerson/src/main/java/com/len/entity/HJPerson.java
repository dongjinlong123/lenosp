package com.len.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name="ecjtu_ie_user_info")
@Data
public class HJPerson {
    //用户编码
    @Id
    private Integer userNum;
    //创建者
    private String createdBy;
    //创建时间
    private Date createdDate;
    //更新者
    private String lastUpdateBy;
    //更新时间
    private Date lastUpdateDate;
    //是否愿意成为校友联络员服务大家
    private String isWantLiaisonMan;
    //姓名
    private String userName;
    //性别
    private String sex;
    //入学年份
    private String studyYear;
    //班级（不分班级的请选择0）
    private Integer classNum;
    //学号
    private String studentId;
    //出生日期
    private String birthday;
    //现居住城市
    private String liveCity;
    //手机号码
    private String telphone;
    //邮箱
    private String email;
    //微信号
    private String weChat;
    //qq
    private String qq;
    //在职行业
    private String profession;
    //单位全称+具体部门
    private String company;
    //职务
    private String job;
    //职称
    private String title;
    //在校期间班委职务
    private String schoolJob;
    //本科毕业后是否继续攻读硕士？
    private String isGraduate;
    //硕士毕业院校
    private String graduateSchool;
    //硕士攻读专业
    private String graduateDegree;
    //硕士学位
    private String masterDegree;
    //硕导
    private String masterGuide;
    //硕士毕业后是否继续攻读博士？
    private String isDoctor;
    //博士毕业院校
    private String doctorSchool;
    //博士攻读专业
    private String doctorDegree;
    //博士学位
    private String doctorMasterDegree;
    //博导
    private String doctorGuide;
    //工业工程校友会期待大家的idea：
    private String idea;
}

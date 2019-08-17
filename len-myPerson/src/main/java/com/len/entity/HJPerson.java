package com.len.entity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name="ecjtu_ie_user_info")
@Data
public class HJPerson {
    //用户编码
    @Id
    @Column(name = "user_num")
    private Integer userNum;
    //创建者
    @Column(name = "created_by")
    private String createdBy;
    //创建时间
    @Column(name = "created_date")
    private Date createdDate;
    //更新者
    @Column(name = "last_update_by")
    private String lastUpdateBy;
    //更新时间
    @Column(name = "last_update_date")
    private Date lastUpdateDate;
    //是否愿意成为校友联络员服务大家
    @Column(name = "is_wantLiaison_man")
    private String isWantliaisonMan;
    //姓名
    @Column(name = "use_name")
    private String useName;
    //性别
    @Column(name = "sex")
    private String sex;
    //入学年份
    @Column(name = "study_year")
    private String studyYear;
    //班级（不分班级的请选择0）
    @Column(name = "class_num")
    private Integer classNum;
    //学号
    @Column(name = "student_id")
    private String studentId;
    //出生日期
    @Column(name = "birthday")
    private String birthday;
    //现居住城市
    @Column(name = "live_city")
    private String liveCity;
    //手机号码
    @Column(name = "telphone")
    private String telphone;
    //邮箱
    @Column(name = "email")
    private String email;
    //微信号
    @Column(name = "we_chat")
    private String weChat;
    //qq
    @Column(name = "qq")
    private String qq;
    //在职行业
    @Column(name = "profession")
    private String profession;
    //单位全称+具体部门
    @Column(name = "company")
    private String company;
    //职务
    @Column(name = "job")
    private String job;
    //职称
    @Column(name = "title")
    private String title;
    //在校期间班委职务
    @Column(name = "school_job")
    private String schoolJob;
    //本科毕业后是否继续攻读硕士
    @Column(name = "is_graduate")
    private String isGraduate;
    //硕士毕业院校
    @Column(name = "graduate_school")
    private String graduateSchool;
    //硕士攻读专业
    @Column(name = "graduate_degree")
    private String graduateDegree;
    //硕士学位
    @Column(name = "master_degree")
    private String masterDegree;
    //硕导
    @Column(name = "master_guide")
    private String masterGuide;
    //硕士毕业后是否继续攻读博士
    @Column(name = "is_doctor")
    private String isDoctor;
    //博士毕业院校
    @Column(name = "doctor_school")
    private String doctorSchool;
    //博士攻读专业
    @Column(name = "doctor_degree")
    private String doctorDegree;
    //博士学位
    @Column(name = "doctor_master_degree")
    private String doctorMasterDegree;
    //博导
    @Column(name = "doctor_guide")
    private String doctorGuide;
    //工业工程校友会期待大家的idea
    @Column(name = "idea")
    private String idea;

    //省市区
    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "area")
    private String area;
}
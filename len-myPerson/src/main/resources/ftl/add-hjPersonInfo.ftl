<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>添加校友信息</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/build/css/city.css">
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/modules/laydate/default/laydate.css">
  <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
    <script src="${re.contextPath}/plugin/layui/lay/modules/laydate.js" charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/common.js" charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js"></script>
    <style>
        /*重写label 的宽度*/
        .layui-form-pane .layui-form-label{
            width: 160px;
        }

         input {
             border: 1px solid #b2b2b2 !important;
         }

    </style>
</head>

<body>

<div class="x-body">
  <form class="layui-form layui-form-pane" style="margin-left: 20px;">
    <div style="width:100%;height:100%;overflow: auto;">

      <div class="layui-form-item">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
          <legend style="font-size:16px;">基本信息</legend>
        </fieldset>
      </div>

      <div class="layui-form-item">
        <label for="useName" class="layui-form-label">
          <span class="x-red">*</span>姓名
       </label>
        <div class="layui-input-inline">
            <input type="text"  id="useName" name="useName"  lay-verify="useName"
                   autocomplete="off" class="layui-input">
        </div>

          <label for="sex" class="layui-form-label">
           <span class="x-red">*</span>性别
          </label>
          <div class="layui-input-inline" style="width:190px;">
              <select  id="sex" name="sex" lay-verify="required">
                  <option value="男">男</option>
                  <option value="女">女</option>
              </select>
          </div>
      </div>

        <div class="layui-form-item">
            <label for="studyYear" class="layui-form-label">
                <span class="x-red">*</span>入学年份
            </label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="studyYear" name="studyYear"  lay-verify="studyYear" readonly="readonly">
            </div>


            <label for="class_num" class="layui-form-label">
                <span class="x-red">*</span>班级
            </label>
            <div class="layui-input-inline" style="width:190px;">
                <select  id="classNum" name="classNum" lay-verify="required">
                    <option value="1">1班</option>
                    <option value="2">2班</option>
                    <option value="3">3班</option>
                    <option value="4">4班</option>
                    <option value="5">5班</option>
                    <option value="6">6班</option>
                    <option value="7">7班</option>
                    <option value="8">8班</option>
                    <option value="9">9班</option>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label for="studentId" class="layui-form-label">
                <span class="x-red"></span>学号
            </label>
            <div class="layui-input-inline">
                <input type="text"  id="studentId" name="studentId"  autocomplete="off" class="layui-input">
            </div>

            <label for="birthday" class="layui-form-label">
                <span class="x-red"></span>出生日期
            </label>
            <div class="layui-input-inline">
                <input type="text"  id="birthday" name="birthday"  autocomplete="off" class="layui-input" lay-verify="birthday" readonly="readonly">
            </div>

        </div>


        <div class="layui-form-item">
            <label for="qq" class="layui-form-label">
                <span class="x-red"></span>qq
            </label>
            <div class="layui-input-inline">
                <input type="text"  id="qq" name="qq"  autocomplete="off" class="layui-input">
            </div>

            <label for="telphone" class="layui-form-label">
                <span class="x-red">*</span>手机号码
            </label>
            <div class="layui-input-inline">
                <input type="text"  id="telphone" name="telphone"  autocomplete="off" class="layui-input" lay-verify="required|phone">
            </div>
        </div>




      <div class="layui-form-item">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
          <legend style="font-size:16px;">工作信息</legend>
        </fieldset>
      </div>


        <div class="layui-form-item">
            <label for="liveCity" class="layui-form-label">
                <span class="x-red"></span>居住城市
            </label>
            <div class="layui-input-inline">
                <input type="text"  id="liveCity" name="liveCity"  autocomplete="off" class="layui-input">
            </div>

            <label for="profession" class="layui-form-label">
                <span class="x-red"></span>在职行业
            </label>
            <div class="layui-input-inline">
                <input type="text"  id="profession" name="profession"  autocomplete="off" class="layui-input">
            </div>
        </div>


        <div class="layui-form-item">
            <label for="company" class="layui-form-label">
                <span class="x-red"></span>单位全称
            </label>
            <div class="layui-input-inline">
                <input type="text"  id="company" name="company"  autocomplete="off" class="layui-input">
            </div>

            <label for="job" class="layui-form-label">
                <span class="x-red"></span>职务
            </label>
            <div class="layui-input-inline">
                <input type="text"  id="job" name="job"  autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label for="title" class="layui-form-label">
                <span class="x-red"></span>职称
            </label>
            <div class="layui-input-inline">
                <input type="text"  id="title" name="title"  autocomplete="off" class="layui-input">
            </div>
            <label for="schoolJob" class="layui-form-label">
                <span class="x-red"></span>在校期间班委职务
            </label>
            <div class="layui-input-inline">
                <input type="text"  id="schoolJob" name="schoolJob"  autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                <legend style="font-size:16px;">教育信息</legend>
            </fieldset>
        </div>

        <div class="layui-form-item">
            <label for="isGraduate" class="layui-form-label">
                <span class="x-red"></span>是否继续攻读硕士
            </label>
            <div class="layui-input-inline" style="width:190px;">
                <select  id="isGraduate" name="isGraduate"   lay-filter="isGraduate">
                    <option value="否">否</option>
                    <option value="是">是</option>
                </select>
            </div>


            <label for="graduateSchool" class="layui-form-label">
                <span class="x-red"></span>硕士毕业院校
            </label>
            <div class="layui-input-inline">
                <input type="text"  id="graduateSchool" name="graduateSchool"  autocomplete="off" class="layui-input isGraduateInput">
            </div>
        </div>



        <div class="layui-form-item">
            <label for="graduateDegree" class="layui-form-label">
                <span class="x-red"></span>硕士攻读专业
            </label>
            <div class="layui-input-inline" style="width:190px;">
               <input type="text"  id="graduateDegree" name="graduateDegree"  autocomplete="off" class="layui-input isGraduateInput">
            </div>


            <label for="masterDegree" class="layui-form-label">
                <span class="x-red"></span>硕士学位
            </label>
            <div class="layui-input-inline">
                <input type="text"  id="masterDegree" name="masterDegree"  autocomplete="off" class="layui-input isGraduateInput">
            </div>
        </div>


        <div class="layui-form-item">
            <label for="masterGuide" class="layui-form-label">
                <span class="x-red"></span>硕导
            </label>
            <div class="layui-input-inline" style="width:190px;">
                <input type="text"  id="masterGuide" name="masterGuide"  autocomplete="off" class="layui-input isGraduateInput">
            </div>

        </div>


        <div class="layui-form-item">
            <label for="isDoctor" class="layui-form-label">
                <span class="x-red"></span>是否继续攻读博士
            </label>
            <div class="layui-input-inline" style="width:190px;">
                <select  id="isDoctor" name="isDoctor"   lay-filter="isDoctor">
                    <option value="否">否</option>
                    <option value="是">是</option>
                </select>
            </div>


            <label for="doctorSchool" class="layui-form-label">
                <span class="x-red"></span>硕士毕业院校
            </label>
            <div class="layui-input-inline">
                <input type="text"  id="doctorSchool" name="doctorSchool"  autocomplete="off" class="layui-input isDoctorInput">
            </div>
        </div>
        <div class="layui-form-item">
            <label for="doctorDegree" class="layui-form-label">
                <span class="x-red"></span>博士攻读专业
            </label>
            <div class="layui-input-inline" style="width:190px;">
                <input type="text"  id="doctorDegree" name="doctorDegree"  autocomplete="off" class="layui-input isDoctorInput">
            </div>


            <label for="doctorMasterDegree" class="layui-form-label">
                <span class="x-red"></span>博士学位
            </label>
            <div class="layui-input-inline">
                <input type="text"  id="doctorMasterDegree" name="doctorMasterDegree"  autocomplete="off" class="layui-input isDoctorInput">
            </div>
        </div>

        <div class="layui-form-item">
            <label for="doctorGuide" class="layui-form-label">
                <span class="x-red"></span>博导
            </label>
            <div class="layui-input-inline" style="width:190px;">
                <input type="text"  id="doctorGuide" name="doctorGuide"  autocomplete="off" class="layui-input isDoctorInput">
            </div>
            <label for="idea" class="layui-form-label">
                <span class="x-red"></span>建议
            </label>
            <div class="layui-input-inline" style="width:190px;">
                <input type="text"  id="idea" name="idea"  autocomplete="off" class="layui-input">
            </div>

        </div>
        <div style="height: 60px"></div>
    </div>





    <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
    position: fixed;bottom: 1px;margin-left:-20px;">
      <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">

        <button  class="layui-btn layui-btn-normal" lay-filter="add" lay-submit>
          增加
        </button>
        <button  class="layui-btn layui-btn-primary" id="close">
          取消
        </button>
      </div>
    </div>
  </form>
</div>
<script>
layui.config({
    base: '${re.contextPath}/plugin/build/js/',
    version: '1.0.1'
})
  layui.use(['form','layer','laydate','city'], function(){
    var $ = layui.jquery;
    var form = layui.form
        ,layer = layui.layer
        ,city = layui.city
       ,laydate = layui.laydate;
     //渲染select
      form.render('select');
      city.init()

      //执行一个laydate实例
      laydate.render({
          elem: '#studyYear' //指定元素
          ,type:'year'
          ,zIndex: 99999999
          ,trigger: 'click' //触发方式
      });
      laydate.render({
          elem: '#birthday' //指定元素
          ,type:'month'
          ,zIndex: 99999999
          ,trigger: 'click' //触发方式
      });

    //自定义验证规则

    form.verify({
      useName: function(value){
        if(value.trim()==""){
          return "姓名不能为空";
        }
      },
        studyYear:function(value) {
        if (value.trim() == "") {
          return "入学年份不能为空";
        }
      }
    });

   $('#close').click(function(){
     var index = parent.layer.getFrameIndex(window.name);
     parent.layer.close(index);
   });
      //监听id 为isGraduate 的下拉框
      hideInputByClass("isGraduateInput",true);
      form.on('select(isGraduate)', function(data){
          if(data.value=='是'){
              hideInputByClass("isGraduateInput",false);
          }else if(data.value=='否'){
              hideInputByClass("isGraduateInput",true);
          }
      });

      //监听id 为isDoctor 的下拉框
      hideInputByClass("isDoctorInput",true);
      form.on('select(isDoctor)', function(data){
          if(data.value=='是'){
              hideInputByClass("isDoctorInput",false);
          }else if(data.value=='否'){
              hideInputByClass("isDoctorInput",true);
          }
      });

    //监听提交
    form.on('submit(add)', function(data){
        layerAjax('/hjPerson/addHjPerson', data.field, 'personList');
        return false;
    });
  });
</script>
</body>

</html>

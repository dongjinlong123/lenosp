layui.define(['jquery','layer'], function(exports) {
    var $ = layui.jquery,
        layer = layui.layer;

var html = '<div  id="selectCity">'+
    ' <form class="layui-form-pane">'+
    '   <div class="layui-form-item">'+
    '   <label for="province" class="layui-form-label">'+
    '   <span class="x-red"></span>省份'+
    '   </label>'+
    '   <div class="layui-input-inline" >'+
    '   <select  id="province" name="province" >'+
    '   <option>请选择省份</option>'+
    '   </select>'+
    '   </div>'+
    '   <label for="city" class="layui-form-label">'+
    '   <span class="x-red"></span>城市'+
    '   </label>'+
    '   <div class="layui-input-inline">'+
    '   <select  id="city" name="city" >'+
    '   <option>请选择城市</option>'+
    '   </select>'+
    '   </div>'+
    '   <label for="area" class="layui-form-label">'+
    '   <span class="x-red"></span>区域'+
    '   </label>'+
    '   <div class="layui-input-inline" >'+
    '   <select  id="area" name="area" >'+
    '   <option>请选择区域</option>'+
    '   </select>'+
    '   </div>'+
    '   </div>'+
    '   <div class="layui-form-item" style="margin-top: 30px;">'+
    '   <button  class="layui-btn layui-btn-normal layui-btn-radius layui-btn-fluid" style="width: 92%;" lay-filter="getCity" >'+
    '   确定'+
    '   </button>'+
    '   </div>'+
    '   </form>'+
    '   </div>';
    var city = {
        init:function(){
            layer.open({
                type: 1,
                title:"省市区选择",
                resize:false,
                area: ['600px', '250px'],
                content: html,
                zIndex:9
            });
        }
    }
    //输出test接口
    exports('city', city);
});
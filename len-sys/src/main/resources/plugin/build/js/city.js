layui.define(['jquery','layer','form'], function(exports) {
    var $ = layui.jquery,
        form = layui.form
        layer = layui.layer;

var html = '<div  id="selectCity">'+
    ' <form class="layui-form-pane">'+
    '   <div class="layui-form-item">'+
    '   <label for="djl_province" class="layui-form-label">'+
    '   <span class="x-red"></span>省份'+
    '   </label>'+
    '   <div class="layui-input-inline" >'+
    '   <select  id="djl_province" name="djl_province" >'+
    '   <option value="">请选择省份</option>'+
    '   </select>'+
    '   </div>'+
    '   <label for="djl_city" class="layui-form-label">'+
    '   <span class="x-red"></span>城市'+
    '   </label>'+
    '   <div class="layui-input-inline">'+
    '   <select  id="djl_city" name="djl_city" >'+
    '   <option value="">请选择城市</option>'+
    '   </select>'+
    '   </div>'+
    '   <label for="djl_area" class="layui-form-label">'+
    '   <span class="x-red"></span>区域'+
    '   </label>'+
    '   <div class="layui-input-inline" >'+
    '   <select  id="djl_area" name="djl_area" >'+
    '   <option value="">请选择区域</option>'+
    '   </select>'+
    '   </div>'+
    '   </div>'+
    '   <div class="layui-form-item" style="margin-top: 30px;">'+
    '   <button  class="layui-btn layui-btn-normal layui-btn-radius layui-btn-fluid" style="width: 92%;" lay-filter="djlGetCity" lay-submit>'+
    '   确定'+
    '   </button>'+
    '   </div>'+
    '   </form>'+
    '   </div>';

    form.on('submit(djlGetCity)', function(data){
        if ($("#djl_province").val().trim() == "") {
            layer.msg("省份不能为空！")
            return false;
        }
        if ($("#djl_city").val().trim() == "") {
            layer.msg("城市不能为空！")
            return false;
        }
        var areaV= "-"+$("#djl_area :selected").text();
        if($("#djl_area :selected").val() == ""){
            areaV = "";
        }
        var result = $("#djl_province :selected").text() +"-" +$("#djl_city :selected").text() +areaV

        $("#"+ret).val(result)
        //关闭弹窗
        layer.close(cityIndex);
        return false;
    });
    var ret;
    var cityIndex;
    var city = {
        init:function(className,arr){
            ret = className
            cityIndex= layer.open({
                type: 1,
                title:"省市区选择",
                resize:false,
                area: ['600px', '250px'],
                content: html,
                zIndex:9
            });

            //初始化数据
            $.ajax({
                url: "/SysArea/getProvince",
                type: "post",
                data: null,
                dataType: "json",
                traditional: true,
                success: function (data) {
                    var d = data.data;
                    for(var i=0;i<d.length;i++){
                        var option = new Option(d[i].areaName,d[i].areaId);
                        $("#djl_province")[0].options.add(option);
                    }
                   city.getCity();
                   city.getArea();
                   city.setDefaultValue(arr)//设置初始值
                }
            });
        },
        setDefaultValue:function(arr){
            if(!arr || arr[0] == undefined || arr[1] == undefined){
                return;
            }
            if(arr[2] == undefined){
                arr[2] = "";
            }
            //设置初始值
            var provinceOptions = $("#djl_province")[0].options;
            for(var po =0;po< provinceOptions.length; po++){
                if(provinceOptions[po].innerHTML == arr[0]){
                    provinceOptions[po].setAttribute("selected","selected")
                    $("#djl_province").change();
                    break;
                }
            }
            setTimeout(function () {
                var cityOptions = $("#djl_city")[0].options;
                for(var co =0;co< cityOptions.length; co++){
                    if(cityOptions[co].innerHTML == arr[1]){
                        cityOptions[co].setAttribute("selected","selected")
                        $("#djl_city").change();
                        break;
                    }
                }
            },300);
            setTimeout(function () {
                var areaOptions = $("#djl_area")[0].options;
                for(var ao = 0 ;ao< areaOptions.length; ao++){
                    if(areaOptions[ao].innerHTML == arr[2]){
                        areaOptions[ao].setAttribute("selected","selected")
                        break;
                    }
                }
            },400);
        },
        getArea:function () {
            $("#djl_city").change(function () {
                //根据省份加载城市
                var cityId = $("#djl_city").val();
                //清空area
                $("#djl_area")[0].options.length =0;
                $("#djl_area").append("<option value=''>请选择区域</option>")
                //初始化数据
                $.ajax({
                    url: "/SysArea/getArea",
                    type: "post",
                    data: {cityId:cityId},
                    dataType: "json",
                    traditional: true,
                    success: function (data) {
                        var d = data.data;
                        for(var i=0;i<d.length;i++){
                            var option = new Option(d[i].areaName,d[i].areaId)
                            $("#djl_area")[0].options.add(option)
                        }
                    }
                });
            });
        },
        getCity:function(){
            $("#djl_province").change(function () {
                //根据省份加载城市
                var provinceId = $("#djl_province").val();
                //清空city 和 area
                $("#djl_city")[0].options.length =0;
                $("#djl_area")[0].options.length =0;
                $("#djl_city").append("<option value=''>请选择城市</option>");
                $("#djl_area").append("<option value=''>请选择区域</option>")
                //初始化数据
                $.ajax({
                    url: "/SysArea/getCity",
                    type: "post",
                    data: {provinceId:provinceId},
                    dataType: "json",
                    traditional: true,
                    success: function (data) {
                        var d = data.data;
                        for(var i=0;i<d.length;i++){
                            var option = new Option(d[i].areaName,d[i].areaId)
                            $("#djl_city")[0].options.add(option)
                        }
                    }
                });
            });
        }
    }
    //输出test接口
    exports('city', city);
});
<!DOCTYPE html>
<html style="height: 100%">
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=no, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
</head>
<body style="height: 100%; margin: 0;overflow: hidden">
<div id="container" style="height: 100%"></div>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts/dist/echarts.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts-gl/dist/echarts-gl.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts-stat/dist/ecStat.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts/dist/extension/dataTool.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts/map/js/china.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts/map/js/world.js"></script>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=wc2IeVKV2iNi2fnBBPNnlhUs9MwKCd1S"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts/dist/extension/bmap.min.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    $.ajax({
        url: "/hjPerson/getHjPersonMap",
        type: "get",
        data: null,
        dataType: "json",
        traditional: true,
        success: function (ret) {
            var data = ret.data.data;
            var max = data[0].value;
            console.log(data)
            data = formatData(data)

            console.log(data)
            var geoCoordMap = ret.data.geoCoordMap;
            option = {
                title : {
                    text: '人员分布',
                    subtext: '交大IE人员',
                    left: 'center'
                },
                tooltip : {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data:['IE人员']
                },
                visualMap: {
                    min: 0,
                    max: max,
                    left: 'left',
                    top: 'bottom',
                    text:['高','低'],           // 文本，默认为数值文本
                    calculable : true
                },
                toolbox: {
                    show: true,
                    orient : 'vertical',
                    left: 'right',
                    top: 'center',
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                series : [
                    {
                        name: '就业人员数量',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data:data
                    }
                ]
            };;
            if (option && typeof option === "object") {
                myChart.setOption(option, true);
            }
        }
    });



    function formatData(data){
        //格式化数据
        var provinceList =[ {name: '北京',value: 0},
            {name: '天津',value: 0},
            {name: '上海',value: 0},
            {name: '重庆',value: 0},
            {name: '河北',value: 0},
            {name: '河南',value: 0},
            {name: '云南',value: 0},
            {name: '辽宁',value: 0},
            {name: '黑龙江',value: 0},
            {name: '湖南',value: 0},
            {name: '安徽',value: 0},
            {name: '山东',value: 0},
            {name: '新疆',value: 0},
            {name: '江苏',value: 0},
            {name: '浙江',value: 0},
            {name: '江西',value: 0},
            {name: '湖北',value: 0},
            {name: '广西',value: 0},
            {name: '甘肃',value: 0},
            {name: '山西',value: 0},
            {name: '内蒙古',value: 0},
            {name: '陕西',value: 0},
            {name: '吉林',value: 0},
            {name: '福建',value: 0},
            {name: '贵州',value: 0},
            {name: '广东',value: 0},
            {name: '青海',value: 0},
            {name: '西藏',value: 0},
            {name: '四川',value: 0},
            {name: '宁夏',value: 0},
            {name: '海南',value: 0},
            {name: '台湾',value: 0},
            {name: '香港',value: 0},
            {name: '澳门',value: 0},
            {name:'南海诸岛',value:0}];

        for(var i=0;i<data.length;i++){
            var name = data[i].name;
            var val = data[i].value;
            for(var j=0;j<provinceList.length;j++){
                var province = provinceList[j].name;
                if(name.indexOf(province) >= 0){
                    //包含
                    provinceList[j].value +=val;
                }
            }
        }
        return provinceList;
    }
</script>
</body>
</html>
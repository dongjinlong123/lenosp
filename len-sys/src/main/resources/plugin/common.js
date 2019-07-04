/**
 * 针对输入标签
 * id :元素id
 * flag true:禁止输入，false 允许输入
 */
function hideInputByClass(className,flag){
    var $id= $("."+className);
    if(flag){
        $id.val('');
        $id.attr('disabled','disabled').css('background','#e6e6e6');
    }
    else{
        $id.removeAttr('disabled').css('background','white')
    }
}

/**
 * 时间戳转换
 * @param inputTime
 * @returns {string}
 */
function formatDateTime(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
};
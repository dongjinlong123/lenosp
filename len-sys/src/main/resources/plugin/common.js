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
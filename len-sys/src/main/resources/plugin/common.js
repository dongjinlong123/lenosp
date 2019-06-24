/*弹出层*/
/*
 参数解释：
 title   标题
 url     请求的url
 id      需要操作的数据id
 w       弹出层宽度（缺省调默认值）
 h       弹出层高度（缺省调默认值）
 */
function openMyWindow(title, url, w, h,isMax) {
    if (title == null || title == '') {
        title = false;
    }
    if (url == null || url == '') {
        url = "error/404";
    }
    if (w == null || w == '') {
        w = ($(window).width() * 0.9);
    }
    if (h == null || h == '') {
        h = ($(window).height() - 50);
    }
    layer.open({
        id: 'job-add',
        type: 2,
        area: [w + 'px', h + 'px'],
        fix: false,
        maxmin: true,
        shadeClose: false,
        shade: 0.4,
        title: title,
        content: url,
        success: function(layero,index){
            //在回调方法中的第2个参数“index”表示的是当前弹窗的索引。
            //通过layer.full方法将窗口放大。
            if(isMax){
                layer.full(index);
            }
        }
    });


}
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
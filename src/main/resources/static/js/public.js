window.onload = function () {
    //非空span展示
    $(".notnull").text("*");
    $(".notnull").css({
        "font-size": "25px",
        "font-weight": "400",
        "color": "red",
        "cursor": "pointer"
    });
    $(".notnull").mouseover(function () {
        notnull = layer.tips('必填项', this, {
            tips: [2, '#e94f49'],
            time: 800
        });
    })

    //初始化layui-form显示
    layui.form.render();
    layui.element.render('nav');

    //主题设置
    $.ajax({
        url: '/prop/getTheme',
        type: 'get',
        success: function (data) {
            if (data == "true") {
                //深色模式
                $('body').css({
                    "-webkit-filter": "grayscale(100%)",
                    "-moz-filter": "grayscale(100%)",
                    "-ms-filter": "grayscale(100%)",
                    "-o-filter": "grayscale(100%)",
                    "filter": "grayscale(100%)",
                    "filter": "progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)",
                    "filter": "gray"
                })
            }
        }
    })

}

//非空弹窗提醒
function tip_notnull() {
    alert("必填项不能为空！", {
        icon: 0
    });
}

//自定义正则验证
function checkResult(str, zhz) {
    if (zhz.test(str))
        return true;
    else
        return false;
}

//js获取get传值
function geturl(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return "";
}

//获取随机数
function getRandom(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}
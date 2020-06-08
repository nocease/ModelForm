var nowtime = "";//日期星期时间
var riqi = "";//日期
var shijian = "";//时间

function setTime() {

    //创建Date对象
    let date = new Date();

    //获取4位数的年份
    let y = date.getFullYear();

    //获取月份,结果是0-11，+1
    let m = addzore(date.getMonth() + 1);

    //获取日期
    let d = addzore(date.getDate());

    //获取星期，结果0-6
    let week = date.getDay();

    //获取时
    let hh = addzore(date.getHours());

    //获取分钟
    let mm = addzore(date.getMinutes());

    //获取秒
    let ss = addzore(date.getSeconds());

    //拼接字符串
    nowtime = y + "/" + m + "/" + d + " 星期" + weekD(week) + " " + hh + ":" + mm + ":" + ss;
    riqi = y + "-" + m + "-" + d;
    shijian = hh + ":" + mm + ":" + ss;
}

setInterval("setTime()", 0, 500);

function weekD(a) {
    if (a == 1)
        return "一";
    else if (a == 2)
        return "二";
    else if (a == 3)
        return "三";
    else if (a == 4)
        return "四";
    else if (a == 5)
        return "五";
    else if (a == 6)
        return "六";
    else if (a == 0)
        return "日";
    else
        return a;
}

//数据前补0
function addzore(num) {
    return num > 9 ? num : "0" + num;
}

//根据日期获取星期
function getWeekDay(datestr) {
    var weekDay = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
    var myDate = new Date(Date.parse(datestr));
    return weekDay[myDate.getDay()];
}

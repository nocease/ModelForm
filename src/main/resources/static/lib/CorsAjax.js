//借助后端的httpclient实现跨域Ajax
//原生js封装Ajax

mycookie = ""; //定义全局追加的cookie

function CorsAjax(obj) {
    var method = obj.type || "get", //发送方式
        timeout = (typeof obj.timeout === "number" ? obj.timeout : false), //过期时间
        url = obj.url || "", //地址
        success = obj.success || function () {
        }, //成功函数
        error = obj.error || function () {
        }, //失败函数
        data = obj.data || {}, //要发送的数据
        headers = obj.headers || {}, //设置请求头
        sendjson = obj.sendjson || false, //是否发送json格式数据
        async = obj.async || true; //异步或同步

    //创建XMLHttpRequest对象
    var Xhr = new XMLHttpRequest();
    //用post向固定的后台发送
    Xhr.open("post", "../dohttp", async);

    //设置超时
    if (timeout != false) {
        setTimeout(function () {
            Xhr.abort();
        }, timeout);
    }

    //设置请求头
    Xhr.setRequestHeader("Content-Type", "application/json") //设置请求头
    Xhr.setRequestHeader("Content-Security-Policy", "upgrade-insecure-requests"); //允许https请求

    //设置cookie
    if (!headers.hasOwnProperty("cookie") && !headers.hasOwnProperty("Cookie")) {
        //没有cookie,追加上固定的cookie
        headers["cookie"] = mycookie;
    } else {
        //有cookie或Cookie
        if (headers.hasOwnProperty("cookie")) headers.cookie += mycookie;
        else headers.Cookie += mycookie;
    }

    //发送数据
    Xhr.send(JSON.stringify({
        url: url + '',
        type: method + '',
        headers: headers,
        params: data,
        sendjson: sendjson + ''
    }));
    //回调函数
    Xhr.onreadystatechange = function () {
        if (Xhr.readyState == 4 && Xhr.status == 200) {
            var result = JSON.parse(Xhr.response);
            //ajax发送成功
            if (result.code == 200) {
                success(result.content, result);
            } else {
                error(result);
            }
        }
    }
}

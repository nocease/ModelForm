/*
*依赖Ajax、layer
*确保引入位置之前存在layui.all.js
*
*/

//邮件推送
function sendMail(email, title, msg) {
    let timestamp = new Date().getTime();
    let data = {timestamp: timestamp, email: email, title: title, msg: msg};
    data.key = md5(data);
    $.ajax({
        url: '/tencent/sendMail',
        type: 'post',
        data: data,
        async: false,
        success: function (data) {
            //return data;
        },
        error: function (err) {
            alert("错误：" + JSON.stringify(err), {icon: 2});
        }
    })
}

//短信推送
function sendSms(phone, code) {
    let timestamp = new Date().getTime();
    let data = {timestamp: timestamp, phone: phone, code: code};
    data.key = md5(data);
    $.ajax({
        url: '/tencent/sendSms',
        type: 'post',
        data: data,
        async: false,
        success: function (data) {
            //return data;
        },
        error: function (err) {
            alert("错误：" + JSON.stringify(err), {icon: 2});
        }
    })
}

//图片文本识别（按行）
function generalOcr(url) {
    let timestamp = new Date().getTime();
    let data = {timestamp: timestamp, url: url};
    data.key = md5(data);
    $.ajax({
        url: '/tencent/generalOcr',
        type: 'post',
        data: data,
        async: false,
        success: function (data) {
            result = data;
        },
        error: function (err) {
            alert("错误：" + JSON.stringify(err), {icon: 2});
        }
    })
}

//AL描述图片
function visionImgtotext(url) {
    let timestamp = new Date().getTime();
    let data = {timestamp: timestamp, url: url};
    data.key = md5(data);
    $.ajax({
        url: '/tencent/visionImgtotext',
        type: 'post',
        data: data,
        async: false,
        success: function (data) {
            result = data;
        },
        error: function (err) {
            alert("错误：" + JSON.stringify(err), {icon: 2});
        }
    })
}

//身份证ocr
function idcardOcr(url, id) {
    let timestamp = new Date().getTime();
    let data = {timestamp: timestamp, url: url, id: id};
    data.key = md5(data);
    $.ajax({
        url: '/tencent/idcardOcr',
        type: 'post',
        data: data,
        async: false,
        success: function (data) {
            result = data;
        },
        error: function (err) {
            alert("错误：" + JSON.stringify(err), {icon: 2});
        }
    })
}

//文本翻译
function translate(text, source, target) {
    let timestamp = new Date().getTime();
    let data = {timestamp: timestamp, text: text, source: source, target: target};
    data.key = md5(data);
    $.ajax({
        url: '/tencent/nlpTextTranslate',
        type: 'post',
        data: data,
        async: false,
        success: function (data) {
            result = data;
        },
        error: function (err) {
            alert("错误：" + JSON.stringify(err), {icon: 2});
        }
    })
}


//--------------------------------------------------------------------------------

//根据sql获取对象
function doSQL_getObj(sql) {
    let timestamp = new Date().getTime();
    let data = {timestamp: timestamp, sql: sql};
    data.key = md5(data);
    $.ajax({
        url: '/doSQL/getObj',
        type: 'post',
        data: data,
        async: false,
        success: function (data) {
            result = data;
        },
        error: function (err) {
            alert("错误：" + JSON.stringify(err), {icon: 2});
        }
    })
}

//根据sql获取对象集合
function doSQL_getObjList(sql) {
    let timestamp = new Date().getTime();
    let data = {timestamp: timestamp, sql: sql};
    data.key = md5(data);
    $.ajax({
        url: '/doSQL/getObjList',
        type: 'post',
        data: data,
        async: false,
        success: function (data) {
            result = data;
        },
        error: function (err) {
            alert("错误：" + JSON.stringify(err), {icon: 2});
        }
    })
}

//执行sql(不推荐)
function doSQL_carry(sql) {
    let timestamp = new Date().getTime();
    let data = {timestamp: timestamp, sql: sql};
    data.key = md5(data);
    $.ajax({
        url: '/doSQL/insertORupdate',
        type: 'post',
        data: data,
        async: false,
        success: function (data) {
            //return data;
        },
        error: function (err) {
            alert("错误：" + JSON.stringify(err), {icon: 2});
        }
    })
}

//-----------------------------------------------------------

//获取当前session对象
function getSession() {
    let timestamp = new Date().getTime();
    let data = {timestamp: timestamp};
    data.key = md5(data);
    $.ajax({
        url: '/doSQL/getSession',
        type: 'post',
        async: false,
        data: data,
        success: function (data) {
            //return data;
            result = data;
        },
        error: function (err) {
            alert("错误：" + JSON.stringify(err), {icon: 2});
        }
    })
}

//清空当前session对象
function delSession() {
    let timestamp = new Date().getTime();
    let data = {timestamp: timestamp};
    data.key = md5(data);
    $.ajax({
        url: '/doSQL/delSession',
        type: 'post',
        async: false,
        data: data,
        success: function (data) {
            //return data;
        },
        error: function (err) {
            alert("错误：" + JSON.stringify(err), {icon: 2});
        }
    })
}

//向session对象中添加或修改value
function setSession(key, value) {
    let timestamp = new Date().getTime();
    let data = {
        timestamp: timestamp,
        key1: key,
        value: value
    };
    data.key = md5(data);
    $.ajax({
        url: '/doSQL/setSession',
        type: 'post',
        data: data,
        async: false,
        success: function (data) {
            // return data;
        },
        error: function (err) {
            alert("错误：" + JSON.stringify(err), {icon: 2});
        }
    })
}

//=============================================
//加密算法
document.write('<script src="../lib/md5.js" type="text/javascript" charset="utf-8"></script>')

function md5(data) {
    let time = data.timestamp + "";
    let md5 = hex_md5(encodeURIComponent(time));
    return hex_md5(encodeURIComponent(md5 + "848817043"));
}
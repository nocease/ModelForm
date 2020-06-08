axios.get("/baidu/getJSbypage?pageid=" + geturl("pageid")).then(res => {
    eval(res.data);
});
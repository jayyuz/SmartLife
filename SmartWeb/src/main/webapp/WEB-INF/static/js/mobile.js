/**
 * Created by Administrator on 2016/10/1.
 */

function loginCheck() {
    var form = document.getElementById("myform");
    if (form.UserMobileNo.value == '') {
        $.alert('请输入手机号!', '温馨提示');
        form.UserMobileNo.focus();
        return false;
    }
    if (form.Password.value == '') {
        $.alert("请输入登录密码!");
        form.Password.focus();
        return false;
    }
    if (form.code.value == '') {
        $.alert("请输入验证码!");
        form.code.focus();
        return false;
    }
    form.submit();
}

function registLoginErr(errCode) {
    var form = document.getElementById("myform");
    switch (errCode) {
        case 0:
            $.alert('请输入用户信息!', '温馨提示');
            form.UserMobileNo.focus();
            break;
        case 1:
            $.alert('请输入手机号!', '温馨提示');
            form.UserMobileNo.focus();
            break;
        case 2:
            $.alert('请输入密码!', '温馨提示');
            form.Password.focus();
            break;
        case 3:
            $.alert('请输入验证码!', '温馨提示');
            form.code.focus();
            break;
        case 4:
            $.alert('验证码不正确，请重新输入!', '温馨提示');
            form.code.focus();
            break;
        case 5:
            $.alert('用户名或密码不正确，请确认后输入!', '温馨提示');
            form.code.focus();
            break;
    }
}

function registCheck() {
    var form = document.getElementById("myform");
    if (form.UserMobileNo.value == '') {
        $.alert('请输入手机号!', '温馨提示');
        form.UserMobileNo.focus();
        return false;
    }
    if (form.Password.value == '') {
        $.alert("请输入登录密码!");
        form.Password.focus();
        return false;
    }
    if (form.code.value == '') {
        $.alert("请输入验证码!");
        form.code.focus();
        return false;
    }
    form.submit();
}

// 刷新图片
function changeImg() {
    var imgSrc = $("#imgObj");
    var src = imgSrc.attr("src");
    imgSrc.attr("src", changeUrl(src));
}

//为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
function changeUrl(url) {
    var timestamp = (new Date()).valueOf();
    var index = url.indexOf("?",url);
    if (index > 0) {
        url = url.substring(0, url.indexOf(url, "?"));
    }
    if ((url.indexOf("&") >= 0)) {
        url = url + "×tamp=" + timestamp;
    } else {
        url = url + "?timestamp=" + timestamp;
    }
    return url;
}

function goToRegist() {
    window.location.assign('/mregist');
}

function addDevicesCheck() {
    var form = document.getElementById("myform");
    if (form.DevId.value == '') {
        $.alert('请输入设备编号', '温馨提示');
        form.DevId.focus();
        return false;
    }
    if (form.sn.value == '') {
        $.alert('请输入设备SN', '温馨提示');
        form.sn.focus();
        return false;
    }
    form.submit();
}

function addDeviceErr(errCode) {
    var form = document.getElementById("myform");
    switch (errCode) {
        case 0:
            $.alert('请输入设备信息!', '温馨提示');
            form.DevId.focus();
            break;
        case 1:
            $.alert('请输入设备编号!', '温馨提示');
            form.DevId.focus();
            break;
        case 2:
            $.alert('请输入设备SN!', '温馨提示');
            form.SN.focus();
            break;
        case 3:
            $.alert('设备已经被添加，如设备不是您本人和信任的人添加，请联系客服!', '温馨提示');
            form.DevId.focus();
            break;
        case 4:
            $.alert('设备编号和设备SN不匹配，请仔细确认后再添加!', '温馨提示');
            form.DevId.focus();
            break;
    }
}

/**检查添加设备返回的值*/
function checkCode(code) {
    if(code ==1){
        $.alert('请输入设备SN!', '温馨提示',function () {
            window.history.back(-1);
        });
    }
}


function setCheck(id, type, json) {
    var input = document.getElementById(id);
    var obj = eval(json);
    switch (type) {
        case "Switch":
            input.checked = obj.state;
            break;

        case "Light":
            input.checked = obj.state;
            break;
        default:
            break;
    }
}

function changeDeviceState(devId) {
    var xmlhttp;
    var input = document.getElementById(devId);
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            //刷新界面
            window.location.reload();
        }
    }
    xmlhttp.open("GET", "/mchangestate/" + devId + "/" + input.checked, true);
    xmlhttp.send();
}

function onDevicesClick(id) {
    $.toast(id + "被点击");
}


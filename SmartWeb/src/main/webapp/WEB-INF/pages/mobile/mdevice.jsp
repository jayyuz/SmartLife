<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/30
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>我的设备</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">

</head>
<body>
<div class="page">
    <header class="bar bar-nav">
        <%--<a class="button button-link button-nav pull-left" href="/mindex" data-transition='slide-out'>
            <span class="icon icon-left"></span>
            返回
        </a>--%>
        <a class="button button-link button-nav pull-left back">
            <span class="icon icon-left"></span>
            返回
        </a>
        <h1 class="title">设备：${deviceEntity.devId}</h1>
    </header>
    <div class="content">
        <div class="list-block">
            <ul>
                <li class="item-content">
                    <div class="item-media"><i class="icon icon-f7"></i></div>
                    <div class="item-inner">
                        <div class="item-title">设备拥有者</div>
                        <div class="item-after">${deviceEntity.userByUserMobileNo.userMobileNo}</div>
                    </div>
                </li>
                <li class="item-content">
                    <div class="item-media"><i class="icon icon-f7"></i></div>
                    <div class="item-inner">
                        <div class="item-title">注册时间</div>
                        <div class="item-after">${deviceEntity.userByUserMobileNo.registTime}</div>
                    </div>
                </li>
                <li class="item-content">
                    <div class="item-media"><i class="icon icon-f7"></i></div>
                    <div class="item-inner">
                        <div class="item-title">型号</div>
                        <div class="item-after">${deviceEntity.devType}</div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>

</body>
</html>

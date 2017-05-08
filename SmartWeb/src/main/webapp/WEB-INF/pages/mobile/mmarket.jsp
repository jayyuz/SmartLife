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
    <title>智能平台</title>
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
        <h1 class="title">设备市场</h1>
    </header>

    <div class="content">
        <h1 >暂未开放，尽请期待！</h1>

    </div>

    <nav class="bar bar-tab">

        <a class="tab-item" href="/mindex" external>
            <span class="icon icon-me"></span>
            <span class="tab-label">我的</span>
        </a>
        <a class="tab-item active" href="#" external>
            <span class="icon icon-cart"></span>
            <span class="tab-label">市场</span>
        </a>
        <a class="tab-item" href="/msetting" external>
            <span class="icon icon-settings"></span>
            <span class="tab-label">设置</span>
        </a>
    </nav>
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>

<script type="text/javascript">
    $.toast("暂未开放，尽请期待！");
</script>
</body>
</html>

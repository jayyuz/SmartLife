<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="../../static/ccs/mobile.css">
</head>
<body>
<div class="page">
    <header class="bar bar-nav">
        <a class="button button-link button-nav pull-left back">
            <span class="icon icon-left"></span>
            返回
        </a>
        <h1 class="title">添加新设备</h1>
    </header>

    <div class="content">
        <form:form action="/madddevicep" method="post" commandName="device" role="form" name="myform" id="myform">
            <div class="list-block">
                <ul>
                    <!-- Text inputs -->
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-name"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">设备Id</div>
                                <div class="item-input">
                                    <input type="text" name="DevId" placeholder="请输入设备Id">
                                </div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-password"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">设备SN</div>
                                <div class="item-input">
                                    <input type="text" name="sn" placeholder="请输入设备SN">
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <div class="row">
                    <div class="col-50"><a class="button button-big button-fill button-danger back">取消</a></div>
                    <div class="col-50">
                        <a href="javascript:void(0)" onclick="addDevicesCheck()"
                           class="button button-big button-fill">添加</a>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>


<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='../../static/js/mobile.js' charset='utf-8'></script>

<c:if test = "${!empty code}">
    <script type="text/javascript">
        checkCode(${code});
    </script>
</c:if>

<c:if test="${!empty errCode}">
    <script type="text/javascript">
        addDeviceErr(${errCode});
    </script>
</c:if>

</body>
</html>

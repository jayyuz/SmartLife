<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>智能平台登录</title>
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
        <h1 class="title">登录</h1>
    </header>
    <div class="content">
        <form:form action="/mloginp" method="post" commandName="user" role="form" name="myform" id="myform">
            <div class="list-block">
                <ul>
                    <!-- Text inputs -->
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-name"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">手机号</div>
                                <div class="item-input">
                                    <input type="text" name="UserMobileNo" placeholder="请输入手机号" value="${userMobile}">
                                </div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-password"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">密码</div>
                                <div class="item-input">
                                    <input type="password" name="Password" placeholder="请输入密码">
                                </div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-password"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">验证码</div>
                                <div class="item-input">
                                    <table>
                                        <tr>
                                            <td><input type="text" name="code" placeholder="请输入验证码"></td>
                                            <td><a href="#"
                                                   onclick="changeImg()"><img id="imgObj" alt="验证码" src="/code"/></a>
                                            </td>
                                        </tr>
                                    </table>

                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <div class="row">
                    <div class="col-50"><a href="#" class="button button-big button-fill button-danger">取消</a></div>
                    <div class="col-50">

                        <a href="javascript:void(0)" onclick="loginCheck()"
                           class="button button-big button-fill button-success">提交</a>
                    </div>
                </div>
            </div>
        </form:form>

        <div class="content-block">
            <a href="javascript:void(0)" onclick="goToRegist()"><p class="regist-link">注册</p></a>
        </div>
    </div>
</div>


<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='../../static/js/mobile.js' charset='utf-8'></script>

<c:if test="${!empty errCode}">
    <script type="text/javascript">
        registLoginErr(${errCode});
    </script>
</c:if>


</body>
</html>

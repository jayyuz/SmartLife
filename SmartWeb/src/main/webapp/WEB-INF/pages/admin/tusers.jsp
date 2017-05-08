<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <%--  <title>SpringMVC 用户管理</title>--%>
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <%--SUI--%>
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
    <script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/??sm.min.css,sm-extend.min.css">
    <script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/??sm.min.js,sm-extend.min.js'
            charset='utf-8'></script>


</head>
<body>
<div class="page-group">
    <div class="page page-current">
        <header class="bar bar-nav">
            <h1 class="title">用户列表${FUserName}</h1>
        </header>
        <div class="content">

            <div class="content-block">
                <p><a href="#" class="button button-fill open-panel" data-panel='#panel-left-demo'>打开左侧栏</a></p>
            </div>

            <!-- 如果用户列表非空 -->
            <c:if test="${!empty userList}">
                <c:forEach items="${userList}" var="user">
                    <div class="content-block-title">用户:${user.nickname}</div>
                    <div class="list-block">
                        <ul>
                            <li class="item-content">
                                <div class="item-media"><i class="icon icon-f7"></i></div>
                                <div class="item-inner">
                                    <div class="item-title">用户名：</div>
                                    <div class="item-after">${user.firstName}</div>
                                </div>
                            </li>
                            <li class="item-content">
                                <div class="item-media"><i class="icon icon-f7"></i></div>
                                <div class="item-inner">
                                    <div class="item-title">用户姓:</div>
                                    <div class="item-after">${user.lastName}</div>
                                </div>
                            </li>
                            <li class="item-content">
                                <div class="item-media"><i class="icon icon-f7"></i></div>
                                <div class="item-inner">
                                    <div class="item-title">用户密码:</div>
                                    <div class="item-after">${user.password}</div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <%--<tr>
                        <td>${user.id}</td>
                        <td>${user.nickname}</td>
                        <td>${user.firstName} ${user.lastName}</td>
                        <td>${user.password}</td>
                        <td>
                            <a href="/admin/users/show/${user.id}" type="button" class="btn btn-sm btn-success">详情</a>
                            <a href="/admin/users/update/${user.id}" type="button" class="btn btn-sm btn-warning">修改</a>
                            <a href="/admin/users/delete/${user.id}" type="button" class="btn btn-sm btn-danger">删除</a>
                        </td>
                    </tr>--%>
                </c:forEach>
            </c:if>
        </div>
    </div>

    <div class="panel-overlay"></div>
    <!-- Left Panel with Reveal effect -->
    <div class="panel panel-left panel-reveal theme-dark" id='panel-left-demo'>
        <div class="content-block">
            <p>这是一个侧栏</p>
            <p>你可以在这里放用户设置页面</p>
            <p><a href="#" class="close-panel">关闭</a></p>
        </div>
        <div class="list-block">
            <!-- .... -->
        </div>
    </div>
    <div class="panel panel-right panel-reveal">
        <div class="content-block">
            <p>这是一个侧栏</p>
            <!-- Click on link with "close-panel" class will close panel -->
            <p><a href="#" class="close-panel">关闭</a></p>
        </div>
    </div>

</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>

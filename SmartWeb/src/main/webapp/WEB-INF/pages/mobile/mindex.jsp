<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="zh-CN">
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

    <script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
    <script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
    <script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
    <script type='text/javascript' src='../../static/js/mobile.js' charset='utf-8'></script>
    <script type='text/javascript' src='../../static/js/websocket.js' charset='utf-8'></script>
</head>
<body>
<div class="page-group">
    <div class="page page-current">
        <header class="bar bar-nav">
            <c:if test="${empty userEntity}">
                <h1 class="title">用户未登录</h1>
            </c:if>
            <c:if test="${!empty userMobile}">
                <h1 class="title">我的设备</h1>
                <a class="button button-link button-nav pull-right" href="/mloginout" data-transition='slide-out'>
                    登出<span class="icon icon-right"></span>
                </a>
            </c:if>
        </header>

        <div class="content" style="padding-bottom: 1rem">
            <div class="list-block cards-list">

                <c:if test="${empty userEntity}">
                    <h2 class="title">用户未登录，请<a href="/mlogin" style="color: red">登录</a>后再操作</h2>
                </c:if>
                <c:if test="${!empty userEntity}">
                    <%-- 建立链接--%>
                    <script type="text/javascript">
                        updateUrl('/mwebsocket');
                        connect();
                    </script>

                    <c:if test="${!empty devices}">
                        <c:forEach items="${devices}" var="device">

                            <div class="card" id="dev">
                                <a href="/mdevice/${device.devId}">
                                    <div class="card-header">设备：${device.devId}

                                        <c:if test="${device.state==true}">
                                            在线
                                        </c:if>

                                        <c:if test="${device.state==false}">
                                            离线
                                        </c:if>
                                    </div>
                                </a>
                                <div class="card-content">
                                    <div class="card-content-inner">
                                        <div class="list-block">
                                            <ul>
                                                <li class="item-content">
                                                    设备类型：${device.devType}
                                                </li>
                                                    <%--<li class="item-content">
                                                        拥有者：${device.users}
                                                    </li>--%>
                                                <li>
                                                    <div class="item-content">
                                                        <div class="item-inner">
                                                            <div class="item-title label">设备状态</div>
                                                            <div class="item-input">
                                                                <label class="label-switch">
                                                                    <input type="checkbox"
                                                                           id=${device.devId}  onchange="changeDeviceState(${device.devId})">
                                                                    <div class="checkbox"></div>
                                                                </label>
                                                            </div>
                                                            <script>
                                                                setCheck(${device.devId}, "${device.devType}", ${device.content});
                                                            </script>
                                                        </div>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>

                    <%--<div class="content-block">
                        <div class="row" >
                            <div class="col-100" ><a href="/madddevice"
                                                   class="button button-big button-fill button-add-device">添加新设备</a>
                            </div>
                        </div>
                    </div>--%>

                </c:if> <%--用户登录--%>
            </div>
        </div>


        <div class="floating-button-container">
            <button class="kc_fab_main_btn" onclick="gotoAddDevice()">+</button>
        </div>

        <c:if test="${!empty userEntity}">
            <nav class="bar bar-tab">
                <a class="tab-item active" href="#" external>
                    <span class="icon icon-me"></span>
                    <span class="tab-label">我的</span>
                </a>
                <a class="tab-item" href="/mmarket" external>
                    <span class="icon icon-cart"></span>
                    <span class="tab-label">市场</span>
                </a>
                <a class="tab-item" href="/msetting" external>
                    <span class="icon icon-settings"></span>
                    <span class="tab-label">设置</span>
                </a>
            </nav>
        </c:if>
    </div>
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='../../static/js/mobile.js' charset='utf-8'></script>

<script type="text/javascript">
    function gotoAddDevice() {
        self.location = '/madddevice';
    }
</script>

</body>
</html>

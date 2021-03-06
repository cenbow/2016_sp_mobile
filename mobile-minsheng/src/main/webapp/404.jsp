<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/tlds/activityName" prefix="spantag"%> 
<%String path = request.getContextPath();%>
<!doctype html>
<html lang="zh-CN">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">

<!-- 开启对web app程序的支持 -->
<meta name="apple-mobile-web-app-capable" content="yes">
<!-- 全屏模式浏览 -->
<meta name="apple-touch-fullscreen" content="yes">
<!-- 改变Safari状态栏的外观 -->
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<title>尚品网触屏版_领先的高端时尚和奢侈品购物网站</title>
<link href="<%=path%>/css/base.css" rel="stylesheet" />
<link href="<%=path%>/css/page/order.css" rel="stylesheet" />

<!-- icon -->
<link rel="apple-touch-icon" href="<%=path%>/images/touch-icon-iphone.png">
<link rel="apple-touch-icon" sizes="72x72" href="<%=path%>/images/touch-icon-ipad.png">
<link rel="apple-touch-icon" sizes="114x114" href="<%=path%>/images/touch-icon-iphone4.png">
<link rel="apple-touch-icon" sizes="144x144" href="<%=path%>/images/touch-icon-newipad.png">
<!-- 启动自定义图片(png, 320X640) -->
<link rel="apple-touch-startup-image" sizes="72x72" href="<%=path%>/images/logo/loading.png">

</head>

<body>

<header>
  <!-- 禁止自动识别5位以上数字为电话 -->
  <meta name="format-detection" content="telephone=no">
  <h1 id="alLogo">
    <a href="<%=path%>/merchandiseaction!splist?ch=${ch}&topicid=${topicid}"/>">尚品</a>
  </h1>
  <nav class="alUser_icon">
   <ul>
    <li><a href="<%=path%>/cartaction!showcart?ch=${ch}"><img src="<%=path%>/images/cart_icon.png" width="31" height="31" alt="购物袋"></a></li>
    <li><a href="<%=path%>/orderaction!orderlist?ch=${ch}"><img src="<%=path%>/images/order_icon.png" width="31" height="31" alt="订单"></a></li>
    <li><a href="<%=path%>/accountaction!info?ch=${ch}"><img src="<%=path%>/images/user_icon.png" width="31" height="31" alt="账户"></a></li>
   </ul>
  </nav>
</header>

<nav class="alNav">
  <ul>
    <li><a href="<%=path%>/merchandiseaction!splist?ch=${ch}&topicid=${topicid}"><spantag:activityName></spantag:activityName></a></li>
    <li>系统提示</li>
  </ul>
</nav>

<div class="alContent">
    <div class="alOrder_none" >
      <h2>抱歉！您查看的页面已不存在</h2>
      <p style="width:130;height:130;display:block;visibility:hidden;"></p>
      <a href="<%=path%>/merchandiseaction!splist?ch=${ch}&topicid=${topicid}" class="alOrder_buyBtn">活动</a>
    </div>
</div>

<jsp:include page="/WEB-INF/pages/common/footer_error.jsp"></jsp:include>

</body>
</html>
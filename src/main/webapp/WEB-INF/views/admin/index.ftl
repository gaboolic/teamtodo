<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#assign contextPath = request.contextPath/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/common.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/module_icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/md-menu.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/admin.css">
<script type="text/javascript"src="${contextPath}/resources/js/lib/jquery.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/menu.js"></script>
<title>后台管理</title>
</head>
<body class="index">
<div class="head-part">
	<div class="title">
		<span class="ic ic-home"></span>
		<h2>
			<strong>主页</strong>欢迎登录网站后台管理系统
		</h2>
	</div>
	<ul class="first-box">
		<li class="first">
			<span class="ic ic-add"></span>
			<ul class="sec-box">
				<li class="sec"><a href="position/add" target="contents">添加岗位</a></li>
				<li class="sec"><a href="activity/add" target="contents">添加活动</a></li>
			</ul>
		</li>
		<li class="label">
			<h2>Admin</h2>
			<p>
				<#if admin.status = 0>
            		超级管理员
        		<#else>
            		普通管理员
        		</#if>
        	</p>
		</li>
		<li class="first">
			<span class="ic ic-arrow-down-w"></span>
			<ul class="sec-box">
				<li class="sec"><a href="${contextPath}/admin/quit">退出登入</a></li>
			</ul>
		</li>
	</ul>
</div>
<div class="md-menu">
	<ul class="menu">
		<li><a class="ic ic-user" href="center" target="contents">个人中心</a></li>
		<li><a class="ic ic-job" href="position/addType" target="contents">岗位管理</a></li>
		<li><a class="ic ic-active" href="activity/add" target="contents">活动管理</a></li>
		<li><a class="ic ic-unit" href="company" target="contents">单位管理</a></li>
		<li><a class="ic ic-stuff" href="user" target="contents">人才管理</a></li>
		<li><a class="ic ic-stars" href="rank/add" target="contents">等级管理</a></li>
		<li><a class="ico ic-ad" href="ad/add" target="contents">首页广告</a></li>
		<li><a class="ico ic-sw" href="switch" target="contents">权限开关</a></li>
	</ul>
</div>


<iframe name="contents" frameborder="0" marginheight="0" src="center"></iframe>


</body>
</html>









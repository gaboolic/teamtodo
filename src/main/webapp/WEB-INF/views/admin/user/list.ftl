<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#assign contextPath = request.contextPath/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/common.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/module_icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/ser_dialog.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/admin.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/jPages.css"> 
<script type="text/javascript" src="${contextPath}/resources/js/lib/jquery.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/common.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/jPages.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/service/dialog.js"></script>
<title>后台管理员登录页面</title>
</head>
<body　scroll="no" style="overflow-x:hidden">
	<div class="container">
		<section class="selector">
			<ul>
				<li class="selected"><a href="${contextPath}/admin/user">人才管理</a></li>
				<li ><a href="${contextPath}/admin/user/allUpdated">星星等级升级提醒</a></li>
			</ul>
		</section>
		<div class="unit-stuff" id="unitmanage">
			<section class="search-part">
				<form method="post" action="${contextPath}/admin/user/query">
					<input type="text" class="search" id="search" name="nickname" placeholder="按照人才昵称搜索"/>
					<button id="btn-search"  class="btn-search ic ic-search"></button>
				</form>
				</section>
                <div class="holder page"></div>
				<section class="retable">
					<ul class="reth">
						<li><!--<input type="checkbox">-->&nbsp;&nbsp;&nbsp;序列</li>
						<li>昵称</li>
						<li>状态</li>
						<li>还剩解冻天数</li>
						<li>操作</li>
					</ul>
					<div id="itemContainer">
					<#list userList as user>
					<ul>
						<li><!--<input type="checkbox" id="${user_index + 1}">-->&nbsp;&nbsp;&nbsp;${user_index + 1}</li>
						<li>
							<#if user.nickname = null>
							    <a href="${contextPath}/admin/user/detail?id=${user.id}"></a>
							<#else>
								<a href="${contextPath}/admin/user/detail?id=${user.id}">${user.nickname}</a>
							</#if>
						</li>
						<li>
							<#if user.isIce = 1>
               					<#if user.iceType = 6>
                   					 永久冻结
                				<#else>
                    				冻结${user.attribute["iceDays"]}天
                				</#if>
            				<#else>
      							正常
            				</#if>
						</li>
						<li>
							<#if user.isIce = 1>
            					<#if user.iceType = 6 >
              	  					永久
            					<#else>
            						${user.attribute["remainDays"]}
            					</#if>
        					<#else>
        						0
        					</#if>
						</li>
						<li>
						<#if admin.status = 0>
							<#if user.isIce = 1>   
									<input type="hidden" class="userId" value="${user.id}">       					
                					<a href="#" class="set-star">设置星星等级</a>|<a href="${contextPath}/admin/user/thaw?id=${user.id}" class="no-cold">取消冻结</a>         				
            				<#else>
            					<input type="hidden" class="userId" value="${user.id}">
								<a href="#" class="set-star">设置星星等级</a>|<a class="cold stuff-cold" href="#">冻结</a>
            				</#if>	
            			</#if>
						</li>
					</ul>
					</#list>
					
			</section>
		</div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("li a.cold").click(function(){
  		 var ID=$(this).parent().find("input").val();
  	$(this).dialog({
  	    height: 350,
		title: "冻结弹出框",
		contentUrl: "${contextPath}/admin/user/ice?id="+ID
	}); 
	
  	});
  	
  	$("li a.set-star").click(function(){
  		 var ID=$(this).parent().find("input").val();
  	$(this).dialog({
  	    height: 350,
		title: "星星等级设置",
		contentUrl: "${contextPath}/admin/user/setStar?id="+ID
	}); 
	
  	});
  	
  	
  	$("div.holder").jPages({
		containerID: "itemContainer",
		previous: "上一页",
		next: "下一页",
		perPage: 10,
	}); 

});
</script>
</html>







<#--
技工管理页</br>
<html>
<head>
    <title></title>
</head>
<body>
<form action="query" method="post">
    <table>
        <tr>
            <td>员工昵称：</td>
            <td><input type="text" name="nickname"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="search"/></td>
        </tr>
    </table>
</form>
</body>
</html>

<#assign contextPath = request.contextPath/>
<table width="500" border="0">
    <tr>
        <td> 序号</td>
        <td>昵称</td>
        <td>头像</td>
        <td>状态</td>
        <td>还剩解冻天数</td>
        <td>操作</td>
    </tr>
<#list userList as user>
    <tr>
        <td> ${user_index + 1}</td>
        <td><a href="detail?id=${user.id}">${user.nickname}</a></td>
        <td><img alt="" src="${contextPath}/${user.headImage}"/></td>
        <td>
            <#if user.isIce = 1>
               <#if user.iceType = 6>
                   		 永久冻结
                <#else>
                    	冻结${user.attribute["iceDays"]}天
                </#if>
            <#else>
      				正常
            </#if>
        </td>
        <td>
        <#if user.isIce = 1>
            <#if user.iceType = 6 >
              	  永久
            <#else>
            ${user.attribute["remainDays"]}
            </#if>
        <#else>
        	0
        </#if>
        </td>
        <td>
            <#if user.isIce = 1>
            	<#if admin.status = 0>
                	<a href="thaw?id=${user.id}">取消冻结</a>
                </#if>
            <#else>
				<a href="ice?id=${user.id}">冻结</a>
            </#if>
        </td>
    </tr>
</#list>
</table>
-->

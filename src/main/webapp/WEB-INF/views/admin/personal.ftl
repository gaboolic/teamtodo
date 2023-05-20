<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#assign contextPath = request.contextPath/>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/common.css">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/module_icon.css">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/ser_dialog.css">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/admin.css">
  <script type="text/javascript" src="${contextPath}/resources/js/lib/jquery.js"></script>
  <script type="text/javascript" src="${contextPath}/resources/js/common.js"></script>
  <script type="text/javascript" src="${contextPath}/resources/js/pwdStrong.js"></script>
  <script type="text/javascript" src="${contextPath}/resources/js/service/dialog.js"></script>
  <script type="application/javascript">
    function modifyPassword() {
      var form = $("#modifyPasswordForm");
      $.post("${contextPath}/admin/center/modifyPassword",
          form.serialize(),
          function (data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
              alert("成功!")
            } else {
              alert("失败!")
            }
          });
    }
  </script>
  
  <script type="text/javascript">
  $(document).ready(function(){
  $(".selector ul li").click(function(){
    $(".selector ul li").removeClass("selected");
    $(this).addClass("selected");
    var selectVal=$(this).attr("name");
    $(".container div").each(function(){
      if($(this).attr("id")===selectVal){
        $(".container div").hide();
        $(this).show();
      }
    });
  });
  $(".container section ul li").eq(0).click();
  });
</script>
  <title>后台管理员登录页面</title>
</head>
<body>
<div class="container">
  <section class="selector">
    <ul>
      <li name="baseinfo">基本信息</li>
      <li name="editpas">修改密码</li>
    </ul>
  </section>
  <div class="basinfo" id="baseinfo">
    <form>
      <label for="user">用户名称：</label>
      <input type="text" id="user" value="${admin.username}" readonly="readonly">
      <label for="kind">所属类型：</label>
    <#if admin.status = 0>
      <input type="text" id="kind" value="超级管理员" readonly="readonly">
    <#else>
      <input type="text" id="kind" value="普通管理员" readonly="readonly">
    </#if>
      <label for="last-date">上次登录时间：</label>
      <input type="text" id="last-date" value="${admin.lastTime?string('yyyy-MM-dd HH:mm:ss')}" readonly="readonly">
      <label for="last-ip">上次登录IP：</label>
      <input type="text" id="last-ip" value="${admin.lastIp}" readonly="readonly">
    </form>
  </div>
  <div class="edit" id="editpas">
    <form id="modifyPasswordForm" method="post" action="center/modifyPassword">
      <label for="old">旧密码:</label>
      <input type="password" id="oldPassword" name="oldPassword" placeholder="请输入旧密码">
      <label for="new">新密码:</label>
      <input type="password" id="newPassword" name="newPassword" placeholder="请输入6-13位新密码" onChange="checkPWD()">
      <label for="again">重复新密码:</label>
      <input type="password" id="confPassword" name="confPassword" placeholder="请重新确认新密码">
      <label class="lb-comment">强度评估:</label>
      <section class="comment">
        <ul>
          <li id="1"></li>
          <li id="2"></li>
          <li id="3"></li>
        </ul>
      </section>

    </form>
    <button class="btn-update" type="button" onclick="modifyPassword()">更新新密码</button>
  </div>
</div>

</body>

</html>
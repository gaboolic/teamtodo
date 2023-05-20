修改密码
<html>
<head>
    <title></title>
</head>
<#assign contextPath = request.contextPath/>
<body>
<form action="${contextPath}/admin/modifyPassword" method="post">
    <table>
        <tr>
            <td>当前密码：</td>
            <td><input type="text" name="oldPassword"/></td>
        </tr>
        <tr>
            <td>修改密码：</td>
            <td><input type="text" name="newPassword"/></td>
        </tr>
        <tr>
            <td>确认密码：</td>
            <td><input type="text" name="confPassword"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="modify"/></td>
        </tr>
    </table>
</form>
${msg}
</body>
</html>

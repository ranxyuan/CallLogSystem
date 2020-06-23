<%--
  Created by IntelliJ IDEA.
  User: ran
  Date: 2019/7/19
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>用户信息</title>
    <link rel="stylesheet" type="text/css" href="../css/my.css">
</head>
<body>
<form action="/user/userSave" method="post">
    <input type="hidden" name="id" value='<c:out value="${user.id}" />'>
    <table>
        <tr>
            <td>用户名 :</td>
            <td><input type="text" name="name" value='<c:out value="${user.name}" />'></td>
        </tr>
        <tr>
            <td>密码 :</td>
            <td><input type="password" name="password" value='<c:out value="${user.password}" />'></td>
        </tr>
        <tr>
            <td>年龄 :</td>
            <td><input type="text" name="age" value='<c:out value="${user.age}" />'></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="保存"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>

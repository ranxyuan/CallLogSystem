<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>查询通话记录</title>
    <link rel="stylesheet" type="text/css" href="../css/my.css">
</head>
<body>
<form action='<c:url value="/callLog/findCallLog" />' method="post">
    <table>
        <tr>
            <td>电话号码 :</td>
            <td><input type="text" name="caller"></td>
        </tr>
        <tr>
            <td>起始时间 :</td>
            <td><input type="text" name="startTime"></td>
        </tr>
        <tr>
            <td>结束时间:</td>
            <td><input type="text" name="endTime"></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="查询"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
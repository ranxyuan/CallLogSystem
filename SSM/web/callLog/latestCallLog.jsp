<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>通话记录</title>
    <link rel="stylesheet" type="text/css" href="../css/my.css">
</head>
<body>
    <c:if test="${log == null}">
        无记录！
    </c:if>
    <c:if test="${log != null}">
        <table id="t1" border="1px" class="t-1" style="width: 800px">
            <tr>
                <td>电话1</td>
                <td><c:out value="${log.caller}" /></td>
            </tr>
            <tr>
                <td>电话2</td>
                <td><c:out value="${log.callee}"/></td>
            </tr>
            <tr>
                <td>时间</td>
                <td><c:out value="${log.callTime}"/></td>
            </tr>
            <tr>
                <td>时长</td>
                <td><c:out value="${log.callDuration}"/></td>
            </tr>

        </table>
    </c:if>

</body>
</html>
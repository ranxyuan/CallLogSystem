<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>通话记录统计结果</title>
    <link rel="stylesheet" type="text/css" href="../css/my.css">
    <script src="../js/jquery-3.2.0.min.js"></script>
    <script src="../js/echarts.js"></script>
    <script>
        $(function () {
            var myChart = echarts.init(document.getElementById('main'));
            var option = {
                title: {
                    text: '<c:out value="${title}" />'
                },
                tooltip: {},
                legend: {
                    data: ['通话次数']
                },
                xAxis: {
                    data: [<c:forEach items="${list}" var="e">'<c:out value="${e.yearMonth}"/>',</c:forEach>]
                },
                yAxis: {},
                series: [{
                    name: '通话次数',
                    type: 'bar',
                    data: [<c:forEach items="${list}" var="e"><c:out value="${e.count}"/>, </c:forEach>]
                }]
            };
            myChart.setOption(option);
        })
    </script>
</head>
<body>
    <form action='<c:url value="/callLog/statCallLog" />' method="post">
        电话号码 : <input type="text" name="caller"><br>
        年 份:  <input type="text" name="year"><br>
        <input type="submit" name="查询">
    </form>

    <div id="main" style="border:1px solid blue;width:600px;height:400px;">
    </div>

    <%--<table id="t1" border="1px" class="t-1" style="width: 800px">--%>
        <%--<thead>--%>
            <%--<tr>--%>
                <%--<td>月份</td>--%>
                <%--<td>次数</td>--%>
            <%--</tr>--%>
        <%--</thead>--%>
        <%--<tbody>--%>
            <%--<c:forEach items="${stat}" var="s">--%>
                <%--<tr>--%>
                    <%--<td><c:out value="${s.yearMonth}"/></td>--%>
                    <%--<td><c:out value="${s.count}"/></td>--%>
                <%--</tr>--%>
            <%--</c:forEach>--%>
        <%--</tbody>--%>
    <%--</table>--%>
</body>
</html>
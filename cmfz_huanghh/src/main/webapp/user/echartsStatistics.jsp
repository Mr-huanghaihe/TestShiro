<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <!-- 引入 ECharts 文件 -->
    <script src="${path}/echartsjs/jquery.min.js"></script>
    <script src="${path}/echartsjs/echarts.js"></script>
    <script type="text/javascript">
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            $.post("${path}/user/statistics", function (data) {
                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '用户注册量展示', //标题
                        link: "${path}/main/main.jsp",
                        target: "self",
                        subtext: "用户信息"
                    },
                    tooltip: {},  //鼠标的提示
                    legend: {
                        // show:false,  是否展示 选项
                        data: ["man", "woman"]  //选项
                    },
                    xAxis: {
                        data: data.month  //横坐标
                    },
                    yAxis: {},  //纵坐标   自适应
                    series: [{
                        name: 'man',
                        type: 'bar',
                        data: data.man
                    }, {
                        name: 'woman',
                        type: 'bar',
                        data: data.woman
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }, "json");
        });

    </script>

</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 800px;height:400px;" class="bg-info"></div>
</body>
</html>
<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>


    <!-- 引入 ECharts 文件 -->
    <script src="${path}/echartsjs/echarts.js"></script>
    <%--javascript 方式获取SDK--%>
    <script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <%--起始数据--%>
    <script type="text/javascript">

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


    </script>
    <%--GoEasy实时数据--%>
    <script type="text/javascript">

            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            var goEasy = new GoEasy({
                appkey: "BC-5bf80b1620d54596b737f5db10f8136b"
            });
            //接受
            goEasy.subscribe({
                channel: "channel1",
                onMessage: function (message) {

                    //将json字符串转为json对象
                    var data= JSON.parse(message.content);
                    console.log(data);

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
                }
            });

    </script>


<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 800px;height:400px;" class="bg-info"></div>

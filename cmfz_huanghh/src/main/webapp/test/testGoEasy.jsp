<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
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

    <%--javascript 方式获取SDK--%>
    <script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script>
        var goEasy = new GoEasy({
            appkey: "BC-5bf80b1620d54596b737f5db10f8136b"
        });
        //GoEasy-OTP可以对appkey进行有效保护,详情请参考​ ,
        //发送​
        /*goEasy.publish({
            channel: "channel1",
            message: "Hello, GoEasy!"
        });*/

        //接受
        goEasy.subscribe({
            channel: "channel1",
            onMessage: function (message) {
                alert("Channel:" + message.channel + " content:" + message.content);
            }
        });
    </script>
</head>
<body>

</body>
</html>
<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<div>
    <label class="col-sm-4 ">广告图片:</label>
    <div class="col-sm-8">
        <img  id="carPicImg" name="carPicImg" width="100%" onclick="fileSelect()"/>
    </div>
</div>
<div id="fileDiv" hidden="hidden">
    <label class="col-sm-2 control-label">图片上传:</label>
    <div class="col-sm-10">
        <input type="file" id="file" name="file" />
    </div>
</div>

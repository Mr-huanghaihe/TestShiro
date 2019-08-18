<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        $("#bntable").jqGrid({
            pager: '#bnpager',
            url: "${path}/banner/showAll",
            editurl: "${path}/banner/edit",
            styleUI: "Bootstrap",
            datatype: "json",
            colNames: ["ID", "名称", "路径", "描述", "状态", "上传时间"],
            cellEdit: true,
            rowNum: 3,
            rowList: [3, 6, 9],
            viewrecords: true,
            //caption:"用户列表",
            autowidth: true,
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {
                    name: "img_path",
                    editable: true,
                    edittype: "file",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='${path}/bootstrap/img/" + cellvalue + "' height='50' width='50' />";
                    }

                },
                {name: "description", editable: true},
                {
                    name: "status", formatter: function (cellvalue, options, rowObject) {
                        return "<button id='status' type='button' class='btn-success'>" + cellvalue + "</button>";
                    }

                },
                {name: "up_date"}
            ]
        });
        /*增删改查操作*/
        $("#bntable").jqGrid('navGrid', '#bnpager',
            {edit: true, add: true, del: true, addtext: "添加", edittext: "编辑", deltext: "删除"},
            {

                afterSubmit: function (data) {
                    //文件上传
                    $.ajaxFileUpload({
                        url: "${path}/banner/upload",
                        type: "post",
                        dataType: "JSON",
                        fileElementId: "img_path",
                        data: {id: data.responseText},
                        success: function () {
                            //刷新表单
                            $("#bntable").trigger("reloadGrid");
                        }
                    })
                    //一定要有返回值，返回什么都行
                    return "hello";
                },
                closeAfterEdit: true
            },
            {
                afterSubmit: function (data) {
                    //文件上传
                    $.ajaxFileUpload({
                        url: "${path}/banner/upload",
                        type: "post",
                        dataType: "JSON",
                        fileElementId: "img_path",
                        data: {id: data.responseText},
                        success: function () {
                            //刷新表单
                            $("#bntable").trigger("reloadGrid");
                        }
                    })
                    //一定要有返回值，返回什么都行
                    return "hello";
                },
                closeAfterAdd: true
            },
            {}
        );
    });
</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>轮播图信息</h2>
    </div>
    <div class="panel">
        <div>
            <ul class="nav nav-tabs">
                <li role="presentation" class="active"><a href="#">轮播图信息</a></li>
            </ul>
        </div>
        <div>
            <div>
                <button id="add" type="button" class="btn-success">添加</button>
                <button id="edit" type="button" class="btn-warning">修改</button>
                <button id="del" type="button" class="btn-danger">删除</button>
            </div>
        </div>
        <%--初始化表单--%>
        <table id="bntable"/>
        <%--定义分页工具栏--%>
        <div id="bnpager"></div>

    </div>




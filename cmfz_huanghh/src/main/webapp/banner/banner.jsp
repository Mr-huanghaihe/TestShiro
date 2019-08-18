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
            height:"auto",
            cellEdit: true,
            rowNum: 3,
            rowList: [3, 6, 9],
            viewrecords: true,
            //caption:"用户列表",
            autowidth: true,
            colModel: [
                {name: "id",align:"center"},
                {name: "title",align:"center", editable: true},
                {
                    name: "img_path",
                    align:"center",
                    editable: true,
                    edittype: "file",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='${path}/bootstrap/img/" + cellvalue + "' height='50' width='50' />";
                    }

                },
                {name: "description", align:"center",editable: true},
                {
                    name: "status",align:"center",
                    formatter: function (cellvalue, option, row) {
                        if (cellvalue == "正常") {
                            //展示状态
                            return "<button class='btn btn-success' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'>正常</button>";
                        } else {
                            //不展示状态
                            return "<button class='btn btn-danger' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'>冻结</button>";
                        }
                    },


                },
                {name: "up_date",align:"center"}
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

    //点击修改
    function change(id, value) {

        if (value == "正常") {

            $.ajax({
                url: "${path}/banner/status",
                type: "post",
                dataType: "JSON",
                data: {"id": id, "status": "冻结"},
                success: function (data) {
                    //页面的刷新
                    $("#bntable").trigger("reloadGrid");
                    //提示框添加信息
                    $("#showMsg").html(data.message);
                    //展示错误信息
                    $("#show").show();

                    //设置提示框时间
                    setTimeout(function () {
                        //关闭提示框
                        $("#show").hide();
                    }, 3000);
                }
            });
        } else {
            $.ajax({
                url: "${path}/banner/status",
                type: "post",
                dataType: "JSON",
                data: {"id": id, "status": "正常"},
                success: function (data) {
                    //页面的刷新
                    $("#bntable").trigger("reloadGrid");
                    //提示框添加信息
                    $("#showMsg").html(data.message);
                    //展示错误信息
                    $("#show").show();
                    //设置提示框时间
                    setTimeout(function () {
                        //关闭提示框
                        $("#show").hide();
                    }, 3000);
                }
            });
        }

    }
</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>轮播图信息</h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">轮播图信息</a></li>
    </ul>
    <div class="panel panel-body">
        <button id="add" type="button" class="btn btn-success">添加</button>
        <button id="edit" type="button" class="btn btn-warning">修改</button>
        <button id="del" type="button" class="btn btn-danger">删除</button>
    </div>
    <div id="show" class="center-block alert alert-warning alert-dismissible" role="alert"  style="width:300px;display: none">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong id="showMsg"></strong>
    </div>
<%--初始化表单--%>
    <table id="bntable"/>
    <%--定义分页工具栏--%>
    <div id="bnpager"></div>
</div>

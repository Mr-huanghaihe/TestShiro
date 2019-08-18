<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script charset="utf-8" src="${path}/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="${path}/kindeditor/lang/zh-CN.js"></script>
<script>
    KindEditor.create('#editor_id', {
        uploadJson:"${path}/editor/upload",
        filePostName:"photo",
        allowFileManager:true,
        fileManagerJson:"${path}/editor/preview",
        afterBlur:function (){  //编辑器失去焦点(blur)时执行的回调函数。
            this.sync();  //将编辑器中的内容同步到表单
        }
    });
</script>

<script>
    //文章相关
    $(function () {
        $("#bntable").jqGrid({
            pager: '#bnpager',
            url: "${path}/article/showAll",
            editurl: "${path}/article/edit",
            styleUI: "Bootstrap",
            datatype: "json",
            colNames: ["ID", "名称",  "文章","上师ID", "状态", "上传时间"],
            height: "auto",
            cellEdit: true,
            rowNum: 3,
            rowList: [3, 6, 9],
            viewrecords: true,
            //caption:"用户列表",
            autowidth: true,
            colModel: [
                {name: "id", align: "center"},
                {name: "title", align: "center"},
                /*{
                    name: "picture",
                    align: "center",
                    edittype: "file",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='/upload/picture/" + cellvalue + "' height='50' width='50' />";
                    }

                },*/
                {name:"content"},
                {name: "guruId", align: "center"},
                {
                    name: "status", align: "center",
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
                {name: "up_date", align: "center"}
            ]
        });
        /*增删改查操作*/
        $("#bntable").jqGrid('navGrid', '#bnpager',
            {edit: false, add: false, del: true, addtext: "添加", edittext: "编辑", deltext: "删除"},
            {},
            {},
            {}
        );
    });
    //查看文章详情及修改功能模态框
    $("#edit").click(function () {
       var rowId=$("#bntable").jqGrid("getGridParam","selrow");
       if(rowId!=null){
           var row=$("#bntable").jqGrid("getRowData",rowId);
           $("#myModal").modal("show");
           $("#title").val(row.title);
           $("#picture").val(row.picture);
           KindEditor.html("#editor_id",row.content);
           $("#modalFooter").html("<button type='button' onclick='updateArticle(\""+rowId+"\")' class='btn btn-default'>提交</button><button type='button' class='btn btn-primary' data-dismiss='modal'>关闭</button>")
       }else {
           alert("请选中一行数据！")
       }
    });
    //查看及修改事件
    function updateArticle(rowId) {
        $.ajax({
            url:"${path}/article/update?id="+rowId,
            type:"post",
            dataType:"json",
            data:$("#articleFrom").serialize(),
            success:function () {
                $('#myModal').modal('hide');
                $("#bntable").trigger("reloadGrid");
            }
        });
    }
    //添加文章模态框
    $("#add").click(function () {
        //给表单置空
        //$("#articleFrom")[0].reset();
        //清除表单相关内容
        $("#title").val("");
        KindEditor.html("#editor_id", "");
        $("#myModal").modal("show");
        //添加按钮
        $("#modalFooter").html("<button type='button' onclick='addArticle()' class='btn btn-default'>提交" +
            "</button><button type='button' class='btn btn-primary' data-dismiss='modal'>关闭</button>");
    });
    //添加文章事件
    function addArticle() {
        $.ajax({
            url:"${path}/article/add",
            type:"post",
            dateType:"json",
            data:$("#articleFrom").serialize(),
            success:function () {
                $('#myModal').modal('hide');
                $("#bntable").trigger("reloadGrid");
            }
        });

    }
    //点击修改状态
    function change(id, value) {

        if (value == "正常") {

            $.ajax({
                url: "${path}/article/status",
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
                url: "${path}/article/status",
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
    //点击删除文章
    $("#del").click(function () {
        var rowId=$("#bntable").jqGrid("getGridParam","selrow");
        if(rowId!=null){
            $.ajax({
                url:"${path}/article/delete?id="+rowId,
                type:"post",
                dateType:"json",
                data:$("#articleFrom").serialize(),
                success:function () {
                    $("#bntable").trigger("reloadGrid");
                }
            });

        }else {
            alert("请选中一行数据！")
        }
    });
    
</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>文章管理</h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">文章管理</a></li>
    </ul>
    <div class="panel panel-body">
        <button id="edit" type="button" class="btn btn-success">查看文章</button>
        <button id="add" type="button" class="btn btn-warning">添加文章</button>
        <button id="del" type="button" class="btn btn-danger">删除文章</button>
    </div>
    <div id="show" class="center-block alert alert-warning alert-dismissible" role="alert"
         style="width:300px;display: none">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <strong id="showMsg"></strong>
    </div>
    <%--初始化表单--%>
    <table id="bntable"/>
    <%--定义分页工具栏--%>
    <div id="bnpager"></div>
    <%--初始化模态框--%>

    <div id="myModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
        <div class="modal-dialog" role="document" style="width: 730px">
            <div class="modal-content">
                <%--模态框标题--%>
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="gridSystemModalLabel">文章</h4>
                </div>
                <%--模态框内容--%>
                <div class="modal-body">
                    <form class="form-horizontal" id="articleFrom" enctype="multipart/form-data">

                        <div class="input-group">
                            <span class="input-group-addon" id="basic-addon1">标题</span>
                            <input id="title" name="title" type="text" class="form-control" aria-describedby="basic-addon1">
                        </div><br>
                        <%--<div class="input-group">
                            <span class="input-group-addon" id="basic-addon2">封面</span>
                            <input id="picture" name="picture" type="file" class="form-control" aria-describedby="basic-addon2">
                        </div><br>--%>
                        <div class="input-group">
                            <%--初始化富文本编辑器--%>
                            <textarea id="editor_id" name="content" style="width:700px;height:300px;">

                            </textarea>
                        </div>
                    </form>
                </div>
                <%--  模态框按钮  --%>
                <div class="modal-footer" id="modalFooter">
                    <%--<button type="button" class="btn btn-default">提交</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>--%>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</div>

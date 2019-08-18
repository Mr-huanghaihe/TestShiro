<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script>
    $(function(){
        $("#bntable").jqGrid({
            url : "${path}/user/showAll",

            datatype : "json",
            rowNum : 3,
            autowidth:true,
            height:"auto",
            styleUI:"Bootstrap",
            rowList : [3,6,9],
            pager : '#bnpager',
            viewrecords : true,  //是否展示总条数
            autowidth: true,
            colNames : [ 'Id', '名字', '法名', '密码','性别','图像','手机号','注册时间','状态' ],
            colModel : [
                {name : 'id',width : 55,hidden:true},
                {name : 'name',editable:true,width : 90,align : "center"},
                {name : 'ahama',width : 100,align : "center"},
                {name : 'password',editable:true,width : 80,align : "center"},
                {name : 'sex',width : 80,align : "center"},
                {name : 'photo',width : 80,align : "center",
                    formatter:function(cellvalue){
                        return "<img src='${path}/bootstrap/img/"+cellvalue+"' style='width:180px;height:80px' />";
                    }// 返回图片。
                },
                {name : 'phone',align : "center"},
                {name : 'reg_date',align : "center"},
                {name : 'status',align : "center",
                    formatter: function (cellvalue, option, row) {
                        if (cellvalue == "正常") {
                            //展示状态
                            return "<button class='btn btn-success' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'>正常</button>";
                        } else {
                            //不展示状态
                            return "<button class='btn btn-danger' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'>冻结</button>";
                        }
                    },
                }
            ]

        });

        /*增删改查操作*/
        $("#bntable").jqGrid('navGrid', '#bnpager',
            {edit : false,add : false,del : true,search:true ,addtext:"添加",edittext:"编辑"},
            {},
            {},
            {}
        );
    });

    //点击修改
    function change(id,value){

        if(value=="正常"){
            $.ajax({
               url:"${path}/user/status",
               type:"post",
               dataType:"JSON",
               data:{"id":id,"status":"冻结"},
               success:function(){
                   //页面的刷新
                   $("#bntable").trigger("reloadGrid");
               }
            });
        }else{
            $.ajax({
                url:"${path}/user/status",
                type:"post",
                dataType:"JSON",
                data:{"id":id,"status":"正常"},
                success:function(){
                    //页面的刷新
                    $("#bntable").trigger("reloadGrid");
                }

            });
        }

    }
    //添加导入模态框
    $("#import").click(function () {
        $("#myModal").modal("show");
        //添加按钮
        $("#modalFooter").html("<button type='button' onclick='importUsers()' class='btn btn-danger'>导入" +
            "</button><button type='button' class='btn btn-primary' data-dismiss='modal'>取消</button>");
    });

    //导入事件
    function importUsers() {
        $.ajax({
            url:"${path}/user/import",
            type:"post",
            dateType:"json",
            success:function () {
                $('#myModal').modal('hide');
                $("#bntable").trigger("reloadGrid");
            }
        });
    }
    //添加导出模态框
    $("#export").click(function () {
        $("#myModal").modal("show");
        //添加按钮
        $("#modalFooter").html("<button type='button' onclick='exportUsers()' class='btn btn-danger'>导出" +
            "</button><button type='button' class='btn btn-primary' data-dismiss='modal'>取消</button>");
    });

    //导出事件
    function exportUsers() {
        $.ajax({
            url:"${path}/user/export",
            type:"post",
            dateType:"json",
            success:function () {
                $('#myModal').modal('hide');
                $("#bntable").trigger("reloadGrid");
            }
        });
    }

    //发送手机验证码
    function sendCode() {
        var number=$('#phoneNumbers').val();
        $.ajax({
            url:"${path}/user/phoneCode",
            type:"post",
            dataType:"JSON",
            data:{"phoneNumbers":number},
            success:function(){
                alert("发送成功！");
            }
        });
    }
</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#">用户管理</a></li>
    </ul>

    <div class="panel panel-body">
        <button id="import" type="button" class="btn btn-info" >导入用户信息</button>
        <button id="export" type="button" class="btn btn-success" >导出用户信息</button>
        <ul class="nav navbar-nav navbar-right">
            <form class="form-inline">
                <div class="form-group">
                    <label for="phoneNumbers">PhoneNumbers</label>
                    <input type="text" class="form-control" id="phoneNumbers" name="phoneNumbers" placeholder="PhoneNumbers">
                </div>
                <button type="button" class="btn btn-info" onclick="sendCode()">发送</button>&emsp;
            </form>
        </ul>
    </div>

    <%--提示信息--%>
    <div id="show" class="alert alert-warning alert-dismissible" role="alert"  style="width:200px;display: none">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong id="showMsg"></strong>
    </div>

    <%--初始化表单--%>
    <table id="bntable" />

    <%--定义分页工具栏--%>
    <div id="bnpager"></div>

    <%--初始化模态框--%>

    <div id="myModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
        <div class="modal-dialog" role="document" style="width: 730px">
            <div class="modal-content">
                <%--模态框标题--%>
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="gridSystemModalLabel">导出用户信息</h4>
                </div>
                <%--模态框内容--%>

                <%--  模态框按钮  --%>
                <div class="modal-footer" id="modalFooter">
                    <%--<button type="button" class="btn btn-default">提交</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>--%>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</div>




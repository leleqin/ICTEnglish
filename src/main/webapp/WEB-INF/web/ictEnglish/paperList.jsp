<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../../public/tag.jsp" %>

<%--我的试卷页面--%>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>首页</title>
    <!-- load css -->
    <link rel="stylesheet" type="text/css"
          href="${baseurl}/public/common/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css"
          href="${baseurl}/public/css/index.css" media="all">
</head>
<body>
<fieldset class="layui-elem-field" style="margin-left: 50px">
    <legend>试卷管理</legend>
    <table class="layui-table" lay-data="{width: 892, height:332, url:'/demo/table/user/', page:true, id:'idTest'}"
           lay-filter="demo">
        <thead>
        <tr>
            <th lay-data="{field:'id', width:80, sort: true, fixed: true}">ID</th>
            <th lay-data="{field:'username', width:80}">创建时间</th>
            <th lay-data="{field:'sex', width:80, sort: true}">试卷名称</th>
            <th lay-data="{field:'city', width:80}">操作</th>
        </tr>
        </thead>
        <%--查询出的数据列表显示--%>
        <tbody id="context">

        </tbody>
    </table>
    <%--分页--%>
    <div id="page"></div>
</fieldset>

<script type="text/javascript" src="${baseurl}/public/js/layui/layui.js" charset="UTF-8"></script>
<script type="text/javascript" src="${baseurl}/public/common/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript">
    //layui组件加载
    layui.use(['laypage', 'layer', 'form'], function () {
        var laypage = layui.laypage
            , $ = layui.jquery
            , layer = layui.layer
            , form = layui.form;

        $.ajax({
            type: 'post',
            url: '/paper/paperList',
            success: function (data) {
                $("#context").html("");
                for (var i = 0; i < data.length; i++) {
                    $("#context").append(`<tr>
                                    <td>` + (i + 1) + `</td>
                                    <td>` + data[i]['date_time'] + `</td>
                                    <td>` + data[i]['name'] + `</td>
                                    <td><button class="layui-btn layui-btn-sm layui-icon" onclick="editTest(` + data[i]['id'] + `)">&#xe642;编辑</button>
                                        <button class="layui-btn layui-btn-sm layui-btn-danger layui-icon" onclick="deleteTest(` + data[i]['id'] + `)">&#xe640;删除</button>
                                        <button class="layui-btn layui-btn-sm layui-btn-danger layui-icon" onclick="scan(` + data[i]['id'] + `)">&#xe640;浏览</button>
                                        <button class="layui-btn layui-btn-sm layui-btn-danger layui-icon" onclick="downloadPaper(` + data[i]['id'] + `)">&#xe640;下载试卷</button>
                                        <button class="layui-btn layui-btn-sm layui-btn-danger layui-icon" onclick="downloadQuestion(` + data[i]['id'] + `)">&#xe640;下载试卷</button>
                                    </td>
                                </tr>`);
                }
            }
        })


    })
</script>

</body>
</html>

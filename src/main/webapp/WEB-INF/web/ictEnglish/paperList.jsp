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
    <%--返回首页的button--%>
    <div class="bottom" align="right">
        <button class="layui-btn" onclick="backToIndex()">返回首页</button>
    </div>
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

        // 试卷列表显示
        $.ajax({
            type: 'post',
            url: '/paper/paperList',
            success: function (data) {
                $("#context").html("");
                for (var i = 0; i < data.length; i++) {
                    $("#context").append(`<tr>
                                    <td>` + (i + 1) + `</td>
                                    <td>` + data[i]['dateTime'] + `</td>
                                    <td>` + data[i]['name'] + `</td>
                                        <td><button class="layui-btn layui-btn-sm layui-icon" onclick="toPreviewPaper(` + data[i]['id'] + `)">&#xe60a;浏览</button>
                                        <button class="layui-btn layui-btn-sm layui-btn-danger layui-icon" onclick="deletePaper(` + data[i]['id'] + `)">&#xe640;删除</button>
                                        <button class="layui-btn layui-btn-sm layui-btn-danger layui-icon" onclick="downloadPaper(` + data[i]['id'] + `)">&#xe601;下载试卷</button>
                                        <button class="layui-btn layui-btn-sm layui-btn-danger layui-icon" onclick="downloadAnswer(` + data[i]['id'] + `)">&#xe601;下载答案</button>
                                    </td>
                                </tr>`);
                }
            }
        })

    });

    //试卷预览
    function toPreviewPaper(id) {
        $.ajax({
            type: 'post',
            url: '${baseurl}/paper/toPreviewPaper',
            data: JSON.stringify(id),
            dataType: 'json',
            contentType: 'application/json',
        success: function (data) {
            console.log(data);
            if (data.result) {
                    console.log("跳转");
                    window.location.href = "${baseurl}/ictEnglish/paperPreview";
                } else {
                    console.log("fail");
                }
            }
        })
    }

    //操作删除按钮
    function deletePaper(id) {
        layer.confirm("是否删除？", {icon: 3, title: '删除'}, function (index) {
            $.ajax({
                type: 'post',
                url: '/paper/deletePaper',
                data: {id: id}
            })
            layer.msg('删除成功', {icon: 6, time: 500}, function () {
                location.reload();
            });
            layer.close(index);
        })
    }

    //下载试卷
    function downloadPaper(id) {
        var url = '/paper/toDownLoadPaper'+'?id='+id;
        window.location.href=url;
    }

    //下载答案
    function downloadAnswer(id) {
        var url = '/paper/toDownloadAnswer'+'?id='+id;
        window.location.href=url;
    }


    //返回首页
    function backToIndex() {
        window.location.href = "${baseurl}/ictEnglish/index";
    }

</script>

</body>
</html>

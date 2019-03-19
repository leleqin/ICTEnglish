<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../../public/tag.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.thoughtWorks.entity.paper.QuestionPaper" %>

<html lang="en">
<head>
    <title>试卷预览</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">

    <!-- load css -->
    <link rel="stylesheet" type="text/css"
          href="${baseurl}/public/common/layui/css/layui.css" media="all">

    <link rel="stylesheet" type="text/css"
          href="${baseurl}/public/css/paperlist.css" media="all">
</head>

<body>
<%
    List<QuestionPaper> oldPaperList = (List<QuestionPaper>) session.getAttribute("oldPapers");
    QuestionPaper newPaper =  (QuestionPaper)session.getAttribute("newPaper");
    System.out.println("oldPaperList"+oldPaperList);
    if(newPaper!=null) {
        System.out.println("paperList.jsp  newPaper"+newPaper.toString());
    } else {
        System.out.println("paperList.jsp  newPaper == null");
    }
   /* System.out.println("paperList.jsp"+oldPaperList.toString());*/
%>

<div style="width: 80%; margin: 0 auto">
    <div class="newpaper layui-form">
        <h1>刚刚生成的试卷</h1>
        <table class="layui-table">
            <colgroup>
                <col width="200">
                <col width="300">
                <col width="300">
            </colgroup>
            <tbody>
            <% if(newPaper!=null && newPaper.getGenerateTime()!=null && newPaper.getName()!=null) { %>
            <tr>
                <td ><%=newPaper.getGenerateTime()%></td>
                <%
                    String paperName = newPaper.getName();
                    int type = newPaper.getType();
                    if(type == 1) {
                        paperName = paperName + "——A卷";
                    } else if(type ==2 ){
                        paperName = paperName + "——B卷";
                    }%>
                <td ><%=paperName%></td>

                <td >
                    <button class="layui-btn" onclick="removeRow(this,<%=newPaper.getId()%>)">删除</button>
                    <button class="layui-btn" onclick="preview(<%=newPaper.getId()%>)">预览</button>
                    <button class="layui-btn" onclick="downloadPaper(<%=newPaper.getId()%>)">下载试题</button>
                    <button class="layui-btn" onclick="downloadAnswer(<%=newPaper.getId()%>)">下载答案</button>
                </td>
            </tr>
            <%} %>
            </tbody>
        </table>
    </div>

    <div class="oldpaper layui-form">
        <h1>以前的试卷</h1>
        <table class="layui-table" >
            <colgroup>
                <col width="200">
                <col width="300">
                <col width="300">
            </colgroup>
            <tbody>
            <% if (oldPaperList!= null) {
                for (QuestionPaper listItem : oldPaperList) {
            %>
            <tr>
                <td ><%=listItem.getGenerateTime()%></td>
                <%
                    String paperName = listItem.getName();
                    int type = listItem.getType();
                    if(type == 1) {
                        paperName = paperName + "——A卷";
                    } else if(type == 2) {
                        paperName = paperName + "——B卷";
                    }
                %>
                <td ><%=paperName%></td>
                <td >
                    <button class="layui-btn" onclick="removeRow(this,<%=listItem.getId()%>)">删除</button>
                    <button class="layui-btn" onclick="preview(<%=listItem.getId()%>)">预览</button>
                    <button class="layui-btn" onclick="downloadPaper(<%=listItem.getId()%>)">下载试题</button>
                    <button class="layui-btn" onclick="downloadAnswer(<%=listItem.getId()%>)">下载答案</button>
                </td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div >

    <div class="bottom" align="right">
        <button class="layui-btn" onclick="backToIndex()">返回首页</button>
    </div>
</div>

<script type="text/javascript" src="${baseurl}/public/common/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="${baseurl}/public/common/jsplugin/jparticle.jquery.js"></script>
<script type="text/javascript">

    function preview(id) {
        $.ajax({
            type: 'POST',
            url: "${baseurl}/ictEnglish/paper/toPreviewPaper",
            data: JSON.stringify(id),
            dataType : 'json',
            contentType : 'application/json',
            success: function (data) {
                console.log(data);
                if (data.result) {
                    console.log("跳转");
                    window.location.href = "${baseurl}/ictEnglish/paperPreview";
                } else {
                    console.log("fail");
                    //layer.msg(data.msg);
                }
            }
        });
    }

    function downloadPaper(id) {
        console.log("download"+id);
        var url = '${baseurl}/ictEnglish/paper/toDownLoadPaper'+'?id='+id;
        console.log(url);
        window.location.href=url;
    }

    function downloadAnswer(id) {
        console.log("download"+id);
        var url = '${baseurl}/ictEnglish/paper/toDownloadAnswer'+'?id='+id;
        console.log(url);
        window.location.href=url;
    }

    function removeRow(r,id) {
        var row = r.parentNode.parentNode;
        var table = row.parentNode.parentNode;
        var rowIndex=row.rowIndex;
        table.deleteRow(rowIndex);
        console.log("remove："+JSON.stringify(id));
        var data = JSON.stringify(id);

        $.ajax({
            type: 'POST',
            url: "${baseurl}/ictEnglish/paper/removePaper",
            data: data,
            dataType : 'json',
            contentType : 'application/json'
        });
    }

    function backToIndex(){
        $.ajax({
            type: 'POST',
            url: "${baseurl}/ictEnglish/paper/clearSession",
        });
        window.location.href='${baseurl}/ictEnglish/index';
    }
</script>
</body>

</html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../public/tag.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.thoughtWorks.entity.paper.QuestionPackage" %>
<%@ page import="com.thoughtWorks.entity.question.Question" %>
<%@ page import="com.thoughtWorks.entity.question.Selection" %>
<%@ page import="com.thoughtWorks.entity.question.TorF" %>
<%@ page import="com.thoughtWorks.entity.question.WordEnToCN" %>
<%@ page import="com.thoughtWorks.entity.question.WordCnToEN" %>
<%@ page import="com.thoughtWorks.entity.question.Explanation" %>
<%@ page import="com.thoughtWorks.entity.question.SentenceEnToCN" %>
<%@ page import="com.thoughtWorks.entity.question.SentenceCnToEN" %>
<%--
  Created by IntelliJ IDEA.
  User: liu
  Date: 2019/1/3
  Time: 21:48
  To change this template use File | Settings | File Templates.
--%>
<html lang="en">
<body>

<div style="margin: 0 auto">
    <div class="top">
        <div style="float: left;">题库管理</div>
        <div onclick="goImportPaper()" onMouseOver=this.style.color="#C1C1C1" onMouseOut=this.style.color="black" style = "text-decoration: underline;float:right;">word导入</div>
    </div>
</div>

<div class="question layui-form">
    <div class="layui-collapse" lay-accordion lay-filter="questionType">
        <div class="layui-colla-item">
            <div class="layui-colla-title">选择题</div>
            <div class="layui-colla-content">
                <table class="layui-table" id="Selection"></table>
            </div>

            <div class="layui-colla-item">
                <div class="layui-colla-title">判断题</div>
                <div class="layui-colla-content">
                    <table class="layui-table" id="TorF"></table>
                </div>
            </div>

            <div class="layui-colla-item">
                <div class="layui-colla-title">短语英译汉</div>
                <div class="layui-colla-content">
                    <table class="layui-table" id="WordEnToCN"></table>
                </div>
            </div>

            <div class="layui-colla-item">
                <div class="layui-colla-title">短语汉译英</div>
                <div class="layui-colla-content">
                    <table class="layui-table" id="WordCnToEN"></table>
                </div>
            </div>

            <div class="layui-colla-item">
                <div class="layui-colla-title">缩略词解释</div>
                <div class="layui-colla-content">
                    <table class="layui-table" id="Explanation"></table>
                </div>
            </div>

            <div class="layui-colla-item">
                <div class="layui-colla-title">句子英译汉</div>
                <div class="layui-colla-content">
                    <table class="layui-table" id="SentenceEnToCN"></table>
                </div>
            </div>

            <div class="layui-colla-item">
                <div class="layui-colla-title">句子汉译英</div>
                <div class="layui-colla-content">
                    <table class="layui-table" id="SentenceCnToEN"></table>
                </div>
            </div>
        </div>
    </div>

    <div class="bottom">
        <div align="right">
            <button class="layui-btn" onclick="backToIndex()">返回首页</button>
        </div>
    </div>
</div>

<script type="text/javascript" src="${baseurl}/public/common/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="${baseurl}/public/common/jsplugin/jparticle.jquery.js"></script>

<script type="text/javascript">

    layui.use(
        [ 'jquery', 'layer', 'element', 'laypage', 'form',
            'laytpl', 'tree' ],
        function () {
            window.jquery = window.$ = layui.jquery;
            window.layer = layui.layer;
            let element = layui.element(),form = layui.form(),laytpl = layui.laytpl;
            //监听折叠
            element.on('collapse(questionType)',function (data) {
                var type = data.content[0].getElementsByTagName("table")[0].getAttribute("id");
                var params={"type":type};
                console.log(type);
                $.ajax({
                    type:'POST',
                    url:'${baseurl}/ictEnglish/paper/getDataBase',
                    data:JSON.stringify(params),
                    dataType:'json',
                    contentType:'application/json',
                    success:function (data) {
                        var _html="";
                        switch (type) {
                            case "Selection":
                                var selectionList = data.selectionList;
                                _html = generateHtml("Selection",_html,selectionList);
                                $("#Selection").html(_html);
                                break;
                            case "TorF":
                                var torFList = data.torFList;
                                _html = generateHtml("TorF",_html,torFList);
                                $("#TorF").html(_html);
                                break;
                            case "WordEnToCN":
                                var wordEnToCNList = data.wordEnToCNList;
                                _html = generateHtml("WordEnToCN",_html,wordEnToCNList);
                                $("#WordEnToCN").html(_html);
                                break;
                            case "WordCnToEN":
                                var wordCnToENList = data.wordCnToENList;
                                _html = generateHtml("WordCnToEN",_html,wordCnToENList);
                                $("#WordCnToEN").html(_html);
                                break;
                            case "Explanation":
                                var explanationList = data.explanationList;
                                _html = generateHtml("Explanation",_html,explanationList);
                                $("#Explanation").html(_html);
                                break;
                            case "SentenceEnToCN":
                                var sentenceEnToCNList = data.sentenceEnToCNList;
                                _html = generateHtml("SentenceEnToCN",_html,sentenceEnToCNList);
                                $("#SentenceEnToCN").html(_html);
                                break;
                            case "SentenceCnToEN":
                                var sentenceCnToENList = data.sentenceCnToENList;
                                _html = generateHtml("SentenceCnToEN",_html,sentenceCnToENList);
                                $("#SentenceCnToEN").html(_html);
                                break;
                        }
                    },
                    dataType : 'json',
                    contentType : 'application/json'
                });
            })
        }
    )


    function removeRow(r,index,id) {
        var type = getType(index);
        var row = r.parentNode.parentNode;
        var table = row.parentNode.parentNode;
        var rowIndex = row.rowIndex;
        table.deleteRow(rowIndex);
        var data = {"type":type,"id":id};
        console.log("remove: "+JSON.stringify(data));
        $.ajax({
            type:'POST',
            url:"${baseurl}/ictEnglish/paper/removeQuestionInDB",
            data:JSON.stringify(data),
            dataType:'json',
            contentType:'application/json',
            success:function () {

            }
        });
    }

    function goImportPaper() {
        window.location.href = "${baseurl}/ictEnglish/import";
    }

    function getType(index) {
        var type = "";
        switch (index) {
            case 1:
                type="Selection";
                break;
            case 2:
                type="TorF";
                break;
            case 3:
                type="WordEnToCN";
                break;
            case 4:
                type="WordCnToEN";
                break;
            case 5:
                type="Explanation";
                break;
            case 6:
                type="SentenceEnToCN";
                break;
            case 7:
                type="SentenceCnToEN";
                break;
            default:
                type = "";
                break;
        }
        return type;
    }

    function backToIndex() {
        $.ajax({
            type:'POST',
            url: "${baseurl}/ictEnglish/clearSession",
        });
        window.location.href = '${baseurl}/ictEnglish/index';
    }

    function generateHtml(str,_html,list) {
        //       if (list.length == 0 ||list.length >= 0 ) {
        _html += `<colgroup>
                 <col>
                 <col>
                 <col width="300">
                 <col width="100">
                 <col width="100">
             </colgroup>
             <thead>
                 <tr>
                     <td>
                         <p>题目</p>
                     </td>
                     <td>
                         <p>答案</p>
                     </td>
                     <td>
                     <p>知识点</p>
                 </td>
                 <td>
                 <p>难易度</p>
             </td>
                 <td>
                         <button class="layui-btn" onclick="`;
        switch (str) {
            case "Selection":
                _html +=` window.location.href = '${baseurl}/ictEnglish/addSelectionPage'`;
                break;
            case "TorF":
                _html +=`window.location.href='${baseurl}/ictEnglish/addTorFPage'`;
                break;
            case "WordEnToCN":
                _html +=`window.location.href='${baseurl}/ictEnglish/addQuestionPage'+'?type=WordEnToCN'`;
                break;
            case "WordCnToEN":
                _html +=`window.location.href='${baseurl}/ictEnglish/addQuestionPage'+'?type=WordCnToEN'`;
                break;
            case "Explanation":
                _html +=`window.location.href='${baseurl}/ictEnglish/addQuestionPage'+'?type=Explanation'`;
                break;
            case "SentenceEnToCN":
                _html +=`window.location.href='${baseurl}/ictEnglish/addQuestionPage'+'?type=SentenceEnToCN'`;
                break;
            case "SentenceCnToEN":
                _html +=`window.location.href='${baseurl}/ictEnglish/addQuestionPage'+'?type=SentenceCnToEN'`;
                break;
        }
        _html +=`">添加</button>
                     </td>
                 </tr>
             </thead>`;
        console.log(_html);
        console.log(list.length);
        if (list !=null && list.length >= 0 ) {
            _html += `<tbody>`;
            for(var i = 0; i < list.length; i++) {
                if(str =="Selection") {
                    _html += `<tr><td>`;
                    _html +=list[i].question;
                    _html +=`<br/><p style="float:left">`;
                    _html +="A."+list[i].answerA;
                    _html +=`</p><br/>`;
                    _html +=`<p style="float:left">`;
                    _html +="B."+list[i].answerB;
                    _html +=`</p><br/>`;
                    _html +=`<p style="float:left">`;
                    _html +="C."+list[i].answerC;
                    _html +=`</p><br/>`;
                    _html +=`<p style="float:left">`;
                    _html +="D."+list[i].answerD;
                    _html +=`</p><br/>`;
                    _html += `</td><td>`;
                    _html += list[i].answer;
                    _html +=`<br/></td>`;
                    _html +=`<td>`;
                    _html +=list[i].knowledgePoint;
                    _html +=`</td>`;
                    _html +=`<td>`;
                    _html +=list[i].difficulty;
                    _html +=`</td>`;
                    _html +=`<td><button class="layui-btn" onclick="removeRow(this,1,`;
                    _html +=list[i].id;
                    _html +=`)">删除</button>`;
                    _html +=`</td>`
                } else if (str =="TorF") {
                    _html += `<tr><td>`;
                    _html +=list[i].question;
                    _html += `</td><td>`;
                    _html += list[i].answer;
                    _html +=`<br/></td>`;
                    _html +=`<td>`;
                    _html +=list[i].knowledgePoint;
                    _html +=`</td>`;
                    _html +=`<td>`;
                    _html +=list[i].difficulty;
                    _html +=`</td>`;
                    _html +=`<td><button class="layui-btn" onclick="removeRow(this,2,`;
                    _html +=list[i].id;
                    _html +=`)">删除</button>`;
                    _html +=`</td>`
                }else if (str=="WordEnToCN") {
                    _html += `<tr><td>`;
                    _html +=list[i].question;
                    _html += `</td><td>`;
                    _html += list[i].answer;
                    _html +=`<br/></td>`;
                    _html +=`<td>`;
                    _html +=list[i].knowledgePoint;
                    _html +=`</td>`;
                    _html +=`<td>`;
                    _html +=list[i].difficulty;
                    _html +=`</td>`;
                    _html +=`<td><button class="layui-btn" onclick="removeRow(this,3,`;
                    _html +=list[i].id;
                    _html +=`)">删除</button>`;
                    _html +=`</td>`
                }else if (str=="WordCnToEN") {
                    _html += `<tr><td>`;
                    _html +=list[i].question;
                    _html += `</td><td>`;
                    _html += list[i].answer;
                    _html +=`<br/></td>`;
                    _html +=`<td>`;
                    _html +=list[i].knowledgePoint;
                    _html +=`</td>`;
                    _html +=`<td>`;
                    _html +=list[i].difficulty;
                    _html +=`</td>`;
                    _html +=`<td><button class="layui-btn" onclick="removeRow(this,4,`;
                    _html +=list[i].id;
                    _html +=`)">删除</button>`;
                    _html +=`</td>`
                }else if (str=="Explanation") {
                    _html += `<tr><td>`;
                    _html +=list[i].question;
                    _html += `</td><td>`;
                    _html += list[i].answer;
                    _html +=`<br/></td>`;
                    _html +=`<td>`;
                    _html +=list[i].knowledgePoint;
                    _html +=`</td>`;
                    _html +=`<td>`;
                    _html +=list[i].difficulty;
                    _html +=`</td>`;
                    _html +=`<td><button class="layui-btn" onclick="removeRow(this,5,`;
                    _html +=list[i].id;
                    _html +=`)">删除</button>`;
                    _html +=`</td>`
                }else if (str=="SentenceEnToCN") {
                    _html += `<tr><td>`;
                    _html +=list[i].question;
                    _html += `</td><td>`;
                    _html += list[i].answer;
                    _html +=`<br/></td>`;
                    _html +=`<td>`;
                    _html +=list[i].knowledgePoint;
                    _html +=`</td>`;
                    _html +=`<td>`;
                    _html +=list[i].difficulty;
                    _html +=`</td>`;
                    _html +=`<td><button class="layui-btn" onclick="removeRow(this,6,`;
                    _html +=list[i].id;
                    _html +=`)">删除</button>`;
                    _html +=`</td>`
                }else if (str == "SentenceCnToEN") {
                    _html += `<tr><td>`;
                    _html +=list[i].question;
                    _html += `</td><td>`;
                    _html += list[i].answer;
                    _html +=`<br/></td>`;
                    _html +=`<td>`;
                    _html +=list[i].knowledgePoint;
                    _html +=`</td>`;
                    _html +=`<td>`;
                    _html +=list[i].difficulty;
                    _html +=`</td>`;
                    _html +=`<td><button class="layui-btn" onclick="removeRow(this,7,`;
                    _html +=list[i].id;
                    _html +=`)">删除</button>`;
                    _html +=`</td>`
                }
            }
            _html += `</tbody>`;
        }
        return _html;
        console.log(_html);
    }


</script>
</body>
<head>
    <title>题库管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta name="renderer" content="webkit">

    <!-- load css -->
    <link rel="stylesheet" type="text/css"
          href="${baseurl}/public/common/layui/css/layui.css" media="all">

    <link rel="stylesheet" type="text/css"
          href="${baseurl}/public/css/paperpreview.css" media="all">
</head>
</html>
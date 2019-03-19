<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../public/tag.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="com.thoughtWorks.entity.paper.QuestionPackage"%>
<%@ page import="com.thoughtWorks.entity.question.Question" %>
<%@ page import="com.thoughtWorks.entity.question.Selection" %>
<%@ page import="com.thoughtWorks.entity.question.TorF" %>
<%@ page import="com.thoughtWorks.entity.question.WordEnToCN" %>
<%@ page import="com.thoughtWorks.entity.question.WordCnToEN" %>
<%@ page import="com.thoughtWorks.entity.question.Explanation" %>
<%@ page import="com.thoughtWorks.entity.question.SentenceEnToCN" %>
<%@ page import="com.thoughtWorks.entity.question.SentenceCnToEN" %>
<%@ page import="com.thoughtWorks.entity.paper.PaperNameInfo" %>
<!--
试卷预览页面
@author ChenJiale
-->
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
          href="${baseurl}/public/css/paperpreview.css" media="all">
</head>

<body>
<%
    System.out.println("paperPreview.jsp: test preview");
    QuestionPackage qp = (QuestionPackage)session.getAttribute("questionPackage");
    List<Selection> selection = qp.getSelectionList();
    List<TorF> torF = qp.getTorFList();
    List<WordEnToCN> wordEnToCN = qp.getWordEnToCNList();
    List<WordCnToEN> wordCnToEN = qp.getWordCnToENList();
    List<Explanation> explanation = qp.getExplanationList();
    List<SentenceEnToCN> sentenceEnToCN = qp.getSentenceEnToCNList();
    List<SentenceCnToEN> sentenceCnToEN = qp.getSentenceCnToENList();

    QuestionPackage abReapeat = (QuestionPackage)session.getAttribute("abReapeatPackage");
    List<Selection> abRselection = abReapeat.getSelectionList();
    List<TorF> abRtorF = abReapeat.getTorFList();
    List<WordEnToCN> abRwordEnToCN = abReapeat.getWordEnToCNList();
    List<WordCnToEN> abRwordCnToEN = abReapeat.getWordCnToENList();
    List<Explanation> abRexplanation = abReapeat.getExplanationList();
    List<SentenceEnToCN> abRsentenceEnToCN = abReapeat.getSentenceEnToCNList();
    List<SentenceCnToEN> abRsentenceCnToEN = abReapeat.getSentenceCnToENList();

    PaperNameInfo paperNameInfo = (PaperNameInfo) session.getAttribute("paperNameInfo");
    int  type = -1;
    String repeatStr = "";
    if(paperNameInfo != null ){
        type = paperNameInfo.getType();
    }

    String abReapeatRate = qp.getAbRepeat(abReapeat);
    System.out.println("paperpreview.jsp:"+abReapeatRate);
    String abRateString = "A/B卷重复率:"+abReapeatRate;
    if(type == 1) {
        repeatStr = "与B卷重复";

    } else if(type == 2) {
        repeatStr = "与A卷重复";
    }
%>

<div style="width: 80%; margin: 0 auto">
    <div class="top">
        <h1>试卷预览</h1>
    </div>

    <div class="question layui-form">
        <table class="layui-table" id="selection">
            <colgroup>
                <col>
                <col width="100">
            </colgroup>
            <thead>
            <tr>
                <td><p>选择题</p></td>
            </tr>
            </thead>
            <tbody>
            <%  if(selection != null) {
                for (Selection list : selection) {
                    Selection question = list;
                    if (question != null) {
            %>
            <tr>
                <td>
                    <%=question.getQuestion()%><br/>
                    <p style="float:left">A.<%=question.getAnswerA()%></p><br/>
                    <p style="float:left">B.<%=question.getAnswerB()%></p><br/>
                    <p style="float:left">C.<%=question.getAnswerC()%></p><br/>
                    <p style="float:left">D.<%=question.getAnswerD()%></p><br/>
                    <%if(abRselection != null && abRselection.contains(question)) { %>
                    <p><font color="red"> <%= repeatStr %></font></p>
                    <%} %>
                </td>
            </tr>
            <%
                        }
                    }
                }
            %>
            </tbody>
        </table>

        <table class="layui-table" id="torF">
            <colgroup>
                <col>
                <col width="100">
            </colgroup>
            <thead>
            <tr>
                <td><p>判断题</p></td>
            </tr>
            </thead>
            <tbody>
            <%
                if(torF!=null ) {
                    for (TorF list : torF) {
                        TorF question = list;
                        if (question != null) {
            %>
            <tr>
                <td>
                    <%=question.getQuestion()%><br/>
                    <%if(abRtorF != null && abRtorF.contains(question)) { %>
                    <p><font color="red"> <%= repeatStr %></font></p>
                    <%} %>
                </td>
            </tr>
            <%
                        }
                    }
                }
            %>
            </tbody>
        </table>

        <table class="layui-table" id="wordEnToCN">
            <colgroup>
                <col>
                <col width="100">
            </colgroup>
            <thead>
            <tr>
                <td><p>常用词组英译汉</p></td>
            </tr>
            </thead>
            <tbody>
            <%
                if(wordEnToCN!=null ) {
                    for (Question list : wordEnToCN) {
                        WordEnToCN question = (WordEnToCN) list;
                        if (question != null) {
            %>
            <tr>
                <td>
                    <%=question.getQuestion()%><br/>
                    <%if(abRwordEnToCN != null && abRwordEnToCN.contains(question)) { %>
                    <p><font color="red"> <%= repeatStr %></font></p>
                    <%} %>
                </td>
            </tr>
            <%
                        }
                    }
                }
            %>
            </tbody>
        </table>

        <table class="layui-table" id="wordCnToEN">
            <colgroup>
                <col>
                <col width="100">
            </colgroup>
            <thead>
            <tr>
                <td><p>常用词组汉译英</p></td>
            </tr>
            </thead>
            <tbody>
            <%
                if(wordCnToEN!=null ) {
                    for (WordCnToEN list : wordCnToEN) {
                        WordCnToEN question = list;
                        if (question != null) {
            %>
            <tr>
                <td>
                    <%=question.getQuestion()%><br/>
                    <%if(abRwordCnToEN != null && abRwordCnToEN.contains(question)) { %>
                    <p><font color="red"> <%= repeatStr %></font></p>
                    <%} %>
                </td>
            </tr>
            <%
                        }
                    }
                }
            %>
            </tbody>
        </table>

        <table class="layui-table" id="explanation">
            <colgroup>
                <col>
                <col width="100">
            </colgroup>
            <thead>
            <tr>
                <td><p>缩略词解释</p></td>
            </tr>
            </thead>
            <tbody>
            <%
                if(explanation!=null ) {
                    for (Explanation list : explanation) {
                        Explanation question = list;
                        if (question != null) {
            %>
            <tr>
                <td>
                    <%=question.getQuestion()%><br/>
                    <%if(abRexplanation != null && abRexplanation.contains(question)) { %>
                    <p><font color="red"> <%= repeatStr %></font></p>
                    <%} %>
                </td>
            </tr>
            <%
                        }
                    }
                }
            %>
            </tbody>
        </table>

        <table class="layui-table" id="sentenceEnToCN">
            <colgroup>
                <col>
                <col width="100">
            </colgroup>
            <thead>
            <tr>
                <td><p>句子英译汉</p></td>
            </tr>
            </thead>
            <tbody>
            <%
                if(sentenceEnToCN!=null ) {
                    for (SentenceEnToCN list : sentenceEnToCN) {
                        SentenceEnToCN question = list;
                        if (question != null) {
            %>
            <tr>
                <td>
                    <%=question.getQuestion()%><br/>
                    <%if(abRsentenceEnToCN != null && abRsentenceEnToCN.contains(question)) { %>
                    <p><font color="red"> <%= repeatStr %></font></p>
                    <%} %>
                </td>
            </tr>
            <%
                        }
                    }
                }
            %>
            </tbody>
        </table>

        <table class="layui-table" id="sentenceCnToEN">
            <colgroup>
                <col>
                <col width="100">
            </colgroup>
            <thead>
            <tr>
                <td><p>句子汉译英</p></td>
            </tr>
            </thead>
            <tbody>
            <%
                if(sentenceCnToEN!=null ) {
                    for (SentenceCnToEN list : sentenceCnToEN) {
                        SentenceCnToEN question = list;
                        if (question != null) {
            %>
            <tr>
                <td>
                    <%=question.getQuestion()%><br/>
                    <%if(abRsentenceCnToEN != null && abRsentenceCnToEN.contains(question)) { %>
                    <p><font color="red"> <%= repeatStr %></font></p>
                    <%} %>
                </td>
            </tr>
            <%
                        }
                    }
                }
            %>
            </tbody>
        </table>
    </div>

    <div class="bottom">
        <% if((abReapeatRate != null || abReapeatRate !="")&&(type ==1 || type == 2)) {%>
        <div id="repeat" align="left">
            <%=abRateString %>
        </div>
        <%} %>
        <div align="right">
            <button class="layui-btn" onclick="window.history.back()">确定</button>
        </div>
    </div>

</div>

<script type="text/javascript" src="${baseurl}/public/common/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="${baseurl}/public/common/jsplugin/jparticle.jquery.js"></script>
<script type="text/javascript">

</script>

</body>


</html>
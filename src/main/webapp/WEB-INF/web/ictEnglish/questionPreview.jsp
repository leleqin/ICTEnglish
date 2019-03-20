<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../public/tag.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.thoughtWorks.entity.paper.QuestionPackage"%>
<%@ page import="com.thoughtWorks.entity.question.Question"%>
<%@ page import="com.thoughtWorks.entity.question.Selection"%>
<%@ page import="com.thoughtWorks.entity.question.TorF"%>
<%@ page import="com.thoughtWorks.entity.question.WordCnToEN"%>
<%@ page import="com.thoughtWorks.entity.question.WordEnToCN"%>
<%@ page import="com.thoughtWorks.entity.question.Explanation"%>
<%@ page import="com.thoughtWorks.entity.question.SentenceCnToEN"%>
<%@ page import="com.thoughtWorks.entity.question.SentenceEnToCN"%>
<%@ page import="com.thoughtWorks.entity.paper.PaperNameInfo"%>


<%--/@author changduo--%>

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
    System.out.println("qustion.jsp: test preview");
    QuestionPackage qp = (QuestionPackage)session.getAttribute("questionPackage");
    System.out.println("seession："+ qp);
    List<Selection> selection = null;
    List<TorF> torf = null;
    List<WordEnToCN> wordentocn = null;
    List<WordCnToEN> wordcntoen = null;
    List<Explanation> explanation = null;
    List<SentenceEnToCN> sentenceentocn = null;
    List<SentenceCnToEN> sentencecntoen = null;

    if(qp!=null) {
        selection = qp.getSelectionList();
        torf = qp.getTorFList();
        wordentocn = qp.getWordEnToCNList();
        wordcntoen = qp.getWordCnToENList();
        explanation = qp.getExplanationList();
        sentenceentocn = qp.getSentenceEnToCNList();
        sentencecntoen = qp.getSentenceCnToENList();
    }


    QuestionPackage abReapeat = (QuestionPackage)session.getAttribute("abReapeatPackage");
    List<Selection> abRselection = null;
    List<TorF> abRtorf = null;
    List<WordEnToCN> abRwordentocn = null;
    List<WordCnToEN> abRwordcntoen = null;
    List<Explanation> abRexplanation = null;
    List<SentenceEnToCN> abRsentenceentocn = null;
    List<SentenceCnToEN> abRsentencecntoen = null;

    if(abReapeat != null) {
        abRselection = abReapeat.getSelectionList();
        abRtorf = abReapeat.getTorFList();
        abRwordentocn = abReapeat.getWordEnToCNList();
        abRwordcntoen = abReapeat.getWordCnToENList();
        abRexplanation = abReapeat.getExplanationList();
        abRsentenceentocn = abReapeat.getSentenceEnToCNList();
        abRsentencecntoen = abReapeat.getSentenceCnToENList();
    }


    PaperNameInfo paperNameInfo = (PaperNameInfo) session.getAttribute("paperNameInfo");
    int  type = -1;
    String repeatStr = null;
    if(paperNameInfo != null ){
        type = paperNameInfo.getType();
    }

    String abReapeatRate = null;
    if(qp!=null) {
        abReapeatRate = qp.getAbRepeat(abReapeat);
    }

    System.out.println("questionPreview.jsp:"+abReapeatRate);
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
                <td>
                    <p>
                        <span>选择题</span>
                        <span>(每小题</span>
                        <input size="1" type="text" id="selScore" style = "text-align:center; border-bottom: 1px solid #dbdbdb; border-top:0px; border-left:0px; border-right:0px;"/>
                        <span>分   共  </span>
                        <input size="1" type="text" id="selTotalScore" style = "text-align:center; border-bottom: 1px solid #dbdbdb; border-top:0px; border-left:0px; border-right:0px;"/>
                        <span> 分)</span>
                    </p>
                </td>
                <td>
                    <button class="layui-btn" onclick="window.location.href='${baseurl}/ictEnglish/manualPaper'+'?type=Selection'+'&fromPreview=true';">添加</button>
                </td>
            </tr>
            </thead>
            <tbody>
            <%if(selection != null) {
                for (Selection list : selection) {
                    Selection question = list;
                    if (question != null) {
            %>
            <tr>
                <td>
                    <%=question.getQuestion()%><br/>
                    <p style="float:left"><%=question.getAnswerA()%></p><br/>
                    <p style="float:left"><%=question.getAnswerB()%></p><br/>
                    <p style="float:left"><%=question.getAnswerC()%></p><br/>
                    <p style="float:left"><%=question.getAnswerD()%></p><br/>
                    <%if(abRselection != null && abRselection.contains(question) && repeatStr != null) { %>
                    <p><font color="red"> <%= repeatStr %></font></p>
                    <%} %>
                </td>
                <td >
                    <button class="layui-btn" onclick="removeRow(this,0,<%=Integer.toString(question.getId())%>)">删除</button>
                </td>
            </tr>
            <%
                        }
                    }
                }
            %>
            </tbody>
        </table>

        <table class="layui-table" id="torf">
            <colgroup>
                <col>
                <col width="100">
            </colgroup>
            <thead>
            <tr>
                <td>
                    <p>
                        <span>判断题</span>
                        <span>(每小题</span>
                        <input size="1" type="text" id="torScore" style = "text-align:center; border-bottom: 1px solid #dbdbdb; border-top:0px; border-left:0px; border-right:0px;"/>
                        <span>分   共  </span>
                        <input size="1" type="text" id="torTotalScore" style = "text-align:center; border-bottom: 1px solid #dbdbdb; border-top:0px; border-left:0px; border-right:0px;"/>
                        <span> 分)</span>
                    </p>
                </td>
                <td>
                    <button class="layui-btn" onclick="window.location.href='${baseurl}/ictEnglish/manualPaper'+'?type=TorF'+'&fromPreview=true';">添加</button>
                </td>
            </tr>
            </thead>
            <tbody>
            <%
                if(torf != null ) {
                    for (TorF list : torf) {
                        TorF question = list;
                        if (question != null) {
            %>
            <tr>
                <td>
                    <%=question.getQuestion()%><br/>
                    <%if(abRtorf != null && abRtorf.contains(question) && repeatStr != null) { %>
                    <p><font color="red"> <%= repeatStr %></font></p>
                    <%} %>
                </td>
                <td >
                    <button class="layui-btn" onclick="removeRow(this,1,<%=Integer.toString(question.getId())%>)">删除</button>
                </td>
            </tr>
            <%
                        }
                    }
                }
            %>
            </tbody>
        </table>

        <table class="layui-table" id="wordentocn">
            <colgroup>
                <col>
                <col width="100">
            </colgroup>
            <thead>
            <tr>
                <td>
                    <p>
                        <span>常用词组英译汉</span>
                        <span>(每小题</span>
                        <input size="1" type="text" id="wetScore" style = "text-align:center; border-bottom: 1px solid #dbdbdb; border-top:0px; border-left:0px; border-right:0px;"/>
                        <span>分   共  </span>
                        <input size="1" type="text" id="wetTotalScore" style = "text-align:center; border-bottom: 1px solid #dbdbdb; border-top:0px; border-left:0px; border-right:0px;"/>
                        <span> 分)</span>
                    </p>
                </td>
                <td>
                    <button class="layui-btn" onclick="window.location.href='${baseurl}/ictEnglish/manualPaper'+'?type=WordEnToCN'+'&fromPreview=true';">添加</button>
                </td>
            </tr>
            </thead>
            <tbody>
            <%
                if(wordentocn!=null ) {
                    for (Question list : wordentocn) {
                        WordEnToCN question = (WordEnToCN)list;
                        if (question != null) {
            %>
            <tr>
                <td>
                    <%=question.getQuestion()%><br/>
                    <%if(abRwordentocn != null && abRwordentocn.contains(question) && repeatStr != null) { %>
                    <p><font color="red"> <%= repeatStr %></font></p>
                    <%} %>
                </td>
                <td >
                    <button class="layui-btn" onclick="removeRow(this,2,<%=Integer.toString(question.getId())%>)">删除</button>
                </td>
            </tr>
            <%
                        }
                    }
                }
            %>
            </tbody>
        </table>

        <table class="layui-table" id="wordcntoen">
            <colgroup>
                <col>
                <col width="100">
            </colgroup>
            <thead>
            <tr>
                <td>
                    <p>
                        <span>常用词组汉译英</span>
                        <span>(每小题</span>
                        <input size="1" type="text" id="wctScore" style = "text-align:center; border-bottom: 1px solid #dbdbdb; border-top:0px; border-left:0px; border-right:0px;"/>
                        <span>分   共  </span>
                        <input size="1" type="text" id="wctTotalScore" style = "text-align:center; border-bottom: 1px solid #dbdbdb; border-top:0px; border-left:0px; border-right:0px;"/>
                        <span> 分)</span>
                    </p>
                </td>
                <td>
                    <button class="layui-btn" onclick="window.location.href='${baseurl}/ictEnglish/manualPaper'+'?type=WordCnToEN'+'&fromPreview=true';">添加</button>
                </td>
            </tr>
            </thead>
            <tbody>
            <%
                if(wordcntoen!=null ) {
                    for (WordCnToEN list : wordcntoen) {
                        WordCnToEN question = list;
                        if (question != null) {
            %>
            <tr>
                <td>
                    <%=question.getQuestion()%><br/>
                    <%if(abRwordcntoen != null && abRwordcntoen.contains(question) && repeatStr != null) { %>
                    <p><font color="red"> <%= repeatStr %></font></p>
                    <%} %>
                </td>
                <td >
                    <button class="layui-btn" onclick="removeRow(this,3,<%=Integer.toString(question.getId())%>)">删除</button>
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
                <td>
                    <p>
                        <span>缩略词解释</span>
                        <span>(每小题</span>
                        <input size="1" type="text" id="expScore" style = "text-align:center; border-bottom: 1px solid #dbdbdb; border-top:0px; border-left:0px; border-right:0px;"/>
                        <span>分   共  </span>
                        <input size="1" type="text" id="expTotalScore" style = "text-align:center; border-bottom: 1px solid #dbdbdb; border-top:0px; border-left:0px; border-right:0px;"/>
                        <span> 分)</span>
                    </p>
                </td>
                <td>
                    <button class="layui-btn" onclick="window.location.href='${baseurl}/ictEnglish/manualPaper'+'?type=Explanation'+'&fromPreview=true';">添加</button>
                </td>
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
                    <%if(abRexplanation != null && abRexplanation.contains(question) && repeatStr != null) { %>
                    <p><font color="red"> <%= repeatStr %></font></p>
                    <%} %>
                </td>
                <td >
                    <button class="layui-btn" onclick="removeRow(this,4,<%=Integer.toString(question.getId())%>)">删除</button>
                </td>
            </tr>
            <%
                        }
                    }
                }
            %>
            </tbody>
        </table>

        <table class="layui-table" id="sentenceentocn">
            <colgroup>
                <col>
                <col width="100">
            </colgroup>
            <thead>
            <tr>
                <td>
                    <p>
                        <span>句子英译汉</span>
                        <span>(每小题</span>
                        <input size="1" type="text" id="setcore" style = "text-align:center; border-bottom: 1px solid #dbdbdb; border-top:0px; border-left:0px; border-right:0px;"/>
                        <span>分   共  </span>
                        <input size="1" type="text" id="setTotalScore" style = "text-align:center; border-bottom: 1px solid #dbdbdb; border-top:0px; border-left:0px; border-right:0px;"/>
                        <span> 分)</span>
                    </p>
                </td>
                <td>
                    <button class="layui-btn" onclick="window.location.href='${baseurl}/ictEnglish/manualPaper'+'?type=SentenceEnToCN'+'&fromPreview=true';">添加</button>
                </td>
            </tr>
            </thead>
            <tbody>
            <%
                if(sentenceentocn!=null ) {
                    for (SentenceEnToCN list : sentenceentocn) {
                        SentenceEnToCN question = list;
                        if (question != null) {
            %>
            <tr>
                <td>
                    <%=question.getQuestion()%><br/>
                    <%if(abRsentenceentocn != null && abRsentenceentocn.contains(question) && repeatStr != null) { %>
                    <p><font color="red"> <%= repeatStr %></font></p>
                    <%} %>
                </td>
                <td >
                    <button class="layui-btn" onclick="removeRow(this,5,<%=Integer.toString(question.getId())%>)">删除</button>
                </td>
            </tr>
            <%
                        }
                    }
                }
            %>
            </tbody>
        </table>

        <table class="layui-table" id="sentencecntoen">
            <colgroup>
                <col>
                <col width="100">
            </colgroup>
            <thead>
            <tr>
                <td>
                    <p>
                        <span>句子汉译英</span>
                        <span>(每小题</span>
                        <input size="1" type="text" id="sctScore" style = "text-align:center; border-bottom: 1px solid #dbdbdb; border-top:0px; border-left:0px; border-right:0px;"/>
                        <span>分   共  </span>
                        <input size="1" type="text" id="sctotalScore" style = "text-align:center; border-bottom: 1px solid #dbdbdb; border-top:0px; border-left:0px; border-right:0px;"/>
                        <span> 分)</span>
                    </p>
                </td>
                <td>
                    <button class="layui-btn" onclick="window.location.href='${baseurl}/ictEnglish/manualPaper'+'?type=SentenceCnToEN'+'&fromPreview=true';">添加</button>
                </td>
            </tr>
            </thead>
            <tbody>
            <%
                if(sentencecntoen!=null ) {
                    for (SentenceCnToEN list : sentencecntoen) {
                        SentenceCnToEN question = list;
                        if (question != null) {
            %>
            <tr>
                <td>
                    <%=question.getQuestion()%><br/>
                    <%if(abRsentencecntoen != null && abRsentencecntoen.contains(question) && repeatStr != null) { %>
                    <p><font color="red"> <%= repeatStr %></font></p>
                    <%} %>
                </td>
                <td >
                    <button class="layui-btn" onclick="removeRow(this,6,<%=Integer.toString(question.getId())%>)">删除</button>
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
            <button class="layui-btn" onclick="window.location.href='${baseurl}/ictEnglish/manualPaper'">放弃 </button>
            <button class="layui-btn" onclick="store()">生成试卷</button>
        </div>
    </div>

</div>

<script type="text/javascript" src="${baseurl}/public/common/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="${baseurl}/public/common/jsplugin/jparticle.jquery.js"></script>
<script type="text/javascript">



    layui.use(
        [ 'jquery', 'layer', 'element', 'laypage', 'form',
            'laytpl', 'tree' ],
        function() {
            window.jQuery = window.$ = layui.jquery;
            window.layer = layui.layer;
            let element = layui.element(), form = layui.form(), laytpl = layui.laytpl;
            $("#selScore").change(function(){
                console.log("change");
                <% if (selection != null) {%>
                $("#selTotalScore").val($("#selScore").val()*<%=selection.size()%>);
                <% } else {%>
                $("#selTotalScore").val("0");
                <% } %>
            });

            $("#torScore").change(function(){
                console.log("change");
                <% if (torf != null) {%>
                $("#torTotalScore").val($("#torScore").val()*<%=torf.size()%>);
                <% } else {%>
                $("#torTotalScore").val("0");
                <% } %>
            });

            $("#wetScore").change(function(){
                console.log("change");
                <% if (wordentocn != null) {%>
                $("#wetTotalScore").val($("#wetScore").val()*<%=wordentocn.size()%>);
                <% } else {%>
                $("#wetTotalScore").val("0");
                <% } %>
            });

            $("#wctScore").change(function(){
                console.log("change");
                <% if (wordcntoen != null) {%>
                $("#wctTotalScore").val($("#wctScore").val()*<%=wordcntoen.size()%>);
                <% } else {%>
                $("#wctTotalScore").val("0");
                <% } %>
            });

            $("#expScore").change(function(){
                console.log("change");
                <% if (explanation != null) {%>
                $("#expTotalScore").val($("#expScore").val()*<%=explanation.size()%>);
                <% } else {%>
                $("#expTotalScore").val("0");
                <% } %>
            });

            $("#setScore").change(function(){
                console.log("change");
                <% if (sentenceentocn != null) {%>
                $("#setTotalScore").val($("#setScore").val()*<%=sentenceentocn.size()%>);
                <% } else {%>
                $("#setTotalScore").val("0");
                <% } %>
            });

            $("#sctScore").change(function(){
                console.log("change");
                <% if (sentencecntoen != null) {%>
                $("#sctTotalScore").val($("#sctScore").val()*<%=sentencecntoen.size()%>);
                <% } else {%>
                $("#sctTotalScore").val("0");
                <% } %>
            });

            remove = {
                setRepeatRate:function() {
                    console.log("setRepeatRate");
                    <% qp = (QuestionPackage)session.getAttribute("questionPackage");
                    abReapeat = (QuestionPackage)session.getAttribute("abReapeatPackage");
                    abReapeatRate = qp.getAbRepeat(abReapeat);
                    if((abReapeatRate != null || abReapeatRate !="")&&(type ==1 || type == 2)) {
                        abRateString = "A/B卷重复率:"+abReapeatRate;
                    }
                    %>
                    $("#repeat").html("<%=abRateString %>");
                    location.reload();
                }
            }
        })

    function removeRow(r,index,id) {
        var type = getType(index);
        var row = r.parentNode.parentNode;
        var table = row.parentNode.parentNode;
        var rowIndex=row.rowIndex;
        table.deleteRow(rowIndex);
        var data = {"type":type, "id": id};
        console.log("remove："+JSON.stringify(data));
        $.ajax({
            type: 'POST',
            url: "${baseurl}/ictEnglish/paper/removeQestionPreview",
            data: JSON.stringify(data),
            dataType : 'json',
            contentType : 'application/json',
            success: function(data) {
                if (data.result) {
                    //console.log("重新加载");
                    remove.setRepeatRate();
                } else {
                    console.log("fail");
                }

            }
        });
    }

    function getType(index) {
        var type="";
        switch (index) {
            case 0:
                type="Selection";
                break;
            case 1:
                type="TorF";
                break;
            case 2:
                type="WordEnToCN";
                break;
            case 3:
                type="WordCnToEN";
                break;
            case 4:
                type="Explanation";
                break;
            case 5:
                type="SentenceEnToCN";
                break;
            case 6:
                type="SentenceCnToEN";
                break;
            default :
                type = "";
                type = "";
                break;
        }
        return type;
    }

    function store() {
        $.ajax({
            type: 'POST',
            url: "${baseurl}/ictEnglish/paper/store",
            success: function (data) {
                if (data.result) {
                    console.log("跳转");
                    window.location.href = "${baseurl}/ictEnglish/paperList";
                } else {
                    console.log("fail");
                }
            }
        });
    }

    function goPaperList() {
        $.ajax({
            type: 'POST',
            url: "${baseurl}/ictEnglish/paper/getPaperList",
            success: function ( ) {
                window.location.href = "${baseurl}/ictEnglish/paperList";
            }
        });
    }
</script>

</body>

</html>
<%--
  Created by IntelliJ IDEA.
  User: liu
  Date: 2019/1/4
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../public/tag.jsp"%>
<%
request.setCharacterEncoding("UTF-8");
String type = request.getParameter("type");
String title = "";
 if (type.equals("WordEnToCN")){
    title = "短语英译汉录入";
}else if (type.equals("WordCnToEN")){
    title = "短语汉译英录入";
}else if (type.equals("Explanation")){
    title = "缩略词解释";
}else if (type.equals("SentenceEnToCN")){
    title = "句子英译汉";
}else if (type.equals("SentenceCnToEN")){
    title = "句子汉译英";
}
%>
<html lang="en">
<head>
    <title><%=title%></title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">

    <!-- load css -->
    <link rel="stylesheet" type="text/css"
          href="${baseurl}/public/common/layui/css/layui.css" media="all">

    <link rel="stylesheet" type="text/css"
          href="${baseurl}/public/css/addQuestion.css" media="all">
    <link rel="stylesheet" href="${baseurl}/public/kindeditor/themes/default/default.css" />
    <link rel="stylesheet" href="${baseurl}/public/kindeditor/plugins/code/prettify.css" />
    <script charset="utf-8" src="${baseurl}/public/kindeditor/guan.js"></script>
    <script charset="utf-8" src="${baseurl}/public/kindeditor/lang/zh-CN.js"></script>
    <script charset="utf-8" src="${baseurl}/public/kindeditor/plugins/code/prettify.js"></script>
    <script>
        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('textarea[name="content"]', {
                cssPath : '${baseurl}/public/kindeditor/plugins/code/prettify.css',
                uploadJson : '${baseurl}/Kindeditor/uploadFile',

                afterCreate : function() {
                    this.sync();
                },
                afterBlur:function(){
                    this.sync();
                }
            });
        });
    </script>
</head>
<body>
    <div style="width: 80%; margin: 0 auto">
        <div class="top layui-form">
            <label class="layui-form-label">请选择:</label>
            <div class="layui-input-block"></div>
            <input type="radio" name="type" value="题目" title="题目" checked="">
            <input type="radio" name="type" value="答案" title="答案">
            <input type="radio" name="type" value="知识点" title="知识点">
            <input type="radio" name="type" value="难易度" title="难易度">
        </div>
    </div>

    <div class="edit">
        <form name="example" method="post" action="${baseurl}/ictEnglish/paper/addSelectionPage">
            <textarea id="content" name="content" style="width: 100%; height: 200px;visibility: hidden"></textarea>
        </form>
    </div>

    <div class="saveDraft" align="right">
        <button class="layui-btn" onclick="save()">保存</button>
    </div>

    <div class="content">
        <table class="layui-table">
            <colgroup>
                <col width="100">
            </colgroup>
            <thead>
                <tr>
                    <td>
                        <p><%=title%></p>
                    </td>
                    <td></td>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <p>题目</p>
                    </td>
                    <td class='question' id='question'></td>
                </tr>
                <tr>
                    <td>
                        <p>答案</p>
                    </td>
                    <td class='answer' id='answer'></td>
                </tr>
                <tr>
                    <td>
                        <p>知识点</p>
                    </td>
                    <td class='knowledge' id='knowledge'></td>
                </tr>
                <tr>
                    <td>
                        <p>难易度</p>
                    </td>
                    <td class='difficulty' id='difficulty'></td>
                </tr>
            </tbody>
        </table>

        <div class="submitContent" align="right">
            <button class="layui-btn" onclick="cleanQuestion()">清空</button>
            <button class="layui-btn" onclick="add.submit()">提交</button>
        </div>
    </div>

</body>

<script>

    layui.use(
        [ 'jquery', 'layer', 'element', 'laypage', 'form',
            'laytpl', 'tree' ],
        function() {
            window.jQuery = window.$ = layui.jquery;
            window.layer = layui.layer;
            let element = layui.element(), form = layui.form(), laytpl = layui.laytpl;
            add = {
                submit:function() {
                    var question = document.getElementById("question").innerHTML;
                    var answer = document.getElementById("answer").innerHTML;
                    var knowledge = document.getElementById("knowledge").innerHTML;
                    var difficulty = document.getElementById("difficulty").innerHTML;
                    if(question.length == 0|| answer.length == 0 ){
                        layer.alert("试题内容录入不全");
                        return;
                    }
                    var questionContent = {
                        "type":"<%=type%>",
                        "question":question,
                        "answer":answer,
                        "knowledge":knowledge,
                        "difficulty":difficulty
                    };
                    console.log("question: "+question.length);
                    console.log(JSON.stringify(questionContent));
                    $.ajax({type: 'POST',
                        url: "${baseurl}/ictEnglish/paper/addQuestionToDB",
                        data: JSON.stringify(questionContent),
                        dataType : 'json',
                        contentType : 'application/json',
                        success: function (data) {
                            if (data.result) {
                                var index = parent.layer.alert('导入成功！', {
                                    skin: 'layui-layer-molv' //样式类名  自定义样式
                                    ,closeBtn: 1    // 是否显示关闭按钮
                                    ,anim: 1 //动画类型
                                    ,btn: ['继续导入','返回题库管理'] //按钮
                                    ,icon: 6    // icon
                                    ,yes:function(){
                                        layer.close(index);
                                        window.location.reload();
                                    }
                                    ,btn2:function(){
                                        layer.close(index);
                                        location.href = "${baseurl}/ictEnglish/dbPage";
                                    }});
                            } else {
                                console.log("fail");
                            }
                        }
                    });
                }
            }
        })

    function save(){
        editor.sync();
        var html = document.getElementById('content').value;
        if(html != ""){
            html = '<p>'+html+'</p>';
        }
        console.log("lq html: "+ html);
        var type = $('input[name="type"]:checked').val();
        console.log("type: "+type);
        if(type == "题目") {
            $(".question").html(html);
        } else if(type == "答案") {
            $(".answer").html(html);
        } else if(type == "知识点") {
            $(".knowledge").html(html);
        } else if(type == "难易度") {
            $(".difficulty").html(html);
        }
    }

    function cleanQuestion() {
        document.getElementById("question").innerHTML=null;
        document.getElementById("answer").innerHTML=null;
        document.getElementById("knowledge").innerHTML=null;
        document.getElementById("difficulty").innerHTML=null;
    }


</script>

</html>
<%!
    private String htmlspecialchars(String str) {
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\"", "&quot;");
        System.out.println(str);
        return str;
    }
%>
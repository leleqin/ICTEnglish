<%--
  Created by IntelliJ IDEA.
  User: liu
  Date: 2019/1/4
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../public/tag.jsp" %>
<% request.setCharacterEncoding("UTF-8"); %>
<html lang="en">
<head>
    <title>选择题录入</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
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
        KindEditor.ready(function (K) {
            editor = K.create('textarea[name="content"]',{
                cssPath : '${baseurl}/public/kindeditor/plugins/code/prettify.css',
                uploadJson : '${baseurl}/Kindeditor/uploadFile',

                afterCreate:function () {
                    this.sync();
                },
                afterBlur:function () {
                    this.sync();
                }
            });
        });
    </script>
</head>
<body>
    <div style="width: 80% ; margin: 0 auto">
        <div class="top layui-form">
            <label class="layui-input-label">请选择</label>
            <div class="layui-input-block">
                <input type="radio" name="type" value="题目" title="题目" checked="">
                <input type="radio" name="type" value="选项A" title="选项A" >
                <input type="radio" name="type" value="选项B" title="选项B">
                <input type="radio" name="type" value="选项C" title="选项C">
                <input type="radio" name="type" value="选项D" title="选项D">
                <input type="radio" name="type" value=答案 title="答案">
                <input type="radio" name="type" value="知识点" title="知识点">
                <input type="radio" name="type" value="难易度" title="难易度">
            </div>
        </div>

        <div class="edit">
            <form name="example" method="post" action="${baseurl}/ictEnglish/addSelectionPage">
                <textarea id="content" name="content" style="width: 100%;height: 200px;visibility: hidden"></textarea>
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
                            <p>选择题</p>
                        </td>
                        <td>
                        </td>
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
                            <p>A.</p>
                        </td>
                        <td class="answerA" id="answerA"></td>
                    </tr>
                    <tr>
                        <td>
                            <p>B.</p>
                        </td>
                        <td class="answerB" id="answerB"></td>
                    </tr>
                    <tr>
                        <td>
                            <p>C.</p>
                        </td>
                        <td class="answerC" id="answerC"></td>
                    </tr>
                    <tr>
                        <td>
                            <p>D.</p>
                        </td>
                        <td class="answerD" id="answerD"></td>
                    </tr>
                    <tr>
                        <td>
                            <p>答案</p>
                        </td>
                        <td class="answer" id="answer"></td>
                    </tr>
                    <tr>
                        <td>
                            <p>知识点</p>
                        </td>
                        <td class="knowledge" id="knowledge"></td>
                    </tr>
                    <tr>
                        <td>
                            <p>难易度</p>
                        </td>
                        <td class="difficulty" id="difficulty"></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="submitContent" align="right">
            <button class="layui-btn" onclick="cleanSelection()">清空</button>
            <button class="layui-btn" onclick="add.submit()">提交</button>
        </div>
    </div>
</body>

<script type="text/javascript" src="${baseurl}/public/common/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="${baseurl}/public/common/jsplugin/jparticle.jquery.js"></script>

<script>
    layui.use(
        [ 'jquery', 'layer', 'element', 'laypage', 'form',
            'laytpl', 'tree' ],
        function () {
            window.jQuery = window.$ = layui.jquery;
            window.layui.layer;
            let element = layui.element(),form = layui.form(),laytpl = layui.laytpl;
            add = {
                submit:function () {
                var question = document.getElementById("question").innerHTML;
                var answerA = document.getElementById("answerA").innerHTML;
                var answerB = document.getElementById("answerB").innerHTML;
                var answerC = document.getElementById("answerC").innerHTML;
                var answerD = document.getElementById("answerD").innerHTML;
                var answer = document.getElementById("answer").innerHTML;
                var knowledge = document.getElementById("knowledge").innerHTML;
                var difficulty = document.getElementById("difficulty").innerHTML;
                if (question.length == 0 || answerA.length == 0 || answerB.length == 0 || answerC.length == 0 || answerD.length == 0 ||
                    answer.length == 0 ) {
                    layer.alert("试题内容录入不全");
                    return;
                }
                var questionContent = {
                    "question":question,
                    "answerA":answerA,
                    "answerB":answerB,
                    "answerC":answerC,
                    "answerD":answerD,
                    "answer":answer,
                    "knowledge":knowledge,
                    "difficulty":difficulty
                };
                console.log("question: "+question.length);
                    $.ajax({type:'POST',
                        url : "${baseurl}/ictEnglish/paper/addSelectionToDB",
                        data : JSON.stringify(questionContent),
                        dateType : 'json',
                        contentType : 'application/json',
                        success : function (data) {
                            console.log("add success");
                            if (data.result){
                                var index = parent.layer.alert('导入成功!',{
                                    skin : 'layui-layer-moly',  //样式类名 自定义样式
                                    closeBtn:1,                 //是否显示关闭按钮
                                    anim : 1,                   //动画类型
                                    btn : ['继续导入','返回题库管理'],    //按钮
                                    yes:function () {
                                        layer.close(index);
                                        window.location.reload();
                                    },
                                    btn2:function () {
                                        layer:close(index);
                                        location.href = "${baseurl}/ictEnglish/dbPage";
                                    }});
                            }else  {
                                console.log("fail");
                            }
                        }
                    });
            }
            }
        })
    
    function save() {
        editor.sync();
        var html = document.getElementById('content').value;
        if (html != ""){
            html = '<p>'+html+'</p>';
        }
        var type = $('input[name="type"]:checked').val();
        console.log("type: "+type);
        if (type == "题目") {
            $(".question").html(html);
        }else if (type == "选项A"){
            $(".answerA").html(html);
        } else if (type == "选项B"){
            $(".answerB").html(html);
        } else  if (type == "选项C"){
            $(".answerC").html(html);
        } else if (type == "选项D"){
            $(".answerD").html(html);
        } else if (type == "答案"){
            $(".answer").html(html);
        } else if (type == "知识点"){
            $(".knowledge").html(html);
        } else if (type == "难易度"){
            $(".difficulty").html(html);
        }
    }

    function cleanSelection() {
        document.getElementById("question").innerHTML = null;
        document.getElementById("answerA").innerHTML = null;
        document.getElementById("answerB").innerHTML = null;
        document.getElementById("answerC").innerHTML = null;
        document.getElementById("answerD").innerHTML = null;
        document.getElementById("answer").innerHTML = null;
        document.getElementById("knowledge").innerHTML = null;
        document.getElementById("difficulty").innerHTML = null;
    }

    function cleanContent() {
        document.getElementById("content").innerHTML = null;
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

<%--
  Created by IntelliJ IDEA.
  User: liu
  Date: 2019/2/18
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../public/tag.jsp"%>
<html lang="en">
<head>
    <title>导入试卷</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta name="renderer" content="webkit">

    <!-- load css  -->
    <link rel="stylesheet" type="text/css"
        href="${baseurl}/public/common/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css"
        href="${baseurl}/public/css/import.css" media="all">
</head>
<body>
    <div style="width: 80%; margin: 0 auto">
        <div class = "top">
            <div onclick="downloadModule()" onMouseOver=this.style.color="#C1C1C1" onMouseOut=this.style.color="black" style = "text-decoration: underline;">模板下载</div>
            <br/>
            <input type="file" name="file" lay-type="file" class="layui-upload-file" id="uploadpaper"></input>

        </div>
        <div class="bottom">
            <div class="bottom" align="right">
                <button class="layui-btn" onclick="notImport()">放弃</button>
                <button class="layui-btn" onclick="importPaper()">导入题库</button>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="${baseurl}/public/common/js/jquery-3.2.0.min.js"></script>
    <script src="${baseurl}/public/common/layui/layui.js" charset="UTF-8"></script>


    <script>
        layui.use('upload',function () {
            layui.upload({
                url:'${baseurl}/ictEnglish/paper/uploadFile',
                elem:'#uploadpaper',//上传接口
                success:function (res) {    //上传成功后的回调
                    console.log(res);
                    var data = res.data;
                    console.log(data);
                    $(".middle").html(data);

                }
            });
        });

        function notImport() {
            $.ajax({
                type:'POST',
                url: "${baseurl}/ictEnglish/paper/notImport",
                success:function () {
                    window.location.href = "${baseurl}/ictEnglish/index";
                }
            });
        }

        function importPaper() {
            $.ajax({
                type:'POST',
                url:"${baseurl}/ictEnglish/paper/importPaper",
                success:function (data) {
                    if (data.result){
                        var index = parent.layer.alert('导入成功!',{
                            skin:'layui-layer-molv',//样式类名 自定义样式
                            closeBtn:1, //是否显示关闭按钮
                            anim:1,     //动画类型
                            btn:['继续导入','返回首页'] ,    //按钮
                            icon:6,//icon
                            yes:function () {
                                $(".middle").html("");
                                layer.close(index);
                            },
                            btn2:function () {
                                layer.close(index);
                                location.href = "${baseurl}/ictEnglish/index";
                            }});
                    }else   {
                        layer.msg(data.msg);
                    }
                }
            });
        }

        function downloadModule() {
            window.location.href = '${baseurl}/ictEnglish/paper/toDownLoadModule';
        }
    </script>

</body>
</html>

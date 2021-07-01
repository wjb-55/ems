<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="utf-8" contentType="text/html; utf-8" isELIgnored="false"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>后台列表页面</title>
    <link rel="stylesheet" href="${path}/static/css/bootstrap.min.css"/>
    <script src="../static/js/jquery-3.5.1.min.js"></script>
    <script src="${path}/static/js/jquery-3.5.1.min.js"></script>
    <script>
        $(function (){
            $("#sen").click(function () {
                var xhr;
                //判断浏览器的差异
                if (window.XMLHttpRequest){ //webket内核
                    xhr = new XMLHttpRequest();
                }else {//   ie
                    xhr = new ActiveXObject("Microsoft.XMLHTTP");
                }
                // //2-2发送get请求 并传递参数
                $.get(
                    "${path}/food/deleteAllES",
                    $("#formId").serialize(),
                    function (data) {
                    },"JSON");
            });

        });
        $(function (){
            $("#sen2").click(function () {
                var xhr;
                //判断浏览器的差异
                if (window.XMLHttpRequest){ //webket内核
                    xhr = new XMLHttpRequest();
                }else {//   ie
                    xhr = new ActiveXObject("Microsoft.XMLHTTP");
                }
                // //2-2发送get请求 并传递参数
                $.get(
                    "${path}/food/ques",
                    $("#formId").serialize(),
                    function (data) {
                    },"JSON");
            });

        });
    </script>
</head>
<body>

<!--功能按钮-->
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">后台食谱管理系统</a>
        </div>

        <!--功能按钮 -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <form id="formId" class="navbar-form navbar-left" >
                <button id="sen" type="button" class="btn btn-danger">清空ES索引库</button>
                <button id="sen2" type="button" class="btn btn-info">基于mysql数据重建索引</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="text-info">小陈</span></a></li>
                <li><a href="#">退出系统</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-12">
            <a href="${path}/add.jsp" class="btn btn-info">添加</a>
        </div>
    </div>
    <div class="row" style="margin-top: 20px;">
        <div class="col-sm-12">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>菜谱名称</th>
                    <th>图片</th>
                    <th>录入时间</th>
                    <th>录入人</th>
                    <th>关于摘要</th>
                    <th>步骤摘要</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="list">
                <tr>
                    <th scope="row">${list.id}</th>
                    <td>${list.foodName}</td>
                    <td><img style="width: 200px;height: 120px;" src="${path}/${list.foodSrc}" class="img-thumbnail" alt=""></td>
                    <td>${list.foodDate}</td>
                    <td>${list.person}</td>
                    <td>${list.fileFood}</td>
                    <td>${list.foodImporter}</td>
                    <td><a href="${path}/food/delete?foodSrc=${list.foodSrc}&id=${list.id}" class="btn btn-danger">删除</a>&nbsp;&nbsp;
                        <a href="${path}/food/selectById?id=${list.id}" class="btn btn-info">修改</a></td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!--热词处理-->

</div>


</body>
</html>
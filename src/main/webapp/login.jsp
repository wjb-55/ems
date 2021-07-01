<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="utf-8" contentType="text/html; utf-8" isELIgnored="false"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
    <title>login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${path}/static/css/bootstrap.min.css"/>
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
                    "${path}/user/login",
                    $("#formId").serialize(),
                    function (data) {
                        if (data.status == 100){
                            location.href="${path}/food/all"
                        }else if(data.status == 104){
                            $("#showMsg").text(data.message);
                        }else{
                            $("#showMsg").text(data.message);
                        }
                    },"JSON");
            });

        });
    </script>
</head>

<body>
<div id="wrap" class="container-fluid">
    <div id="top_content" class="row"  style="margin: 0 auto;">
        <div class="col-sm-8 col-sm-offset-2">
            <div id="header">
                <div id="topheader">
                    <h1 class="text-center text-info">欢迎进入菜谱管理系统V1.0</h1>
                </div>
                <div id="navigation">
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="margin-top: 20px;">
        <div class="col-sm-8 col-sm-offset-2">
            <div id="content">
                <form id="formId" >
                    <div class="form-group">
                        <label for="username">用户名</label>
                        <input type="text"  v-model="username" id="username" class="form-control" name="name"/>
                    </div>
                    <div class="form-group">
                        <label for="password">密码</label>
                        <input type="password" id="password"  v-model="password"  class="form-control" name="password"/>                    </div>
                    <br>
                    <input id="sen" type="button" style="width: 98%"   class="btn btn-danger" value="登录&raquo;"/><br>
                    <span id="showMsg" style="color: #a52a2a"/>
                </form>
            </div>
        </div>
    </div>
    <div class="row" style="margin-top: 40px;">
        <div class="col-sm-8 col-sm-offset-2">
            <h5 class="text-center">Fruit@136.com</h5>
        </div>
    </div>
</div>
</body>
</html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="utf-8" contentType="text/html; utf-8" isELIgnored="false"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>前台首页</title>
    <link rel="stylesheet" href="${path}/static/css/bootstrap.min.css"/>
    <script src="${path}/static/js/jquery-3.5.1.min.js"></script>
</head>
<body>

<div class="container-fluid">

    <!--搜索框-->
    <div class="row" style="margin-top: 20px;">
        <div class="col-sm-12" style="text-align: center">
            <form class="form-inline" id="formId" action="${path}/food/pageAllES">
                <div class="form-group" style="width: 600px;">
                    <input type="text" style="width: 600px;" class="form-control" name="message" placeholder="请输入查询条件">
                </div>
                <button type="submit" class="btn btn-primary">搜索</button>
            </form>
        </div>
    </div>

    <h1 class="page-header">搜索结果</h1>
    <!--搜索列表-->
    <div class="row" style="margin-top: 20px">
        <c:forEach items="${list}" var="list">
        <div class="col-sm-3">
            <div class="thumbnail">
                <img id="srcw" src="${path}/${list.foodSrc}" class="img-circle" style="width: 200px;height: 120px;">
                <div class="caption">
                    <h3 class="text-center">${list.foodName}</h3>
                    <p>${list.fileFood}</p>
                    <p><a href="${path}/food/selectByIdEs?id=${list.id}" class="btn btn-danger btn-block" role="button">查看详细</a></p>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
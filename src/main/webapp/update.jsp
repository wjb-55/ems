<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="utf-8" contentType="text/html; utf-8" isELIgnored="false"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>后台修改页面</title>
    <link rel="stylesheet" href="${path}/static/css/bootstrap.min.css"/>
    <script src="../static/js/jquery-3.5.1.min.js"></script>
</head>
<body>



<div class="container-fluid">
    <div class="row">
        <div class="col-sm-12">
            <form method="post" enctype="multipart/form-data" action="${path}/food/update?id=${list.id}">
                <div class="form-group">
                    <label class="col-sm-2 control-label">ID</label>
                    <div class="col-sm-10">
                        <p class="form-control-static">${list.id}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label >菜谱名称:</label>
                    <input type="text" value="${list.foodName}" name="foodName" class="form-control" >
                </div>
                <div class="form-group">
                    <label >菜谱图片:</label>
                    <input type="file" class="form-control" name="photos">
                </div>
                <div class="form-group">
                    <label >录入日期:</label>
                    <input type="date" class="form-control"   name="foodDate">
                </div>
                <div class="form-group">
                    <label >录入人:</label>
                    <input type="text" class="form-control" value="${list.person}" name="person">
                </div>

                <div class="form-group">
                    <label >菜谱关于:</label>
                    <input type="text" value="${list.fileFood}" name="fileFood" class="form-control" >
                </div>

                <div class="form-group">
                    <label >烹饪步骤:</label>
                    <input type="text" value="${list.foodImporter}" name="foodImporter" class="form-control" >
                    </textarea>
                </div>
                <button type="submit" class="btn btn-info btn-block">录入菜谱</button>
                <a href="${path}/food/all" type="button" class="btn btn-danger btn-block">返回列表</a>
            </form>
        </div>
    </div>

</div>

</body>
</html>
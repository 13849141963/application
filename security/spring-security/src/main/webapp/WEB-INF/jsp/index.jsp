<%--
  Created by IntelliJ IDEA.
  User: Apple
  Date: 2019/10/24
  Time: 下午8:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    欢迎用户：${username}
    <h2>以下是网站的功能</h2><br/>
    <%--拥有该权限就可以访问--%>
    <security:authorize access="hasAuthority('ROLE_ADD_PRODUCT')">
    <a href="${pageContext.request.contextPath}/product/add">添加商品</a><br/>
    </security:authorize>

    <security:authorize access="hasAuthority('ROLE_UPDATE_PRODUCT')">
    <a href="${pageContext.request.contextPath}/product/update">修改商品</a><br/>
    </security:authorize>

    <security:authorize access="hasAuthority('ROLE_LIST_PRODUCT')">
    <a href="${pageContext.request.contextPath}/product/list">查询商品</a><br/>
    </security:authorize>

    <security:authorize access="hasAuthority('ROLE_DELETE_PRODUCT')">
    <a href="${pageContext.request.contextPath}/product/delete">删除商品</a><br/>
    </security:authorize>
</body>
</html>

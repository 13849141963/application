<%--
  Created by IntelliJ IDEA.
  User: Apple
  Date: 2019/10/24
  Time: 下午8:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <title>Title</title>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    </head>
    <body>
        <h1>登陆页面</h1><br/>
        <c:if test="${not empty param.error}">
            <font color="red">用户名或密码错误～～</font>
        </c:if>
        <form method="post"  id="loginForm">
            <input type="text" name="username" value="zs"/><br/>
            <input type="password" name="password" value="123"/><br/>
            <input type="text" name="imageCode" value="验证码"/><br/>
            <%--默认记住我前端name值为remember-me value为true--%>
            记住我<input type="checkbox" name="remember-me" value="true"/><br/>
            <input type="button" id="loginBtn" value="登陆"/>
        </form>
    </body>
    <script>
        $(function () {
            $.ajax({
                //请求方式
                type : "POST",
                //请求的媒体类型
                contentType: "application/json;charset=UTF-8",
                //请求地址
                url : "${pageContext.request.contextPath}/imageCode",
                //请求成功
                success : function(result) {
                    console.log(result);
                },
                //请求失败，包含具体的错误信息
                error : function(e){
                    console.log(e.status);
                    console.log(e.responseText);
                }
            })


            $("#loginBtn").click(function () {
                $.post("${pageContext.request.contextPath}/login",$("#loginForm").serialize(),function (data) {
                    // 判断是否登陆成功
                    if(data.success){
                        window.location.href = "${pageContext.request.contextPath}/product/index";
                    }else {
                        alert(data.msg)
                    }
                },"json")
            })
        })
    </script>
</html>

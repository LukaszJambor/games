<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head></head>
<body>
<form:form method="post" modelAttribute="userData">
    <table>
        <tr>
            <td>
                <form:label path="login">Login</form:label>
            </td>
            <td>
                <form:input path="login"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="password">Password</form:label>
            </td>
            <td>
                <form:password path="password"/>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
<c:if test="${not empty error}">
    <c:forEach var="msg" items="${error}">
        <p1>${msg.defaultMessage}<p1>
    </c:forEach>
</c:if>
</body>
</html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head></head>
<body>
<form:form method="post" modelAttribute="userEntity">
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
        <tr><td><input type="submit" value="Submit"/></td></tr>
    </table>
</form:form>
</body>
</html>
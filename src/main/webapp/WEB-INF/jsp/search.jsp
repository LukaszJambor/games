<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head></head>
<body>
<form:form method="post" modelAttribute="queryData">
    <table>
        <tr>
            <td><form:label path="name">Tytuł</form:label></td>
            <td><form:input path="name"/></td>
        </tr>
        <tr>
            <td><form:label path="producer">Producent</form:label></td>
            <td><form:input path="producer"/></td>
        </tr>
    </table>
    <tr><td><input type="submit" value="Submit"/></td></tr>
</form:form>
</body>
</html>
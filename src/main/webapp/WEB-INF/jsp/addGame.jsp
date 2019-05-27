<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head></head>
<body>
<form:form method="post" modelAttribute="gameData">
    <table>
        <tr>
            <td><form:label path="name">Tytuł</form:label></td>
            <td><form:input path="name"/></td>
        </tr>
        <tr>
            <td><form:label path="type">Gatunek</form:label></td>
            <td><form:input path="type"/></td>
        </tr>
        <tr>
            <td><form:label path="producer">Producent</form:label></td>
            <td><form:input path="producer"/></td>
        </tr>
        <tr>
            <td><form:label path="distributionPath">Sposób dystrybucji</form:label></td>
            <td><form:input path="distributionPath"/></td>
        </tr>
        <tr><td><input type="submit" value="Submit"/></td></tr>
    </table>
</form:form>
</body>
</html>
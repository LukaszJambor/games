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
            <td><form:label path="producerName">Producent</form:label></td>
            <td><form:input path="producerName"/></td>
        </tr>
        <tr>
            <td><form:label path="distributionPath">Sposób dystrybucji</form:label></td>
            <td><form:input path="distributionPath"/></td>
        </tr>
        <tr>
            <td><form:label path="price">Cena</form:label></td>
            <td><form:input path="price"/></td>
        </tr>
        <tr>
            <td><form:label path="currency">Waluta</form:label></td>
            <td><form:input path="currency"/></td>
        </tr>
        <tr>
            <td><form:label path="quantity">Ilość</form:label></td>
            <td><form:input path="quantity"/></td>
        </tr>
        <tr><td><input type="submit" value="Submit"/></td></tr>
    </table>
</form:form>
</body>
</html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head></head>
<body>
<table>
<c:forEach items="${games}" var="game">
    <tr>
        <td>${game.id}</td>
        <td>${game.name}</td>
        <td>${game.type}</td>
        <td>${game.producer}</td>
        <td>${game.distributionPath}</td>
    </tr>
</c:forEach>
</table>
<br>
<a href="<c:url value="/addGame"/>">add</a>
<br>
<a href="<c:url value="/search"/>">search</a>
</body>
</html>
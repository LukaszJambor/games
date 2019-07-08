<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head></head>
<body>
<table>
    <c:forEach items="${games}" var="game">
        <tr>
            <td>${game.id}</td>
            <td>${game.name}</td>
            <td>${game.type}</td>
            <td>${game.producerName}</td>
            <td>${game.distributionPath}</td>
            <td>${game.price}</td>
            <td>${game.currency}</td>
            <td>${game.quantity}</td>
            <c:if test="${not empty userId and game.quantity>0}">
                <td><a href="<c:url value="/user/${userId}/lend/${game.id}"/>">Pożycz</a></td>
            </c:if>
        </tr>
    </c:forEach>
</table>
<p1>${notAvailable}</p1>
<p1>${doubleLend}</p1>
<br>
<a href="<c:url value="/addGame"/>">add</a>
<br>
<a href="<c:url value="/search"/>">search</a>
</body>
</html>
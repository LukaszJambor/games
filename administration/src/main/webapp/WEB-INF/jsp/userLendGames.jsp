<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head></head>
<body>
<table>
    <c:forEach items="${games}" var="game">
        <c:if test="${empty game.lendEndDate}">
        <tr>
            <td>${game.title}</td>
            <td>${game.lendStartDate}</td>
                <td><a href="<c:url value="/user/${userId}/return/${game.gameId}"/>">Zwróć</a></td>
        </tr>
        </c:if>
    </c:forEach>
    <br>
</table>
<p1>archiwum</p1>
<table>
    <c:forEach items="${games}" var="game">
        <c:if test="${not empty game.lendEndDate}">
            <tr>
                <td>${game.title}</td>
                <td>${game.lendStartDate}</td>
                <td>${game.lendEndDate}</td>
            </tr>
        </c:if>
    </c:forEach>
</table>
</body>
</html>
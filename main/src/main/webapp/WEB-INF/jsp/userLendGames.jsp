<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
<c:if test="${gameId != null}">
    <form:form action="/user/${userId}/game/${gameId}/addComment" method="post" modelAttribute="commentData">
        <table>
            <tr>
                <td><form:label path="comment">Komentarz</form:label></td>
                <td><form:input path="comment"/></td>
            </tr>
        </table>
        <tr><td><input type="submit" value="Submit"/></td></tr>
    </form:form>
</c:if>
</body>
</html>
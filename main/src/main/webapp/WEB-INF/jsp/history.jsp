<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head></head>
<body>
<table>
    <c:forEach items="${payments}" var="payment">
        <tr>
            <td>${payment.paymentTime}</td>
            <td>${payment.cost}</td>
            <td>${payment.gameName}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
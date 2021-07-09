<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${id==null}">
        <ul class="grid-inline paginator">
        <c:forEach var="page" begin="1" end="${pages}">
            <li>
                <a href="./${param.risorsa}?page=${page}">${page}</a>
            </li>
        </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
        <ul class="grid-inline paginator">
            <c:forEach var="page" begin="1" end="${pages}">
                <li>
                    <a href="./${param.risorsa}?id=${id}&page=${page}">${page}</a>
                </li>
            </c:forEach>
        </ul>
    </c:otherwise>
</c:choose>
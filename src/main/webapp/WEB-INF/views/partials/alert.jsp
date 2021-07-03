<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    $(document).ready(function(){
        $(".close").children("svg").click(function(){
            $(".notification").remove();
        });
    });
</script>
<div class="notification ${alert.type} cell w50">
        <ol class="cell">
            <c:forEach var="msg" items="${alert.messages}">
                <li>${msg}</li>
            </c:forEach>
        </ol>
    <span id="notification-close" class="close">
        <%@include file="../../../icons/x.svg"%>
    </span>
</div>
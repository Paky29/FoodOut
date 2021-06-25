<header class="topbar grid-x align-center">
    <%@include file="../../../../icons/menu.svg"%>
    <label class="field command">
        <input type="text" placeholder="Cerca comandi">
    </label>
    <span class="account" style="color: white">
        <%@include file="../../../../icons/user.svg"%> <%--cambiare con icona user--%>
        Benvenuto, ${utenteSession.nome}
    </span>
</header>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
<title>${param.title}</title>
<meta name="description" content="delivery">
<link rel="icon" type="image/png" href="images/logo.png">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone-no">
<meta name="apple-mobile-web-app-title" content="FoodOut">
<meta name="apple-mobile-web-app-status-bar-style" content="default">
<link rel="apple-touch-icon" href="images/logo.png">
<link rel="apple-touch-startup-image" href="images/logo.png">
<meta name="theme-color" content="#000000">
<link href="css/reset.css" rel="stylesheet">
<link href="css/library.css" rel="stylesheet">
<c:if test="${not empty param.style}">
    <link rel="stylesheet" href="css/${param.style}">
</c:if>
<script src="js/library.js" defer></script>
<c:if test="${not empty param.script}">
    <script src="js/${param.script}"></script>
</c:if>
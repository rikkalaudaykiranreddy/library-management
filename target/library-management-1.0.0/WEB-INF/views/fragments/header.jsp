<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Library Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css"/>
</head>
<body>

<!-- ── Navbar ──────────────────────────────────────────────── -->
<nav class="navbar">
    <a href="${pageContext.request.contextPath}/" class="navbar-brand">
        <span class="brand-icon">📚</span>
        LibraryMS
    </a>
    <ul class="navbar-nav">
        <li><a href="${pageContext.request.contextPath}/books/catalog"
               class="${pageTitle == 'Book Catalog' ? 'active' : ''}">📖 Catalog</a></li>
        <li><a href="${pageContext.request.contextPath}/books"
               class="${pageTitle == 'Books' ? 'active' : ''}">📋 Books</a></li>
        <li><a href="${pageContext.request.contextPath}/authors"
               class="${pageTitle == 'Authors' ? 'active' : ''}">✍️ Authors</a></li>
        <li><a href="${pageContext.request.contextPath}/books/add">➕ Add Book</a></li>
        <li><a href="${pageContext.request.contextPath}/authors/add">➕ Add Author</a></li>
    </ul>
</nav>

<!-- ── Flash Messages ──────────────────────────────────────── -->
<div style="max-width:1200px;margin:0 auto;padding:0 1.5rem;">
    <c:if test="${not empty successMsg}">
        <div class="alert alert-success" style="margin-top:1.25rem;">
            ✅ ${successMsg}
        </div>
    </c:if>
    <c:if test="${not empty errorMsg}">
        <div class="alert alert-danger" style="margin-top:1.25rem;">
            ❌ ${errorMsg}
        </div>
    </c:if>
</div>

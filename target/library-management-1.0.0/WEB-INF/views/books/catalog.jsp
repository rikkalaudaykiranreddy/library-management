<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content="Full book catalog with author details — Library Management System"/>
    <title>Book Catalog | LibraryMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css"/>
</head>
<body>

<%@ include file="../fragments/header.jsp" %>

<div class="page-wrapper">

    <!-- Stats -->
    <div class="stats-row">
        <div class="stat-card">
            <div class="stat-icon purple">📚</div>
            <div>
                <div class="stat-value">${catalog.size()}</div>
                <div class="stat-label">Total Books</div>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-icon gold">✍️</div>
            <div>
                <div class="stat-value">
                    <c:set var="unique" value="0"/>
                    ${catalog.stream().map(b -> b.authorName).distinct().count()}
                </div>
                <div class="stat-label">Authors</div>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-icon green">🆕</div>
            <div>
                <div class="stat-value">
                    <c:if test="${not empty catalog}">${catalog[0].bookTitle}</c:if>
                    <c:if test="${empty catalog}">—</c:if>
                </div>
                <div class="stat-label">Latest Added</div>
            </div>
        </div>
    </div>

    <!-- Header -->
    <div class="page-header">
        <div>
            <h1 class="page-title">📖 Book Catalog</h1>
            <p class="page-subtitle">All books with their authors — newest entries first</p>
        </div>
        <div style="display:flex;gap:0.75rem;">
            <a href="${pageContext.request.contextPath}/books/add" class="btn btn-primary">➕ Add Book</a>
            <a href="${pageContext.request.contextPath}/books" class="btn btn-outline">📋 Books Only</a>
        </div>
    </div>

    <!-- Table -->
    <c:choose>
        <c:when test="${empty catalog}">
            <div class="empty-state">
                <div class="empty-icon">📭</div>
                <p>No books found in the catalog.</p>
                <a href="${pageContext.request.contextPath}/books/add" class="btn btn-primary">Add First Book</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Title</th>
                            <th>Genre</th>
                            <th>Year</th>
                            <th>Price</th>
                            <th>ISBN</th>
                            <th>Author</th>
                            <th>Nationality</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="row" items="${catalog}" varStatus="s">
                            <tr>
                                <td style="color:var(--text-secondary);font-size:0.8rem;">${s.index + 1}</td>
                                <td>
                                    <strong>${row.bookTitle}</strong>
                                    <c:if test="${s.index == 0}">
                                        <span class="badge badge-new" style="margin-left:0.4rem;">NEW</span>
                                    </c:if>
                                </td>
                                <td><span class="badge badge-genre">${row.genre}</span></td>
                                <td>${row.publicationYear}</td>
                                <td style="color:var(--gold);">$<fmt:formatNumber value="${row.price}" pattern="#,##0.00"/></td>
                                <td style="font-size:0.8rem;color:var(--text-secondary);">${row.isbn}</td>
                                <td><strong>${row.authorName}</strong></td>
                                <td style="color:var(--text-secondary);">${row.nationality}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/books/edit/${row.bookId}"
                                       class="btn btn-outline btn-sm">✏️ Edit</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="../fragments/footer.jsp" %>

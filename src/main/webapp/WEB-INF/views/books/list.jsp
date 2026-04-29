<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content="All books in the library — Library Management System"/>
    <title>Books | LibraryMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css"/>
</head>
<body>

<%@ include file="../fragments/header.jsp" %>

<div class="page-wrapper">

    <div class="page-header">
        <div>
            <h1 class="page-title">📋 All Books</h1>
            <p class="page-subtitle">Newest books appear at the top</p>
        </div>
        <a href="${pageContext.request.contextPath}/books/add" class="btn btn-primary">➕ Add New Book</a>
    </div>

    <c:choose>
        <c:when test="${empty books}">
            <div class="empty-state">
                <div class="empty-icon">📭</div>
                <p>No books found. Start building your library!</p>
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
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="book" items="${books}" varStatus="s">
                            <tr>
                                <td style="color:var(--text-secondary);font-size:0.8rem;">${s.index + 1}</td>
                                <td>
                                    <strong>${book.title}</strong>
                                    <c:if test="${s.index == 0}">
                                        <span class="badge badge-new" style="margin-left:0.4rem;">NEW</span>
                                    </c:if>
                                </td>
                                <td><span class="badge badge-genre">${book.genre}</span></td>
                                <td>${book.publicationYear}</td>
                                <td style="color:var(--gold);">$<fmt:formatNumber value="${book.price}" pattern="#,##0.00"/></td>
                                <td style="font-size:0.8rem;color:var(--text-secondary);">${book.isbn}</td>
                                <td>${book.author.name}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/books/edit/${book.id}"
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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content="${pageTitle} — Library Management System"/>
    <title>${pageTitle} | LibraryMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css"/>
</head>
<body>

<%@ include file="../fragments/header.jsp" %>

<div class="page-wrapper">

    <div class="page-header">
        <div>
            <h1 class="page-title">
                <c:choose>
                    <c:when test="${book.id != null}">✏️ Edit Book</c:when>
                    <c:otherwise>➕ Add New Book</c:otherwise>
                </c:choose>
            </h1>
            <p class="page-subtitle">
                <c:choose>
                    <c:when test="${book.id != null}">Update the book details below</c:when>
                    <c:otherwise>Fill in the details to add a new book to the library</c:otherwise>
                </c:choose>
            </p>
        </div>
        <a href="${pageContext.request.contextPath}/books" class="btn btn-outline">← Back to Books</a>
    </div>

    <div class="form-card">
        <c:choose>
            <%-- ── EDIT ──────────────────────────────────────────────── --%>
            <c:when test="${book.id != null}">
                <form:form action="${pageContext.request.contextPath}/books/update/${book.id}"
                           method="post" modelAttribute="book">
                    <div class="form-grid">
                        <div class="form-group full-width">
                            <label class="form-label" for="title">Book Title *</label>
                            <form:input path="title" id="title" class="form-control" placeholder="e.g. The Great Gatsby"/>
                            <form:errors path="title" cssClass="form-error"/>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="genre">Genre *</label>
                            <form:input path="genre" id="genre" class="form-control" placeholder="e.g. Fiction"/>
                            <form:errors path="genre" cssClass="form-error"/>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="publicationYear">Publication Year *</label>
                            <form:input path="publicationYear" id="publicationYear" type="number"
                                        class="form-control" placeholder="e.g. 2023"/>
                            <form:errors path="publicationYear" cssClass="form-error"/>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="price">Price ($) *</label>
                            <form:input path="price" id="price" type="number" step="0.01"
                                        class="form-control" placeholder="e.g. 12.99"/>
                            <form:errors path="price" cssClass="form-error"/>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="isbn">ISBN</label>
                            <form:input path="isbn" id="isbn" class="form-control" placeholder="e.g. 978-0000000000"/>
                            <form:errors path="isbn" cssClass="form-error"/>
                        </div>
                        <div class="form-group full-width">
                            <label class="form-label" for="authorId">Author *</label>
                            <select name="authorId" id="authorId" class="form-control" required>
                                <option value="">-- Select Author --</option>
                                <c:forEach var="a" items="${authors}">
                                    <option value="${a.id}"
                                        ${a.id == selectedAuthorId ? 'selected' : ''}>${a.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-actions">
                        <a href="${pageContext.request.contextPath}/books" class="btn btn-outline">Cancel</a>
                        <button type="submit" class="btn btn-primary">💾 Update Book</button>
                    </div>
                </form:form>
            </c:when>

            <%-- ── ADD ──────────────────────────────────────────────── --%>
            <c:otherwise>
                <form:form action="${pageContext.request.contextPath}/books/save"
                           method="post" modelAttribute="book">
                    <div class="form-grid">
                        <div class="form-group full-width">
                            <label class="form-label" for="title">Book Title *</label>
                            <form:input path="title" id="title" class="form-control" placeholder="e.g. The Great Gatsby"/>
                            <form:errors path="title" cssClass="form-error"/>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="genre">Genre *</label>
                            <form:input path="genre" id="genre" class="form-control" placeholder="e.g. Fiction"/>
                            <form:errors path="genre" cssClass="form-error"/>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="publicationYear">Publication Year *</label>
                            <form:input path="publicationYear" id="publicationYear" type="number"
                                        class="form-control" placeholder="e.g. 2023"/>
                            <form:errors path="publicationYear" cssClass="form-error"/>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="price">Price ($) *</label>
                            <form:input path="price" id="price" type="number" step="0.01"
                                        class="form-control" placeholder="e.g. 12.99"/>
                            <form:errors path="price" cssClass="form-error"/>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="isbn">ISBN</label>
                            <form:input path="isbn" id="isbn" class="form-control" placeholder="e.g. 978-0000000000"/>
                            <form:errors path="isbn" cssClass="form-error"/>
                        </div>
                        <div class="form-group full-width">
                            <label class="form-label" for="authorId">Author *</label>
                            <select name="authorId" id="authorId" class="form-control" required>
                                <option value="">-- Select Author --</option>
                                <c:forEach var="a" items="${authors}">
                                    <option value="${a.id}">${a.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-actions">
                        <a href="${pageContext.request.contextPath}/books" class="btn btn-outline">Cancel</a>
                        <button type="submit" class="btn btn-primary">➕ Add Book</button>
                    </div>
                </form:form>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="../fragments/footer.jsp" %>

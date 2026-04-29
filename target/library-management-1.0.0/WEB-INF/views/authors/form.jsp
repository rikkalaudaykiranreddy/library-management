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
                    <c:when test="${author.id != null}">✏️ Edit Author</c:when>
                    <c:otherwise>➕ Add New Author</c:otherwise>
                </c:choose>
            </h1>
            <p class="page-subtitle">
                <c:choose>
                    <c:when test="${author.id != null}">Update the author's information below</c:when>
                    <c:otherwise>Fill in the details to add a new author</c:otherwise>
                </c:choose>
            </p>
        </div>
        <a href="${pageContext.request.contextPath}/authors" class="btn btn-outline">← Back to Authors</a>
    </div>

    <div class="form-card">
        <c:choose>
            <%-- ── EDIT ─────────────────────────────────── --%>
            <c:when test="${author.id != null}">
                <form:form action="${pageContext.request.contextPath}/authors/update/${author.id}"
                           method="post" modelAttribute="author">
                    <div class="form-grid">
                        <div class="form-group full-width">
                            <label class="form-label" for="name">Full Name *</label>
                            <form:input path="name" id="name" class="form-control"
                                        placeholder="e.g. Ernest Hemingway"/>
                            <form:errors path="name" cssClass="form-error"/>
                        </div>
                        <div class="form-group full-width">
                            <label class="form-label" for="nationality">Nationality</label>
                            <form:input path="nationality" id="nationality" class="form-control"
                                        placeholder="e.g. American"/>
                            <form:errors path="nationality" cssClass="form-error"/>
                        </div>
                        <div class="form-group full-width">
                            <label class="form-label" for="bio">Biography</label>
                            <form:textarea path="bio" id="bio" class="form-control" rows="4"
                                           placeholder="A short biography of the author..."/>
                            <form:errors path="bio" cssClass="form-error"/>
                        </div>
                    </div>
                    <div class="form-actions">
                        <a href="${pageContext.request.contextPath}/authors" class="btn btn-outline">Cancel</a>
                        <button type="submit" class="btn btn-primary">💾 Update Author</button>
                    </div>
                </form:form>
            </c:when>

            <%-- ── ADD ─────────────────────────────────── --%>
            <c:otherwise>
                <form:form action="${pageContext.request.contextPath}/authors/save"
                           method="post" modelAttribute="author">
                    <div class="form-grid">
                        <div class="form-group full-width">
                            <label class="form-label" for="name">Full Name *</label>
                            <form:input path="name" id="name" class="form-control"
                                        placeholder="e.g. Ernest Hemingway"/>
                            <form:errors path="name" cssClass="form-error"/>
                        </div>
                        <div class="form-group full-width">
                            <label class="form-label" for="nationality">Nationality</label>
                            <form:input path="nationality" id="nationality" class="form-control"
                                        placeholder="e.g. American"/>
                            <form:errors path="nationality" cssClass="form-error"/>
                        </div>
                        <div class="form-group full-width">
                            <label class="form-label" for="bio">Biography</label>
                            <form:textarea path="bio" id="bio" class="form-control" rows="4"
                                           placeholder="A short biography of the author..."/>
                            <form:errors path="bio" cssClass="form-error"/>
                        </div>
                    </div>
                    <div class="form-actions">
                        <a href="${pageContext.request.contextPath}/authors" class="btn btn-outline">Cancel</a>
                        <button type="submit" class="btn btn-primary">➕ Add Author</button>
                    </div>
                </form:form>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="../fragments/footer.jsp" %>

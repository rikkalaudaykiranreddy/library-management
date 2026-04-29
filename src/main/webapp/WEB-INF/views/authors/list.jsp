<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content="All authors — Library Management System"/>
    <title>Authors | LibraryMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css"/>
</head>
<body>

<%@ include file="../fragments/header.jsp" %>

<div class="page-wrapper">

    <div class="page-header">
        <div>
            <h1 class="page-title">✍️ All Authors</h1>
            <p class="page-subtitle">Newest authors appear at the top</p>
        </div>
        <a href="${pageContext.request.contextPath}/authors/add" class="btn btn-primary">➕ Add New Author</a>
    </div>

    <c:choose>
        <c:when test="${empty authors}">
            <div class="empty-state">
                <div class="empty-icon">🧑‍🎨</div>
                <p>No authors yet. Add the first one!</p>
                <a href="${pageContext.request.contextPath}/authors/add" class="btn btn-primary">Add Author</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Nationality</th>
                            <th>Bio</th>
                            <th>Books</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="author" items="${authors}" varStatus="s">
                            <tr>
                                <td style="color:var(--text-secondary);font-size:0.8rem;">${s.index + 1}</td>
                                <td>
                                    <strong>${author.name}</strong>
                                    <c:if test="${s.index == 0}">
                                        <span class="badge badge-new" style="margin-left:0.4rem;">NEW</span>
                                    </c:if>
                                </td>
                                <td><span class="badge badge-genre">${author.nationality}</span></td>
                                <td style="max-width:320px;color:var(--text-secondary);font-size:0.83rem;">
                                    ${author.bio}
                                </td>
                                <td style="text-align:center;">
                                    <span style="background:rgba(108,99,255,0.15);
                                                 color:var(--accent-light);
                                                 padding:0.2rem 0.6rem;
                                                 border-radius:999px;
                                                 font-size:0.82rem;
                                                 font-weight:600;">
                                        ${author.books.size()}
                                    </span>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/authors/edit/${author.id}"
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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tt" uri="time-tracker-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n.messages"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@include file="/WEB-INF/fragments/navbar.jspf" %>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h1 class="display-4">
                <span><fmt:message key="users.profile.welcome"/></span>
                <span>${requestScope.user.firstName}</span>
                <a href="${pageContext.request.contextPath}/app/profile/update"
                   class="btn btn-primary float-right">
                    <img src="${pageContext.request.contextPath}/images/outline-edit.png" alt="edit user">
                </a>
            </h1>
            <hr>
        </div>
        <div class="card-body">
            <div class="card-text">
                <span><fmt:message key="users.profile.first_name"/></span>
                <span>${requestScope.user.firstName}</span>
            </div>
            <hr>
            <div class="card-text">
                <span><fmt:message key="users.profile.last_name"/></span>
                <span>${requestScope.user.lastName}</span>
            </div>
            <hr>
            <div class="card-text">
                <span><fmt:message key="users.profile.username"/></span>
                <span>${requestScope.user.username}</span>
            </div>
            <hr>
            <div class="card-text">
                <c:if test="${requestScope.user.activities.isEmpty()}">
                    <span><fmt:message key="users.profile.activities.empty"/></span>
                </c:if>
                <c:if test="${!requestScope.user.activities.isEmpty()}">
                    <table class="table table-sm table-bordered table-striped">
                        <caption style="caption-side: top">
                            <fmt:message key="users.profile.activities.caption"/>
                        </caption>
                        <thead>
                        <tr>
                            <th><fmt:message key="users.profile.activities.name"/></th>
                            <th><fmt:message key="users.profile.activities.status"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.user.activities}" var="activity">
                            <tr>
                                <td>${activity.name}</td>
                                <td>${activity.status}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
            <div class="card-text">
                <c:if test="${requestScope.user.activityRequests.isEmpty()}">
                    <span><fmt:message key="users.profile.activity.requests.empty"/></span>
                </c:if>
                <c:if test="${!requestScope.user.activityRequests.isEmpty()}">
                    <table class="table table-sm table-bordered table-striped">
                        <caption style="caption-side: top">
                            <fmt:message key="users.profile.activity.requests.caption"/>
                        </caption>
                        <thead>
                        <tr>
                            <th><fmt:message key="users.profile.activity.requests.activity_name"/></th>
                            <th><fmt:message key="users.profile.activity.requests.action"/></th>
                            <th><fmt:message key="users.profile.activity.requests.status"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.user.activityRequests}" var="activityRequest">
                            <tr>
                                <td>${activityRequest.activity.name}</td>
                                <td>${activityRequest.action}</td>
                                <td>${activityRequest.status}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/fragments/footer.jspf" %>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
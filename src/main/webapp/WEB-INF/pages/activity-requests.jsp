<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tt" uri="time-tracker-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n.messages"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Activity requests</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@include file="/WEB-INF/fragments/navbar.jspf" %>

<div class="container">
    <div class="card">
        <div class="card-header">
            <div>
                <c:if test="${requestScope.activityRequests.isEmpty()}">
                    <h1 class="display-4"><fmt:message key="activity.request.empty"/></h1>
                    <hr>
                </c:if>
                <c:if test="${!requestScope.activityRequests.isEmpty()}">
                    <h1 class="display-4"><fmt:message key="activity.request.title"/></h1>
                    <hr>
                    <%@include file="/WEB-INF/fragments/activity-requests-paginator.jspf" %>
                </c:if>
            </div>
        </div>
        <div class="card-body">
            <c:if test="${!requestScope.activityRequests.isEmpty()}">
                <div class="row">
                    <c:forEach items="${requestScope.activityRequests}" var="activityRequest">
                        <div class="col-md-3">
                            <div class="card mb-4 shadow-sm">
                                <div class="card-header">
                                    <p>${activityRequest.activity.name}</p>
                                    <p>${activityRequest.user.username}</p>
                                    <p>
                                        <fmt:parseDate value="${ activityRequest.requestDate }"
                                                       pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                                        <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
                                    </p>
                                </div>
                                <div class="card-body">
                                    <p>
                                        <span><fmt:message key="activity.request.id"/></span>
                                        <span>${activityRequest.id}</span>
                                    </p>
                                    <p>
                                        <span><fmt:message key="activity.request.activity_id"/></span>
                                        <span>${activityRequest.activity.id}</span>
                                    </p>
                                    <p>
                                        <span><fmt:message key="activity.request.user_id"/></span>
                                        <span>${activityRequest.user.id}</span>
                                    </p>
                                    <p>
                                        <span><fmt:message key="activity.request.action"/></span>
                                        <span>${activityRequest.action.toString()}</span>
                                    </p>
                                    <p>
                                        <span><fmt:message key="activity.request.status"/></span>
                                        <span>${activityRequest.status.toString()}</span>
                                    </p>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div class="btn-group">
                                            <a class="btn btn-sm btn-secondary"
                                               href="${pageContext.request.contextPath}/app/activities/request/approve?id=${activityRequest.id}"
                                            >
                                                <fmt:message key="activity.request.button.approve"/>
                                            </a>
                                            <a class="btn btn-sm btn-secondary"
                                               href="${pageContext.request.contextPath}/app/activities/request/reject?id=${activityRequest.id}"
                                            >
                                                <fmt:message key="activity.request.button.reject"/>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
        <div class="card-footer">
            <%@include file="/WEB-INF/fragments/activity-requests-paginator.jspf" %>
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

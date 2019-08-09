<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tt" uri="time-tracker-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n.messages"/>

<html lang="en">
<head>
    <meta charset="utf-8">

    <title>Activities</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@include file="/WEB-INF/fragments/navbar.jspf" %>

<div class="container">
    <div class="card">
        <div class="card-header">
            <c:if test="${!requestScope.activites.isEmpty()}">
                <h1 class="display-4"><fmt:message key="activities.title"/></h1>
                <hr>
                <%@include file="/WEB-INF/fragments/activities-paginator.jspf" %>
            </c:if>
            <c:if test="${requestScope.activites.isEmpty()}">
                <h1 class="display-4"><fmt:message key="activities.empty"/></h1>
                <hr>
            </c:if>
        </div>
        <div class="card-body">
            <div class="row">
                <c:forEach items="${requestScope.activities}" var="activity">
                    <div class="col-md-6">
                        <div class="card mb-3 shadow-sm">
                            <div class="card-header">
                                <p>${activity.name}</p>
                            </div>
                            <div class="card-body">
                                <p>${activity.description}</p>
                            </div>
                            <div class="card-footer">
                                <p>
                                    <span><fmt:message key="activities.activity.id"/></span>
                                    <span>${activity.id}</span>
                                </p>
                                <p>
                                    <span><fmt:message key="activities.activity.importance"/></span>
                                    <span>${activity.importance.toString()}</span>
                                </p>
                                <p>
                                    <span><fmt:message key="activities.activity.start_time"/></span>
                                    <span>
                                        <fmt:parseDate value="${activity.startTime}" pattern="yyyy-MM-dd'T'HH:mm"
                                                       var="parsedDateTime" type="both"/>
                                        <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
                                    </span>
                                </p>
                                <p>
                                    <span><fmt:message key="activities.activity.end_time"/></span>
                                    <span>
                                        <fmt:parseDate value="${activity.endTime}" pattern="yyyy-MM-dd'T'HH:mm"
                                                       var="parsedDateTime" type="both"/>
                                        <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
                                    </span>
                                </p>
                                <p>
                                    <span><fmt:message key="activities.activity.duration"/></span>
                                    <span>
                                            ${activity.duration.toDaysPart()}
                                    </span>
                                    <span><fmt:message key="activities.activity.duration.days"/></span>
                                    <span>
                                            ${activity.duration.toHoursPart()}
                                    </span>
                                    <span><fmt:message key="activities.activity.duration.hours"/></span>
                                    <span>
                                            ${activity.duration.toMinutesPart()}
                                    </span>
                                    <span><fmt:message key="activities.activity.duration.minutes"/></span>
                                </p>
                                <p>
                                    <span><fmt:message key="activities.activity.users"/></span>
                                    <c:forEach items="${activity.users}" var="user">
                                        <span>${user.username}</span><span>;</span>
                                    </c:forEach>
                                </p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <div aria-label="btn-group" class="btn-group" role="group">
                                        <tt:hasAuthority authority="ADMIN">
                                            <a class="btn btn-sm btn-secondary"
                                               href="${pageContext.request.contextPath}/app/activities/delete?id=${activity.id}">
                                                <fmt:message key="activities.activity.button.delete"/>
                                            </a>
                                        </tt:hasAuthority>
                                        <a class="btn btn-sm btn-secondary"
                                           href="${pageContext.request.contextPath}/app/activities/request/add?id=${activity.id}">
                                            <fmt:message key="activities.activity.button.request_to_add"/>
                                        </a>
                                        <a class="btn btn-sm btn-secondary"
                                           href="${pageContext.request.contextPath}/app/activities/request/complete?id=${activity.id}">
                                            <fmt:message key="activities.activity.button.request_to_complete"/>
                                        </a>
                                        <button type="button" class="btn btn-sm btn-primary" data-toggle="modal"
                                                data-target="#timeSpentModal${activity.id}">
                                            <fmt:message key="activities.activity.button.mark_time_spent"/>
                                        </button>
                                    </div>
                                </div>
                                <!--Start modal window-->
                                <div aria-hidden="true" aria-labelledby="timeSpentModalTitle" class="modal fade"
                                     id="timeSpentModal${activity.id}" role="dialog" tabindex="-1">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="timeSpentModalTitle">
                                                    <fmt:message key="activities.modal.title"/>
                                                </h5>
                                                <button aria-label="Close" class="close" data-dismiss="modal"
                                                        type="button">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <form method="post"
                                                  action="${pageContext.request.contextPath}/app/activities/mark-time?id=${activity.id}">
                                                <div class="modal-body">
                                                    <div class="input-group mb-3">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text">
                                                                <fmt:message key="activities.modal.days"/>
                                                            </span>
                                                        </div>
                                                        <input aria-label="days" class="form-control"
                                                               name="days"
                                                               min="0"
                                                               required
                                                               type="number">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text">
                                                                <fmt:message key="activities.modal.hours"/>
                                                            </span>
                                                        </div>
                                                        <input aria-label="hours" class="form-control"
                                                               name="hours"
                                                               min="0"
                                                               required
                                                               type="number">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text">
                                                                <fmt:message key="activities.modal.minutes"/>
                                                            </span>
                                                        </div>
                                                        <input aria-label="minutes" class="form-control"
                                                               name="minutes"
                                                               min="0"
                                                               required
                                                               type="number">
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button class="btn btn-secondary" data-dismiss="modal"
                                                            type="button">
                                                        <fmt:message key="activities.modal.button.close"/>
                                                    </button>
                                                    <button class="btn btn-primary" type="submit">
                                                        <fmt:message key="activities.modal.button.confirm"/>
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <!--End modal window-->
                                <small>
                                    <span><fmt:message key="activities.activity.status"/></span>
                                    <span>
                                            ${activity.status.toString()}
                                    </span>
                                </small>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="card-footer">
            <%@include file="/WEB-INF/fragments/activities-paginator.jspf" %>
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

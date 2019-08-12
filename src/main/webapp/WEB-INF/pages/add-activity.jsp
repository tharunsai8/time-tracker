<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tt" uri="time-tracker-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n.messages"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add new activity</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@include file="/WEB-INF/fragments/navbar.jspf" %>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h1 class="display-4"><fmt:message key="activities.add.title"/></h1>
            <hr>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <form method="post" action="${pageContext.request.contextPath}/app/activities/add">
                        <div class="row">
                            <div class="form-group col-md-6">
                                <label class="col-form-label" for="name">
                                    <fmt:message key="activities.add.activity_name.label"/>
                                </label>
                                <input class="form-control"
                                       id="name"
                                       name="name"
                                       value="${requestScope.activity.name}"
                                       placeholder="<fmt:message key="activities.add.activity_name.placeholder"/>"
                                       type="text"
                                       required/>
                                <span class="text-danger">
                                    <c:forEach items="${requestScope.errors.nameErrors}" var="error">
                                        ${error}<br>
                                    </c:forEach>
                                </span>
                            </div>
                            <div class="form-group col-md-12">
                                <label class="col-form-label" for="description">
                                    <fmt:message key="activities.add.description.label"/>
                                </label>
                                <textarea id="description" rows="4"
                                          name="description"
                                          placeholder="<fmt:message key="activities.add.description.placeholder"/>"
                                          class="form-control">${requestScope.activity.description}</textarea>
                                <span class="text-danger">
                                    <c:forEach items="${requestScope.errors.descriptionErrors}" var="error">
                                        ${error}<br>
                                    </c:forEach>
                                </span>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-form-label" for="importanceLevel">
                                    <fmt:message key="activities.add.importance.label"/>
                                </label>
                                <select name="importance" class="form-control" id="importanceLevel">
                                    <c:forEach items="${requestScope.importanceLevels}" var="importance">
                                        <option value="${importance.name()}">
                                                ${importance.toString()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mt-1">
                                <input class="btn btn-primary" value="<fmt:message key="activities.add.button.submit"/>"
                                       type="submit">
                            </div>
                        </div>
                    </form>
                </div>
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
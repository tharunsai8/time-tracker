<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tt" uri="time-tracker-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n.messages"/>

<html lang="en">
<head>
    <meta charset="UTF-8">

    <title>Registration form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@include file="WEB-INF/fragments/navbar.jspf" %>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h1 class="display-4">
                <fmt:message key="users.update.title"/>
            </h1>
            <hr>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <form action="${pageContext.request.contextPath}/app/users/update?id=${requestScope.user.id}"
                          method="post">
                        <div class="row">
                            <div class="form-group col-md-6">
                                <label class="col-form-label" for="firstName">
                                    <fmt:message key="users.update.first_name.label"/>
                                </label>
                                <input type="text"
                                       name="firstName"
                                       value="${requestScope.user.firstName}"
                                       id="firstName"
                                       class="form-control"
                                       placeholder="<fmt:message key="users.update.first_name.placeholder"/>">
                                <span class="text-danger">
                                    <c:forEach items="${requestScope.errors.firstNameErrors}" var="error">
                                        ${error}<br>
                                    </c:forEach>
                                </span>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-form-label" for="lastName">
                                    <fmt:message key="users.update.last_name.label"/>
                                </label>
                                <input type="text"
                                       name="lastName"
                                       value="${requestScope.user.lastName}"
                                       id="lastName"
                                       class="form-control"
                                       placeholder="<fmt:message key="users.update.last_name.placeholder"/>">
                                <span class="text-danger">
                                    <c:forEach items="${requestScope.errors.lastNameErrors}" var="error">
                                        ${error}<br>
                                    </c:forEach>
                                </span>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-form-label" for="username">
                                    <fmt:message key="users.update.username.label"/>
                                </label>
                                <input type="text"
                                       name="username"
                                       value="${requestScope.user.username}"
                                       id="username"
                                       class="form-control"
                                       placeholder="<fmt:message key="users.update.username.placeholder"/>">
                                <span class="text-danger">
                                    <c:forEach items="${requestScope.errors.usernameErrors}" var="error">
                                        ${error}<br>
                                    </c:forEach>
                                    ${requestScope.usernameUniqueError}
                                </span>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-form-label" for="password">
                                    <fmt:message key="users.update.password.label"/>
                                </label>
                                <input type="password"
                                       name="password"
                                       id="password"
                                       class="form-control"
                                       placeholder="<fmt:message key="users.update.password.placeholder"/>">
                                <span class="text-danger">
                                    <c:forEach items="${requestScope.errors.passwordErrors}" var="error">
                                        ${error}<br>
                                    </c:forEach>
                                </span>
                            </div>
                            <div class="form-group form-check col-md-6">
                                <ul>
                                    <c:forEach items="${requestScope.authorities}" var="authority">
                                        <li>
                                            <input type="checkbox"
                                                   class="form-check-input"
                                                   name="authorities"
                                                    <c:if test="${requestScope.user.getAuthorities().contains(authority)}">
                                                        checked
                                                    </c:if>
                                                   value="${authority.name()}"
                                                   id="authority${authority.ordinal()}"/>
                                            <label for="authority${authority.ordinal()}" class="form-check-label">
                                                    ${authority}
                                            </label>
                                        </li>
                                    </c:forEach>
                                    <span class="text-danger">
                                        <c:forEach items="${requestScope.errors.authoritiesErrors}" var="error">
                                            ${error}<br>
                                        </c:forEach>
                                    </span>
                                </ul>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <input class="btn btn-primary"
                                       value="<fmt:message key="users.update.button.submit"/>"
                                       type="submit">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="WEB-INF/fragments/footer.jspf" %>

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

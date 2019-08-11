<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tt" uri="time-tracker-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n.messages"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signin.css">
</head>
<body class="text-center">
<form class="form-signin" action="${pageContext.request.contextPath}/app/login" method="post">
    <a href="${pageContext.request.contextPath}/app/index">
        <img class="mb-4" src="${pageContext.request.contextPath}/images/stopwatch-black.png" width="100" height="100"
             alt="icon">
    </a>
    <h1 class="h3 mb-3 font-weight-normal">
        <fmt:message key="login.header"/>
    </h1>
    <c:if test="${requestScope.success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <span>
                <fmt:message key="login.logout.success"/>
            </span>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>
    <c:if test="${requestScope.error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <span>
                <fmt:message key="login.error"/>
            </span>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>

    <label id="usernameLabel" for="username" class="sr-only">
        <fmt:message key="login.label.username"/>
    </label>
    <input type="text"
           id="username"
           name="username"
           class="form-control"
           placeholder="<fmt:message key="login.input.username.placeholder"/>"
           required
           autofocus
    >
    <label id="passwordLabel" for="password" class="sr-only">
        placeholder="<fmt:message key="login.label.password"/>"
    </label>
    <input type="password"
           name="password"
           id="password"
           placeholder="<fmt:message key="login.input.password.placeholder"/>"
           required
           class="form-control"
    >
    <button class="btn btn-lg btn-primary btn-block" type="submit">
        <fmt:message key="button.signin"/>
    </button>
    <div class="card" style="margin-top: 20px">
        <div class="card-body">
            <p class="card-text">
                <span>
                    <fmt:message key="login.new"/>
                </span>
                <a href="${pageContext.request.contextPath}/app/registration" class="memberNameLink">
                    <fmt:message key="login.new.create"/>
                </a>
            </p>
        </div>
    </div>
</form>

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
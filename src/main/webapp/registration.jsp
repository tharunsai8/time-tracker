<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n.messages"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/app/index">
        <img src="${pageContext.request.contextPath}/images/stopwatch-white.png" class="d-inline-block align-top" width="30" height="30">
        Time-Tracker
    </a>
    <div class="collapse navbar-collapse" id="navbars">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/app/index">
                    <fmt:message key="link.home"/>
                </a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/app/users">
                    <fmt:message key="link.all_users"/>
                </a>
            </li>
            <li class="nav-item dropdown active">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Activities
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/app/activities">All activities</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/app/activities/add">Add activity</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/app/activities/request">All activity requests</a>
                </div>
            </li>
        </ul>
        <div class="form-inline my-2 my-lg-0">
            <form action="${pageContext.request.contextPath}/app/registration" method="get">
                <button type="submit" class="btn btn-outline-primary mr-3 my-2 my-sm-0">
                    <fmt:message key="button.signup"/>
                </button>
            </form>
            <form action="${pageContext.request.contextPath}/app/login" method="get">
                <button type="submit" class="btn btn-primary mr-3 my-2 my-sm-0">
                    <fmt:message key="button.signin"/>
                </button>
            </form>
            <form action="${pageContext.request.contextPath}/app/logout" method="get">
                <button type="submit" class="btn btn-primary my-2 my-sm-0">
                    <fmt:message key="button.signout"/>
                </button>
            </form>
        </div>
    </div>
</nav>

<div class="container">
    <div class="card">
        <div class="card-header">
            <fmt:message key="registration.header"/>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/app/registration" method="post">
                <div class="form-row">
                    <div class="col-md-3 mb-3">
                        <label id="firstNameLabel" for="firstName">
                            <fmt:message key="registration.first_name.label"/>
                        </label>
                        <input type="text"
                               name="firstName"
                               id="firstName"
                               placeholder="<fmt:message key="registration.first_name.placeholder"/>"
                               class="form-control"
                               required
                        />
                    </div>
                    <div class="col-md-3 mb-3">
                        <label for="lastName">
                            <fmt:message key="registration.last_name.label"/>
                        </label>
                        <input type="text"
                               name="lastName"
                               id="lastName"
                               placeholder="<fmt:message key="registration.last_name.placeholder"/>"
                               class="form-control"
                               required
                        />
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-3 mb-3">
                        <label id="usernameLabel" for="username">
                            <fmt:message key="registration.username.label"/>
                        </label>
                        <input type="text"
                               name="username"
                               id="username"
                               placeholder="<fmt:message key="registration.username.placeholder"/>"
                               class="form-control"
                               required
                        />
                    </div>
                    <div class="col-md-3 mb-3">
                        <label for="password">
                            <fmt:message key="registration.password.label"/>
                        </label>
                        <input type="password"
                               name="password"
                               id="password"
                               placeholder="<fmt:message key="registration.password.placeholder"/>"
                               class="form-control"
                               required
                        />
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">
                    <fmt:message key="button.signup"/>
                </button>
            </form>
        </div>
        <div id="resultMessage" class="card-footer">
            ${resultMessage}
        </div>
    </div>
</div>

<footer class="container">
    <p>
    <span>
      <fmt:message key="footer.logged_user"/>
    </span>
        <span class="mr-4">
            ${sessionScope.user.getUsername()}
        </span>
        <span>
      <fmt:message key="footer.roles"/>
    </span>
        <span class="mr-4">
            ${sessionScope.user.getRole()}
        </span>
    </p>
    <span style="float: right">
    <a href="?lang=en">
      <fmt:message key="lang.en"/>
    </a>
    |
    <a href="?lang=ua">
      <fmt:message key="lang.ua"/>
    </a>
</span>
</footer>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>
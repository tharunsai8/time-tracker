<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en"/>
<fmt:setBundle basename="i18n.messages"/>

<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Time tracker</title>

  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
  <a class="navbar-brand" href="/index">
    <img src="${pageContext.request.contextPath}/images/stopwatch-white.svg" class="d-inline-block align-top" width="30" height="30">
    Time-Tracker
  </a>
  <div class="collapse navbar-collapse" id="navbars">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="/index">
          <fmt:message key="link.home"/>
        </a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="/users">
          <fmt:message key="link.all_users"/>
        </a>
      </li>
      <li class="nav-item dropdown active">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Activities
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="/activities">All activities</a>
          <a class="dropdown-item" href="/activities/add">Add activity</a>
          <a class="dropdown-item" href="/activities/request">All activity requests</a>
        </div>
      </li>
    </ul>
    <div class="form-inline my-2 my-lg-0">
      <form action="${pageContext.request.contextPath}/registration" method="get">
        <button type="submit" class="btn btn-outline-primary mr-3 my-2 my-sm-0">
          <fmt:message key="button.signup"/>
        </button>
      </form>
      <form action="${pageContext.request.contextPath}/login" method="get">
        <button type="submit" class="btn btn-primary mr-3 my-2 my-sm-0">
          <fmt:message key="button.signin"/>
        </button>
      </form>
      <form action="/logout" method="get">
        <button type="submit" class="btn btn-primary my-2 my-sm-0">
          <fmt:message key="button.signout"/>
        </button>
      </form>
    </div>
  </div>
</nav>

<main role="main">
  <div class="jumbotron">
    <div class="container">
      <h1 class="display-4">
        <fmt:message key="index.main.name"/>
      </h1>
      <p>
        <fmt:message key="index.main.desc"/>
      </p>
    </div>
  </div>

  <div class="container">
    <div class="row">
      <div class="col-md-4">
        <h3>
          <fmt:message key="index.secondary.column1.name"/>

        </h3>
        <p>
          <fmt:message key="index.secondary.column1.desc"/>
        </p>
      </div>
      <div class="col-md-4">
        <h3>
          <fmt:message key="index.secondary.column2.name"/>
        </h3>
        <p>
          <fmt:message key="index.secondary.column2.desc"/>
        </p>
      </div>
      <div class="col-md-4">
        <h3>
          <fmt:message key="index.secondary.column3.name"/>
        </h3>
        <p>
          <fmt:message key="index.secondary.column3.desc"/>
        </p>
      </div>
    </div>
    <hr>
  </div>
</main>

<footer class="container">
  <p>
    <span>
      <fmt:message key="footer.logged_user"/>
    </span>
    <span class="mr-4">
      Roles
    </span>
    <span>
      <fmt:message key="footer.roles"/>
    </span>
    <span class="mr-4">
      Roles
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

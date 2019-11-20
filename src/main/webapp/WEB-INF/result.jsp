<%@ page import="java.util.List" %>
<%@ page import="ru.itpark.implementation.model.Auto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Search Result</title>
    <%@ include file="bootstrap.jsp" %>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <h1>Search Results</h1>

            <div class="row">
                <% if (request.getAttribute("searchResult") != null) { %>
                <% for (Auto item : (List<Auto>) request.getAttribute("searchResult")) { %>
                <div class="col-sm-6 mt-3">
                    <div class="card">
                        <img src="<%= request.getContextPath() %>/images/<%= item.getImageUrl() %>"
                             class="card-img-top" alt="imagePlaceholder">
                        <div class="card-body">
                            <h5 class="card-title"><%= item.getName() %>
                            </h5>
                            <p class="card-text"><%= item.getDescription()%>
                            </p>
                            <a href="<%= request.getContextPath() %>/<%= item.getId() %>" class="btn btn-primary">Details</a>
                        </div>
                    </div>
                </div>
                <% } %>
                <% } %>
            </div>

        </div>
    </div>
</div>

</body>
</html>

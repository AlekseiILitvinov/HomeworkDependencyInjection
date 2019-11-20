<%@ page import="ru.itpark.implementation.model.Auto" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <%@ include file="bootstrap.jsp" %>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <h1>Details</h1>

            <div class="row">
                <% if (request.getAttribute("item") != null) { %>
                <% Auto item = (Auto) request.getAttribute("item"); %>
                <div class="col-sm-6 mt-3">
                    <div class="card">
                        <img src="<%= request.getContextPath() %>/images/<%= item.getImageUrl() %>" class="card-img-top" alt="imagePlaceholder">
                        <div class="card-body">
                            <h5 class="card-title"><%= item.getName() %></h5>
                            <p class="card-text"><%= item.getDescription()%></p>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
        </div>
    </div>
</div>


</body>
</html>
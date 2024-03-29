<%@ page import="ru.itpark.implementation.model.Auto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <h1>Catalog</h1>

            <form class="mt-3" action="<%= request.getContextPath() %>search">
                <input name="name" class="form-control" type="search" placeholder="Search">
            </form>


            <div class="row">
                <% if (request.getAttribute("items") != null) { %>
                <% for (Auto item : (List<Auto>)request.getAttribute("items")) { %>
                <div class="col-sm-6 mt-3">
                    <div class="card">
                        <img src="<%= request.getContextPath() %>/images/<%= item.getImageUrl() %>" class="card-img-top" alt="imagePlaceholder">
                        <div class="card-body">
                            <h5 class="card-title"><%= item.getName() %></h5>
                            <p class="card-text"><%= item.getDescription()%></p>
                            <a href="<%= request.getContextPath() %>/<%= item.getId() %>" class="btn btn-primary">Details</a>
                        </div>
                    </div>
                </div>
                <% } %>
                <% } %>
            </div>


            <form class="mt-3" action="<%= request.getContextPath() %>/" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="name">Auto Name</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Auto name" required>
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea name="description" class="form-control" id="description" placeholder="Auto description" required></textarea>
                </div>

                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="file" name="file" accept="image/*" required>
                    <label class="custom-file-label" for="file">Choose file...</label>
                </div>

                <button type="submit" class="btn btn-primary mt-2">Create</button>
            </form>
        </div>
    </div>
</div>


</body>
</html>
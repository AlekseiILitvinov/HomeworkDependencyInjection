package ru.itpark.implementation.router;

import lombok.AllArgsConstructor;
import ru.itpark.framework.annotation.Component;
import ru.itpark.implementation.controller.AutoController;
import ru.itpark.framework.router.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
@AllArgsConstructor
public class RouterImpl implements Router {
    private final AutoController autoController;

    @Override
    public Object route(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        if (request.getMethod().equals("GET")) {
            try {
                routeGet(request, response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else if (request.getMethod().equals("POST")) {
            try {
                autoController.handlePost(request);
                response.sendRedirect(request.getServletPath());
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private Object routeGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String requestURI = request.getRequestURI();
        if (requestURI.equals("/")) {
            request.setAttribute("items", autoController.getAll());
            request.getRequestDispatcher("/WEB-INF/catalog.jsp").forward(request, response);
        } else if (requestURI.startsWith("/images")) {
            final String id = requestURI.substring("/images/".length());
            autoController.getFile(id, response.getOutputStream());
        } else if (requestURI.startsWith("/search")) {
            String query = request.getQueryString();
            String name = query.substring("name=".length());
            request.setAttribute("searchResult", autoController.doSearch(name));
            request.getRequestDispatcher("/WEB-INF/result.jsp").forward(request, response);
        } else if (requestURI.startsWith("/")) {
            final Integer id = Integer.parseInt(requestURI.substring(1));
            request.setAttribute("item", autoController.getById(id));
            request.getRequestDispatcher("/WEB-INF/details.jsp").forward(request, response);
        } else {
            System.out.println("in routeGet");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
        return null;
    }
}


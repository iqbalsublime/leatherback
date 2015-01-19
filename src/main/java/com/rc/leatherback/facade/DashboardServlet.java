package com.rc.leatherback.facade;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DashboardServlet", urlPatterns = "/dashboard", loadOnStartup = 1)
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = -5844083487692950518L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message", "Welcome");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/dashboard/dashboard.jsp");
        requestDispatcher.forward(req, resp);
    }
}

package com.rc.leatherback.facade;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.rc.leatherback.exception.AuthenticatedFailedException;
import com.rc.leatherback.service.LoginService;

@WebServlet(name = "loginServlet", urlPatterns = "/login", loadOnStartup = 1)
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = -473880976809068336L;

    private LoginService loginService;

    public LoginServlet() {
        this.loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/login/form.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            try {
                if (loginService.call(username, password)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("user", loginService.getLoggedInUser());
                    // setting session to expiry in 30 mins
                    // session.setMaxInactiveInterval(30 * 60);

                    resp.sendRedirect("/dashboard");
                } else {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/login/form.jsp");
                    PrintWriter out = resp.getWriter();
                    out.println("<font color=red>Either user name or password is wrong.</font>");
                    rd.include(req, resp);
                }
            } catch (ClassNotFoundException | SQLException | AuthenticatedFailedException exception) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/login/form.jsp");
                PrintWriter out = resp.getWriter();
                out.println("<font color=red>Either user name or password is wrong.</font>");
                rd.include(req, resp);
            }
        }


    }
}

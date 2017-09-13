package it.unibz.prowd;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by e7250 on 7/24/2017.
 */
@WebServlet("/profile")
public class ViewProfileServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String plch = request.getParameter("id");
    System.out.println("user wants to see " + plch);
    request.setAttribute("profile", plch);
    request.getRequestDispatcher("profile.jsp").forward(request, response);
  }
}

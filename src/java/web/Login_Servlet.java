package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Login_Servlet")
public class Login_Servlet extends HttpServlet {

    protected void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("Username");
        String password = request.getParameter("Password");

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Users WHERE Userame = ? AND Password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                String role = results.getString("Role");
                int userId = results.getInt("User_ID");
                String fullName = results.getString("Full_Name"); 

                HttpSession session = request.getSession();
                session.setAttribute("UserId", userId);
                session.setAttribute("Username", username);
                session.setAttribute("Role", role);
                session.setAttribute("UserFullName", fullName); 

                if (role.equals("STUDENT")) {
                    response.sendRedirect("StudentPortalServlet");
                } else if (role.equals("INSTRUCTOR")) {
                    response.sendRedirect("InstructorPortalServlet");
                } else {
                    response.sendRedirect("AdminPortalServlet");
                }

            } else {
                response.sendRedirect("login.jsp?error=1");
            }
        } catch (SQLException excp) {
            excp.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h3 style='color:red;'>Database error: " + excp.getMessage() + "</h3>");
            out.println("</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleLogin(request, response);
    }
}

package web.Admin;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import web.Database_Conn;

@WebServlet("/AdminPortalServlet")
public class AdminPortalServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || !"ADMIN".equals(session.getAttribute("Role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection conn = Database_Conn.getConnection()) {

          
            List<String> instructors = new ArrayList<>();
            String sql1 = "SELECT Full_Name FROM Users WHERE Role = 'INSTRUCTOR'";
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(sql1);
            while (rs1.next()) {
                instructors.add(rs1.getString("Full_Name"));
            }

            
            List<String> students = new ArrayList<>();
            String sql2 = "SELECT Full_Name FROM Users WHERE Role = 'STUDENT'";
            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery(sql2);
            while (rs2.next()) {
                students.add(rs2.getString("Full_Name"));
            }
            
            List<String> courses = new ArrayList<>();
            String sql3 = "SELECT Course_Name FROM Courses";
            Statement stmt3 = conn.createStatement();
            ResultSet rs3 = stmt3.executeQuery(sql3);
            while (rs3.next()) {
                courses.add(rs3.getString("Course_Name"));
            }

            request.setAttribute("instructors", instructors);
            request.setAttribute("students", students);
            request.setAttribute("courses", courses);
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_portal.jsp");
            dispatcher.forward(request, response);

        } 
        catch (SQLException excp) {
            excp.printStackTrace();
            response.sendRedirect("login.jsp?error=2");
        }
    }
}

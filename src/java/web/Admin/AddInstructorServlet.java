package web.Admin;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import web.DatabaseConnection;

@WebServlet("/AddInstructorServlet")
public class AddInstructorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || !"ADMIN".equals(session.getAttribute("Role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Map<String, String>> courseList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT Course_Num, Course_Name FROM Courses";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);

            while (result.next()) {
                Map<String, String> course = new HashMap<>();
                course.put("id", String.valueOf(result.getInt("Course_Num")));
                course.put("name", result.getString("Course_Name"));
                courseList.add(course);
            }

            request.setAttribute("courseList", courseList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin/add_instructor.jsp");
            dispatcher.forward(request, response);

        } 
        catch (SQLException excp) {
            excp.printStackTrace();
            response.sendRedirect("admin/admin_portal.jsp?error=1");
        }
    }
}

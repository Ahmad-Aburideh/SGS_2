package web.Admin;

import web.Database_Conn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/AddCourseServlet")
public class AddCourseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String courseName = request.getParameter("courseName");

        try (Connection conn = Database_Conn.getConnection()) {

            // Check if course already exists
            String checkSql = "SELECT * FROM Courses WHERE Course_Name = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, courseName);
            ResultSet result = checkStmt.executeQuery();

            if (result.next()) {
                // Course already exists
                response.sendRedirect("admin/add_course.jsp?error=duplicate");
                return;
            }

            // Insert new course
            String insertSql = "INSERT INTO Courses (Course_Name) VALUES (?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, courseName);
            insertStmt.executeUpdate();

            response.sendRedirect("admin/admin_portal.jsp");

        } 
        catch (SQLException excp) {
            excp.printStackTrace();
            response.sendRedirect("admin/add_course.jsp?error=db");
        }
    }
}

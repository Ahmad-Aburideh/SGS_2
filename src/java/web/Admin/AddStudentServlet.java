package web.Admin;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import web.Database_Conn;

@WebServlet("/AddStudentServlet")
public class AddStudentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = Database_Conn.getConnection()) {
            String sql = "INSERT INTO Users (Userame, Password, Full_Name, Role) VALUES (?, ?, ?, 'STUDENT')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, fullName);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                response.sendRedirect("admin/admin_portal.jsp?success=student_added");
            } 
            else {
                response.sendRedirect("admin/add_student.jsp?error=insert_failed");
            }

        } 
        catch (SQLException excp) {
            excp.printStackTrace();
            response.sendRedirect("admin/add_student.jsp?error=sql_exception");
        }
    }
}

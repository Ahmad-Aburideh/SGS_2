package web.Instructor;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import web.DatabaseConnection;

@WebServlet("/EditGradeServlet")
public class EditGradeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String enrollmentIdStr = request.getParameter("enrollmentId");
        String newGradeStr = request.getParameter("newGrade");

        try {
            int enrollmentId = Integer.parseInt(enrollmentIdStr);
            double newGrade = Double.parseDouble(newGradeStr);

            try (Connection conn = DatabaseConnection.getConnection()) {
                String updateSql = "UPDATE registrations SET Grade = ? WHERE Enrollment_ID = ?";
                PreparedStatement stmt = conn.prepareStatement(updateSql);
                stmt.setDouble(1, newGrade);
                stmt.setInt(2, enrollmentId);
                stmt.executeUpdate();
            }

            
            response.sendRedirect("InstructorPortalServlet");

        } catch (NumberFormatException | SQLException excp) {
            excp.printStackTrace();
            response.sendRedirect("instructor/instructor_portal.jsp?error=1");
        }
    }
}

package web.Admin;

import web.Database_Conn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/EnrollStudentServlet")
public class EnrollStudentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String studentName = request.getParameter("student");
        String instructorCourseIdStr = request.getParameter("instructorCourse");

        try (Connection conn = Database_Conn.getConnection()) {

            // Get Student ID
            String studentQuery = "SELECT User_ID FROM Users WHERE Full_Name = ?";
            PreparedStatement studentStmt = conn.prepareStatement(studentQuery);
            studentStmt.setString(1, studentName);
            ResultSet studentRs = studentStmt.executeQuery();

            int studentId = -1;
            if (studentRs.next()) {
                studentId = studentRs.getInt("User_ID");
            }

            int instructorCourseId = Integer.parseInt(instructorCourseIdStr);

            // Get Course_Num linked with Instructor_Course_ID
            String courseNumQuery = "SELECT Course_Num FROM instructor_course WHERE ID = ?";
            PreparedStatement courseNumStmt = conn.prepareStatement(courseNumQuery);
            courseNumStmt.setInt(1, instructorCourseId);
            ResultSet courseNumRs = courseNumStmt.executeQuery();

            int courseNum = -1;
            if (courseNumRs.next()) {
                courseNum = courseNumRs.getInt("Course_Num");
            }

            // Check if student is already registered for the same Course_Num with any instructor
            String checkQuery = """
                SELECT 1 FROM registrations r
                JOIN instructor_course ic ON r.Instructor_Course_ID = ic.ID
                WHERE r.Student_ID = ? AND ic.Course_Num = ?
            """;
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, studentId);
            checkStmt.setInt(2, courseNum);
            ResultSet checkRs = checkStmt.executeQuery();

            if (checkRs.next()) {
                // Already registered – redirect with error
                response.sendRedirect("LoadEnrollmentDataServlet?error=duplicate");
                return;
            }

            // Insert registration
            String enrollQuery = "INSERT INTO registrations (Student_ID, Instructor_Course_ID, Grade) VALUES (?, ?, 0)";
            PreparedStatement enrollStmt = conn.prepareStatement(enrollQuery);
            enrollStmt.setInt(1, studentId);
            enrollStmt.setInt(2, instructorCourseId);
            enrollStmt.executeUpdate();

            response.sendRedirect("admin/admin_portal.jsp");

        } catch (SQLException | NumberFormatException excp) {
            excp.printStackTrace();
            response.sendRedirect("admin/admin_portal.jsp?error=1");
        }
    }
}

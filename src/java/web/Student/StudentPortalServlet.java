package web.Student;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import web.DatabaseConnection;

@WebServlet("/StudentPortalServlet")
public class StudentPortalServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || !"STUDENT".equals(session.getAttribute("Role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        int studentId = (int) session.getAttribute("UserId");
        List<StudentGrade> grades = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = """
                SELECT c.Course_Name, r.Grade, u.Full_Name AS Instructor_Name
                FROM registrations r
                JOIN instructor_course ic ON r.Instructor_Course_ID = ic.ID
                JOIN courses c ON ic.Course_Num = c.Course_Num
                JOIN users u ON ic.Instructor_ID = u.User_ID
                WHERE r.Student_ID = ?
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                String course = result.getString("Course_Name");
                double grade = result.getDouble("Grade");
                String instructor = result.getString("Instructor_Name");

                grades.add(new StudentGrade(course, grade, instructor));
            }

            request.setAttribute("grades", grades);
            RequestDispatcher dispatcher = request.getRequestDispatcher("student/student_portal.jsp");
            dispatcher.forward(request, response);

        }
        catch (SQLException excp) {
            excp.printStackTrace();
            response.sendRedirect("login.jsp?error=2");
        }
    }
}

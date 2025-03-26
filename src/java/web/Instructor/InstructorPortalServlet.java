package web.Instructor;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import web.Database_Conn;

@WebServlet("/InstructorPortalServlet")
public class InstructorPortalServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || !"INSTRUCTOR".equals(session.getAttribute("Role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        int instructorId = (int) session.getAttribute("UserId");
        List<InstructorGrade> gradeList = new ArrayList<>();
        List<Map<String, String>> instructorCourses = new ArrayList<>();

        try (Connection conn = Database_Conn.getConnection()) {
            // Load student grades
            String sql = """
                SELECT r.Enrollment_ID, u.Full_Name AS StudentName, c.Course_Name, ic.Section, r.Grade
                FROM registrations r
                JOIN users u ON r.Student_ID = u.User_ID
                JOIN instructor_course ic ON r.Instructor_Course_ID = ic.ID
                JOIN courses c ON ic.Course_Num = c.Course_Num
                WHERE ic.Instructor_ID = ?
                """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, instructorId);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                int enrollmentId = result.getInt("Enrollment_ID");
                String course = result.getString("Course_Name");
                String student = result.getString("StudentName");
                double grade = result.getDouble("Grade");
                String section = result.getString("Section");
                InstructorGrade row = new InstructorGrade(enrollmentId, course, student, section, grade);
                gradeList.add(row);
            }

            // Load courses for dropdown list (for statistics)
            String courseSql = """
                SELECT ic.ID AS InstructorCourseID, c.Course_Name, ic.Section
                FROM instructor_course ic
                JOIN courses c ON ic.Course_Num = c.Course_Num
                WHERE ic.Instructor_ID = ?
                """;

            PreparedStatement courseStmt = conn.prepareStatement(courseSql);
            courseStmt.setInt(1, instructorId);
            ResultSet courseRs = courseStmt.executeQuery();

            while (courseRs.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("InstructorCourseID", String.valueOf(courseRs.getInt("InstructorCourseID")));
                map.put("CourseName", courseRs.getString("Course_Name"));
                map.put("Section", courseRs.getString("Section"));
                instructorCourses.add(map);
            }

            request.setAttribute("grades", gradeList);
            request.setAttribute("instructorCourses", instructorCourses);
            RequestDispatcher dispatcher = request.getRequestDispatcher("instructor/instructor_portal.jsp");
            dispatcher.forward(request, response);

        } 
        catch (SQLException excp) {
            excp.printStackTrace();
            response.sendRedirect("login.jsp?error=2");
        }
    }
}

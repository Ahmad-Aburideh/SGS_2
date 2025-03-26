package web.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import web.Database_Conn;

@WebServlet("/LoadEnrollmentDataServlet")
public class LoadEnrollmentDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || !"ADMIN".equals(session.getAttribute("Role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        String error = request.getParameter("error");
        if (error != null) {
            request.setAttribute("error", error);
        }

        List<String> students = new ArrayList<>();
        List<Map<String, String>> instructorCourses = new ArrayList<>();

        try (Connection conn = Database_Conn.getConnection()) {

            // Fetch students
            String studentSql = "SELECT Full_Name FROM Users WHERE Role = 'STUDENT'";
            PreparedStatement studentStmt = conn.prepareStatement(studentSql);
            ResultSet studentRs = studentStmt.executeQuery();
            while (studentRs.next()) {
                students.add(studentRs.getString("Full_Name"));
            }

            // Fetch instructor_course entries
            String icSql = """
                SELECT ic.ID AS Instructor_Course_ID, u.Full_Name AS InstructorName, c.Course_Name
                FROM instructor_course ic
                JOIN Users u ON ic.Instructor_ID = u.User_ID
                JOIN Courses c ON ic.Course_Num = c.Course_Num
            """;

            PreparedStatement icStmt = conn.prepareStatement(icSql);
            ResultSet icRs = icStmt.executeQuery();
            while (icRs.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("Instructor_Course_ID", String.valueOf(icRs.getInt("Instructor_Course_ID")));
                map.put("Instructor_Name", icRs.getString("InstructorName"));
                map.put("Course_Name", icRs.getString("Course_Name"));
                instructorCourses.add(map);
            }

            session.setAttribute("students", students);
            session.setAttribute("instructorCourses", instructorCourses);

            RequestDispatcher dispatcher = request.getRequestDispatcher("admin/enroll_student.jsp");
            dispatcher.forward(request, response);

        } 
        catch (SQLException excp) {
            excp.printStackTrace();
            response.sendRedirect("admin/admin_portal.jsp?error=db");
        }
    }
}

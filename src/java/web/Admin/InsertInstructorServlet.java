package web.Admin;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import web.DatabaseConnection;

@WebServlet("/InsertInstructorServlet")
public class InsertInstructorServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String[] selectedCourses = request.getParameterValues("courses");

        if (selectedCourses == null || selectedCourses.length == 0) {
            request.setAttribute("error", "noCourse");
            request.setAttribute("courseList", loadCourses());
            request.getRequestDispatcher("admin/add_instructor.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if instructor with the same full name exists
            String checkSql = "SELECT * FROM Users WHERE Full_Name = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, fullName);
            ResultSet checkResult = checkStmt.executeQuery();

            if (checkResult.next()) {
                request.setAttribute("error", "duplicate");
                request.setAttribute("courseList", loadCourses());
                request.getRequestDispatcher("admin/add_instructor.jsp").forward(request, response);
                return;
            }

            // Insert instructor
            String insertInstructor = "INSERT INTO Users (Full_Name, Userame, Password, Role) VALUES (?, ?, ?, 'INSTRUCTOR')";
            PreparedStatement stmt = conn.prepareStatement(insertInstructor, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, fullName);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int instructorId = -1;
            if (generatedKeys.next()) {
                instructorId = generatedKeys.getInt(1);
            }

            String insertRelation = "INSERT INTO instructor_course (Instructor_ID, Course_Num) VALUES (?, ?)";
            PreparedStatement relationStmt = conn.prepareStatement(insertRelation);

            for (String courseNumStr : selectedCourses) {
                int courseNum = Integer.parseInt(courseNumStr);
                relationStmt.setInt(1, instructorId);
                relationStmt.setInt(2, courseNum);
                relationStmt.executeUpdate();
            }

            response.sendRedirect("admin/admin_portal.jsp");

        } catch (SQLException excp) {
            excp.printStackTrace();
            request.setAttribute("error", "unexpected");
            request.setAttribute("courseList", loadCourses());
            request.getRequestDispatcher("admin/add_instructor.jsp").forward(request, response);
        }
    }

    private List<Map<String, String>> loadCourses() {
        List<Map<String, String>> courseList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT Course_Num, Course_Name FROM Courses";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, String> course = new HashMap<>();
                course.put("id", String.valueOf(rs.getInt("Course_Num")));
                course.put("name", rs.getString("Course_Name"));
                courseList.add(course);
            }
        } 
        catch (SQLException excp) {
            excp.printStackTrace();
        }
        return courseList;
    }
}

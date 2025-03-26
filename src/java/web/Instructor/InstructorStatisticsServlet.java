package web.Instructor;

import web.Database_Conn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/InstructorStatisticsServlet")
public class InstructorStatisticsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || !"INSTRUCTOR".equals(session.getAttribute("Role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        int instructorId = (int) session.getAttribute("UserId");
        Map<String, Map<String, Double>> courseStats = new LinkedHashMap<>();

        try (Connection conn = Database_Conn.getConnection()) {
            String sql = """
                SELECT c.Course_Name, r.Grade
                FROM registrations r
                JOIN instructor_course ic ON r.Instructor_Course_ID = ic.ID
                JOIN courses c ON ic.Course_Num = c.Course_Num
                WHERE ic.Instructor_ID = ? AND r.Grade IS NOT NULL
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, instructorId);
            ResultSet rs = stmt.executeQuery();

            Map<String, List<Double>> courseGrades = new HashMap<>();

            while (rs.next()) {
                String course = rs.getString("Course_Name");
                double grade = rs.getDouble("Grade");
                if (course != null) {
                    courseGrades.computeIfAbsent(course, k -> new ArrayList<>()).add(grade);
                }
            }

            for (Map.Entry<String, List<Double>> entry : courseGrades.entrySet()) {
                String course = entry.getKey();
                List<Double> grades = entry.getValue();
                Collections.sort(grades);

                double avg = grades.stream().mapToDouble(Double::doubleValue).average().orElse(0);
                double median = grades.size() % 2 == 0
                        ? (grades.get(grades.size()/2 - 1) + grades.get(grades.size()/2)) / 2
                        : grades.get(grades.size()/2);
                double max = Collections.max(grades);
                double min = Collections.min(grades);

                Map<String, Double> stats = new LinkedHashMap<>();
                stats.put("Average", avg);
                stats.put("Median", median);
                stats.put("Max", max);
                stats.put("Min", min);

                courseStats.put(course, stats);
            }

            request.setAttribute("statistics", courseStats);
            RequestDispatcher dispatcher = request.getRequestDispatcher("instructor/instructor_statistics.jsp");
            dispatcher.forward(request, response);

        } 
        catch (SQLException excp) {
            excp.printStackTrace();
            response.sendRedirect("InstructorPortalServlet?error=db");
        }
    }
}

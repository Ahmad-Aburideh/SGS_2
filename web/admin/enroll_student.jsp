<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Enroll Student in Course</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fdf8e1;
            padding: 40px;
        }
        h2 {
            color: #ffcc00;
            text-align: center;
        }
        form {
            max-width: 400px;
            margin: auto;
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px #ccc;
        }
        label, select {
            display: block;
            margin-bottom: 15px;
            font-weight: bold;
        }
        select, input[type="submit"] {
            width: 100%;
            padding: 8px;
            font-size: 14px;
        }
        input[type="submit"] {
            background-color: #ffcc00;
            border: none;
            font-weight: bold;
            cursor: pointer;
        }
        .error {
            color: red;
            font-weight: bold;
            text-align: center;
        }
    </style>
</head>
<body>
    <h2>Enroll Student in Course</h2>
    <% if ("duplicate".equals(request.getAttribute("error"))) { %>
        <div class="error">
            This student is already enrolled in this course with another instructor.
        </div>
    <% } %>

    <form action="EnrollStudentServlet" method="post">
        <label for="student">Select Student:</label>
        <select name="student" required>
            <%
                List<String> students = (List<String>) session.getAttribute("students");
                for (String s : students) {
            %>
                <option value="<%= s %>"><%= s %></option>
            <% } %>
        </select>

        <label for="instructorCourse">Select Course & Instructor:</label>
        <select name="instructorCourse" required>
            <%
                List<Map<String, String>> instructorCourses = (List<Map<String, String>>) session.getAttribute("instructorCourses");
                for (Map<String, String> option : instructorCourses) {
                    String id = option.get("Instructor_Course_ID");
                    String course = option.get("Course_Name");
                    String instructor = option.get("Instructor_Name");
            %>
                <option value="<%= id %>"><%= instructor %> - <%= course %></option>
            <% } %>
        </select>

        <input type="submit" value="Enroll">
    </form>
</body>
</html>

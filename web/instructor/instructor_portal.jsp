<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="web.Instructor.InstructorGrade"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Instructor Portal</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fdf8e1;
            padding: 40px;
        }
        h1 {
            color: #ffcc00;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #ffcc00;
        }
        .logout, .stats-btn {
            margin-top: 20px;
        }
        .logout-btn, .stats-btn input[type="submit"] {
            background-color: #ffcc00;
            color: black;
            padding: 10px 24px;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .logout-btn:hover, .stats-btn input[type="submit"]:hover {
            background-color: #e6b800;
        }
        .edit-cell {
            text-align: center;
            background-color: #fff9c4;
        }
        .edit-form input[type="submit"] {
            background-color: #ffcc00;
            border: none;
            padding: 6px 12px;
            cursor: pointer;
            font-weight: bold;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <h1>Welcome to Instructor Portal, <%= session.getAttribute("UserFullName") %></h1>

    <%
        List<InstructorGrade> gradeList = (List<InstructorGrade>) request.getAttribute("grades");

        if (gradeList != null && !gradeList.isEmpty()) {
    %>
        <table>
            <tr>
                <th>Course</th>
                <th>Student Name</th>
                <th>Grade</th>
                <th>Edit</th>
            </tr>
    <%
            for (InstructorGrade row : gradeList) {
    %>
            <tr>
                <td><%= row.getCourseName() %></td>
                <td><%= row.getStudentName() %></td>
                <td><%= row.getGrade() %></td>
                <td class="edit-cell">
                    <form class="edit-form" action="instructor/edit_grade.jsp" method="get">
                        <input type="hidden" name="enrollmentId" value="<%= row.getEnrollmentId() %>">
                        <input type="hidden" name="studentName" value="<%= row.getStudentName() %>">
                        <input type="hidden" name="courseName" value="<%= row.getCourseName() %>">
                        <input type="hidden" name="grade" value="<%= row.getGrade() %>">
                        <input type="submit" value="Edit Grade">
                    </form>
                </td>
            </tr>
    <%
            }
    %>
        </table>

        
        <div class="stats-btn">
            <form action="InstructorStatisticsServlet" method="get">
                <input type="submit" value="View Statistics">
            </form>
        </div>

    <%
        } else {
    %>
        <p>You don't Have Any Registered Students.</p>
    <%
        }
    %>

    <div class="logout">
        <form action="LogoutServlet" method="post">
            <input type="submit" value="Logout" class="logout-btn">
        </form>
    </div>
</body>
</html>

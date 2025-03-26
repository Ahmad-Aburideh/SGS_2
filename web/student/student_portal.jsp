<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="web.Student.StudentGrade"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Portal</title>
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
            width: 60%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #ffec99;
            color: #333;
        }
        td {
            background-color: #fffbea;
        }
        .logout {
            margin-top: 30px;
        }
        .logout-btn {
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

        .logout-btn:hover {
            background-color: #e6b800;
        }
    </style>
</head>
<body>
    <h1>Welcome to Student Portal, <%= session.getAttribute("UserFullName") %></h1>


    <%
        List<StudentGrade> grades = (List<StudentGrade>) request.getAttribute("grades");
        if (grades != null && !grades.isEmpty()) {
    %>
        <table>
            <tr>
                <th>Course Name</th>
                <th>Grade</th>
                <th>Instructor</th>
            </tr>
            <% for (StudentGrade g : grades) { %>
            <tr>
                <td><%= g.getCourseName() %></td>
                <td><%= g.getGrade() %></td>
                <td><%= g.getInstructorName() %></td>
            </tr>
            <% } %>
        </table>
    <%
        } else {
    %>
        <p>You Are Not Registered In Any Courses.</p>
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

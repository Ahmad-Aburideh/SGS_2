<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String enrollmentId = request.getParameter("enrollmentId");
    String studentName = request.getParameter("studentName");
    String courseName = request.getParameter("courseName");
    String grade = request.getParameter("grade");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Grade</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fdf8e1;
            padding: 40px;
        }
        h2 {
            color: #ffcc00;
        }
        form {
            background-color: white;
            padding: 20px;
            width: 300px;
            border-radius: 8px;
            box-shadow: 0 0 8px #ccc;
        }
        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }
        input[type="number"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 20px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        input[type="submit"] {
            background-color: #ffcc00;
            border: none;
            padding: 10px 15px;
            cursor: pointer;
            font-weight: bold;
            border-radius: 6px;
        }
    </style>
</head>
<body>

    <h2>Edit Grade for <%= studentName %></h2>
    <p>Course: <strong><%= courseName %></strong></p>
    <p>Current Grade: <strong><%= grade %></strong></p>

    <form action="<%= request.getContextPath() %>/EditGradeServlet" method="post">
        <input type="hidden" name="enrollmentId" value="<%= enrollmentId %>">
        
        <label for="newGrade">New Grade:</label>
        <input type="number" step="0.01" name="newGrade" required min="0" max="100">

        <input type="submit" value="Save Grade">
    </form>

</body>
</html>

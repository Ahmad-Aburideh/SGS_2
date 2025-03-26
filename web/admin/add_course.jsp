<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Course</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fdf8e1;
            padding: 40px;
            text-align: center;
        }
        h2 {
            color: #ffcc00;
        }
        form {
            background-color: white;
            padding: 20px;
            border-radius: 12px;
            display: inline-block;
            box-shadow: 0 0 10px #ccc;
        }
        input[type="text"] {
            padding: 10px;
            width: 300px;
            border: 1px solid #ccc;
            border-radius: 6px;
            margin-bottom: 15px;
        }
        input[type="submit"] {
            background-color: #ffcc00;
            padding: 10px 20px;
            border: none;
            font-weight: bold;
            cursor: pointer;
            border-radius: 6px;
        }
        .error {
            color: red;
            margin-top: 10px;
            font-weight: bold;
        }
    </style>
</head>
<body>

    <h2>Add New Course</h2>

    <% if ("duplicate".equals(request.getParameter("error"))) { %>
        <div class="error">This Course Already Exists.</div>
    <% } %>

    <form action="<%= request.getContextPath() %>/AddCourseServlet" method="post">
        <input type="text" name="courseName" placeholder="Course Name" required>
        <br>
        <input type="submit" value="Add Course">
    </form>

</body>
</html>

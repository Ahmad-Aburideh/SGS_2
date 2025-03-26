<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%
    List<Map<String, String>> courses = (List<Map<String, String>>) request.getAttribute("courseList");
    if (courses == null) courses = new ArrayList<>();
    String error = (String) request.getAttribute("error");
    String errorMessage = (String) request.getAttribute("errorMessage");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Instructor</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fdf8e1;
            padding: 40px;
        }
        h1 {
            text-align: center;
            color: #ffcc00;
        }
        form {
            max-width: 500px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 0 10px #ccc;
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 15px;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        .checkbox-group {
            margin-top: 10px;
            padding: 10px;
            background-color: #f9f9c5;
            border: 1px solid #ddd;
            border-radius: 8px;
        }
        .checkbox-group label {
            display: block;
            margin-bottom: 8px;
        }
        input[type="submit"] {
            margin-top: 20px;
            width: 100%;
            padding: 10px;
            background-color: #ffcc00;
            border: none;
            font-size: 16px;
            cursor: pointer;
            font-weight: bold;
        }
        .back {
            margin-top: 20px;
            text-align: center;
        }
        .back a {
            color: black;
            text-decoration: none;
            font-weight: bold;
        }
        .error-message {
            color: red;
            text-align: center;
            font-weight: bold;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <h1>Add New Instructor</h1>

    <% if (errorMessage != null) { %>
        <div class="error-message"><%= errorMessage %></div>
    <% } else if ("noCourse".equals(error)) { %>
        <div class="error-message">Please select at least one course.</div>
    <% } else if ("duplicate".equals(error)) { %>
        <div class="error-message">Instructor with the same full name already exists.</div>
    <% } else if ("1".equals(error)) { %>
        <div class="error-message">An unexpected error occurred. Please try again.</div>
    <% } %>

    <form action="<%= request.getContextPath() %>/InsertInstructorServlet" method="post">
        <label>Full Name:</label>
        <input type="text" name="fullName" required>

        <label>Username:</label>
        <input type="text" name="username" required>

        <label>Password:</label>
        <input type="password" name="password" required>

        <label>Assign Courses:</label>
        <div class="checkbox-group">
            <% for (Map<String, String> course : courses) { %>
                <label>
                    <input type="checkbox" name="courses" value="<%= course.get("id") %>">
                    <%= course.get("name") %>
                </label>
            <% } %>
        </div>

        <input type="submit" value="Add Instructor">
    </form>

    <div class="back">
        <a href="admin/admin_portal.jsp">← Back to Admin Portal</a>
    </div>
</body>
</html>

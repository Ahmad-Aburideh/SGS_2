<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Portal</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fdf8e1;
            padding: 40px;
        }
        h1 {
            color: #ffcc00;
            text-align: center;
        }
        .btn-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 20px;
            margin-top: 40px;
        }
        .admin-btn {
            padding: 12px 24px;
            background-color: #ffcc00;
            color: black;
            border: none;
            font-size: 16px;
            cursor: pointer;
            width: 300px;
            border-radius: 8px;
            font-weight: bold;
        }
        .logout {
            margin-top: 40px;
            text-align: center;
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

    <h1>Welcome to Admin Portal , <%= session.getAttribute("UserFullName") %></h1>

    <div class="btn-container">
        <form action="<%= request.getContextPath() %>/admin/add_student.jsp">
            <input class="admin-btn" type="submit" value="Add New Student">
        </form>

        <form action="<%= request.getContextPath() %>/AddInstructorServlet" method="get">
            <input class="admin-btn" type="submit" value="Add New Instructor">
        </form>

        <form action="<%= request.getContextPath() %>/admin/add_course.jsp">
            <input class="admin-btn" type="submit" value="Add New Course">
        </form>

        <form action="<%= request.getContextPath() %>/LoadEnrollmentDataServlet">
            <input class="admin-btn" type="submit" value="Enroll Student in Course">
        </form>
    </div>


    <div class="logout">
        <form action="<%= request.getContextPath() %>/LogoutServlet" method="post">
            <input type="submit" value="Logout" class="logout-btn">
        </form>
    </div>


</body>
</html>

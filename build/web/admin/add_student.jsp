<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Student</title>
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
            max-width: 400px;
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
    </style>
</head>
<body>
    <h1>Add New Student</h1>

    <form action="../AddStudentServlet" method="post">
        <label>Full Name:</label>
        <input type="text" name="fullName" required>

        <label>Username:</label>
        <input type="text" name="username" required>

        <label>Password:</label>
        <input type="password" name="password" required>

        <input type="submit" value="Add Student">
    </form>

    <div class="back">
        <a href="admin_portal.jsp">← Back to Admin Portal</a>
    </div>
</body>
</html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fef9e7; 
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-container {
            background-color: #fff9c4;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.15);
            width: 400px;
        }

        h2 {
            text-align: center;
            color: #f57f17;
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
            color: #444;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        input[type="submit"] {
            width: 100%;
            background-color: #fbc02d;
            color: #000;
            border: none;
            padding: 10px;
            font-weight: bold;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #f9a825;
        }

        .error {
            color: red;
            text-align: center;
            margin-top: 10px;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="login-container">
    <h2>Student Grading System Login Page</h2>

    <form action="Login_Servlet" method="post">
        <label for="username">Username:</label>
        <input type="text" name="Username" id="username" required />

        <label for="password">Password:</label>
        <input type="password" name="Password" id="password" required />

        <input type="submit" value="Login" />
    </form>

    <%
        String error = request.getParameter("error");
        if (error != null) {
            if (error.equals("1")) {
    %>
                <p class="error">Invalid Username or Password.</p>
    <%
            } else if (error.equals("2")) {
    %>
                <p class="error">There is Something Wrong, Please Try Again.</p>
    <%
            }
        }
    %>
</div>

</body>
</html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedHashMap"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Instructor Statistics</title>
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
        table {
            width: 80%;
            margin: auto;
            border-collapse: collapse;
            background-color: white;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #ffcc00;
        }
        .back-btn {
            margin-top: 30px;
            text-align: center;
        }
        .back-btn a {
            background-color: #ffcc00;
            padding: 10px 24px;
            text-decoration: none;
            color: black;
            font-weight: bold;
            border-radius: 8px;
            display: inline-block;
        }
        .back-btn a:hover {
            background-color: #e6b800;
        }
    </style>
</head>
<body>
    <h1>Course Statistics</h1>

    <%
        Map<String, Map<String, Double>> stats = (Map<String, Map<String, Double>>) request.getAttribute("statistics");
        if (stats != null && !stats.isEmpty()) {
    %>
        <table>
            <tr>
                <th>Course Name</th>
                <th>Average</th>
                <th>Median</th>
                <th>Highest</th>
                <th>Lowest</th>
            </tr>
            <%
                for (Map.Entry<String, Map<String, Double>> entry : stats.entrySet()) {
                    String course = entry.getKey();
                    Map<String, Double> values = entry.getValue();
            %>
            <tr>
                <td><%= course %></td>
                <td><%= values.get("Average") %></td>
                <td><%= values.get("Median") %></td>
                <td><%= values.get("Max") %></td>
                <td><%= values.get("Min") %></td>
            </tr>
            <% } %>
        </table>
    <% } else { %>
        <p style="text-align:center; font-weight:bold;">No data available for statistics.</p>
    <% } %>

    <div class="back-btn">
        <a href="InstructorPortalServlet">← Back to Portal</a>
    </div>
</body>
</html>

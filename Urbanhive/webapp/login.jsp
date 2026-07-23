<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>UrbanHive Login</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body>
<main class="shell shell--centered">
    <section class="card auth-card">
        <h1>UrbanHive</h1>
        <p class="muted">Society management for residents, admins, and guards.</p>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
        <div class="alert"><%= error %></div>
        <% } %>

        <form method="post" action="<%= request.getContextPath() %>/login" class="form">
            <label>Email</label>
            <input type="email" name="email" placeholder="admin@urbanhive.local" required>

            <label>Password</label>
            <input type="password" name="password" placeholder="Enter password" required>

            <button type="submit">Log in</button>
        </form>

        <div class="demo-box">
            <strong>Demo users</strong>
            <p>Admin: admin@urbanhive.local / admin123</p>
            <p>Resident: resident@urbanhive.local / resident123</p>
            <p>Guard: guard@urbanhive.local / guard123</p>
        </div>
    </section>
</main>
</body>
</html>

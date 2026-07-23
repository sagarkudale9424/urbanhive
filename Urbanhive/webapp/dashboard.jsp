<%@ page import="java.util.List" %>
<%@ page import="com.urbanhive.model.User" %>
<%@ page import="com.urbanhive.model.Bill" %>
<%@ page import="com.urbanhive.model.Complaint" %>
<%@ page import="com.urbanhive.model.Notice" %>
<%@ page import="com.urbanhive.model.VisitorLog" %>
<%
    User currentUser = (User) request.getAttribute("currentUser");
    List<Bill> bills = (List<Bill>) request.getAttribute("bills");
    List<Complaint> complaints = (List<Complaint>) request.getAttribute("complaints");
    List<Notice> notices = (List<Notice>) request.getAttribute("notices");
    List<VisitorLog> visitorLogs = (List<VisitorLog>) request.getAttribute("visitorLogs");
    String flash = (String) session.getAttribute("flash");
    if (flash != null) {
        session.removeAttribute("flash");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>UrbanHive Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body>
<main class="shell">
    <header class="topbar">
        <div>
            <h1>UrbanHive Dashboard</h1>
            <p class="muted">Welcome, <%= currentUser.getFullName() %> (<%= currentUser.getRole() %>)</p>
        </div>
        <a class="link-button" href="<%= request.getContextPath() %>/logout">Logout</a>
    </header>

    <% if (flash != null) { %>
    <div class="alert"><%= flash %></div>
    <% } %>

    <section class="grid">
        <article class="card">
            <h2>Notices</h2>
            <% if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) { %>
            <form method="post" action="<%= request.getContextPath() %>/notices" class="form">
                <label>Title</label>
                <input type="text" name="title" required>
                <label>Content</label>
                <textarea name="content" rows="3" required></textarea>
                <button type="submit">Post notice</button>
            </form>
            <% } %>

            <div class="list">
                <% for (Notice notice : notices) { %>
                <div class="list-item">
                    <strong><%= notice.getTitle() %></strong>
                    <p><%= notice.getContent() %></p>
                    <small><%= notice.getPostedAt() %></small>
                </div>
                <% } %>
            </div>
        </article>

        <article class="card">
            <h2>Complaints</h2>
            <% if ("RESIDENT".equalsIgnoreCase(currentUser.getRole())) { %>
            <form method="post" action="<%= request.getContextPath() %>/complaints" class="form">
                <label>Issue title</label>
                <input type="text" name="title" required>
                <label>Description</label>
                <textarea name="description" rows="3" required></textarea>
                <button type="submit">Submit complaint</button>
            </form>
            <% } %>

            <div class="list">
                <% if (complaints != null) {
                    for (Complaint complaint : complaints) { %>
                <div class="list-item">
                    <strong><%= complaint.getTitle() %></strong>
                    <p><%= complaint.getDescription() %></p>
                    <small>Status: <%= complaint.getStatus() %> | Assigned: <%= complaint.getAssignedTo() %></small>
                </div>
                <% }} %>
            </div>
        </article>

        <% if ("RESIDENT".equalsIgnoreCase(currentUser.getRole())) { %>
        <article class="card">
            <h2>Maintenance Bills</h2>
            <div class="list">
                <% if (bills != null) {
                    for (Bill bill : bills) { %>
                <div class="list-item">
                    <strong><%= bill.getBillMonth() %></strong>
                    <p>Amount: Rs. <%= bill.getAmount() %></p>
                    <small>Due: <%= bill.getDueDate() %> | Status: <%= bill.getStatus() %></small>
                </div>
                <% }} %>
            </div>
        </article>
        <% } %>

        <article class="card">
            <h2>Visitor Logs</h2>
            <% if ("GUARD".equalsIgnoreCase(currentUser.getRole())) { %>
            <form method="post" action="<%= request.getContextPath() %>/visitors" class="form">
                <label>Resident ID</label>
                <input type="number" name="residentId" placeholder="Optional">
                <label>Visitor name</label>
                <input type="text" name="visitorName" required>
                <label>Purpose</label>
                <input type="text" name="purpose" required>
                <button type="submit">Log entry</button>
            </form>
            <% } %>

            <div class="list">
                <% for (VisitorLog log : visitorLogs) { %>
                <div class="list-item">
                    <strong><%= log.getVisitorName() %></strong>
                    <p><%= log.getPurpose() %></p>
                    <small>Entry: <%= log.getEntryTime() %></small>
                </div>
                <% } %>
            </div>
        </article>
    </section>
</main>
</body>
</html>

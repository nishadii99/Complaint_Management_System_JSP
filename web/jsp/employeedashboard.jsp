<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.ijse.model.Complaint" %>

<%
    List<Complaint> complaintList = (List<Complaint>) request.getAttribute("complaintList");
    String success = request.getParameter("message");
    String error = request.getParameter("error");
%>

<html>
<head>
    <title>Submit Complaint</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/employeedashboard.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js" ></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="${pageContext.request.contextPath}/js/employeedashboard.js" defer></script>
</head>
<body>


<div class="navbar">
    <p >Employee Dashboard</p>
    <a href="${pageContext.request.contextPath}/jsp/signin.jsp">Logout</a>
</div>


<% if (success != null) { %>
<script>
    Swal.fire({
        icon: 'success',
        title: 'Success',
        text: '<%= success %>',
        confirmButtonColor: '#3085d6'
    });
</script>
<% } else if (error != null) { %>
<script>
    Swal.fire({
        icon: 'error',
        title: 'Error',
        text: '<%= error %>',
        confirmButtonColor: '#d33'
    });
</script>
<% } %>


<div class="container">
    <div class="form-box">
        <div class="form-title">Submit Complaint</div>
        <form id="complaintForm" action="${pageContext.request.contextPath}/emp-complaint" method="post">
            <input type="hidden" name="action" id="formAction" value="insert" />
            <input type="hidden" name="user_id" value="${sessionScope.user_id}" />
            <input type="hidden" name="complaint_id" id="complaint_id" />
            <p>Welcome : ${sessionScope.username} </p>
            <label style="color: black">Title:</label>
            <input type="text" name="title" id="title" placeholder="Enter your Compliant title" required />

            <label style="color: black">Description:</label>
            <textarea name="description" id="description" rows="5" placeholder="Enter your Compliant" required></textarea>

            <!-- Action Buttons -->
            <button type="submit" class="btn-submit">Submit</button>
            <button type="button" class="btn-update" onclick="setFormAction('update')">Update</button>
            <button type="button" class="btn-delete" onclick="confirmDelete()">Delete</button>
        </form>

        <form method="get" action="${pageContext.request.contextPath}/emp-complaint" style="display: inline;">
            <button id="btn-reload" type="submit">Reload Complaints</button>
        </form>

    </div>


    <table class="complaints-table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>Status</th>
            <th>Remarks</th>
            <th>Created At</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (complaintList == null || complaintList.isEmpty()) {
        %>
        <tr><td colspan="6">No complaints found.</td></tr>
        <%
        } else {
            for (Complaint c : complaintList) {
        %>
        <tr>
            <td><%= c.getComplaintId() %></td>
            <td><%= c.getTitle() %></td>
            <td><%= c.getDescription() %></td>
            <td><%= c.getStatus() %></td>
            <td><%= c.getRemarks() %></td>
            <td><%= c.getCreatedAt() %></td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>

<script>
    const complaintList = [
        <% if (complaintList != null) {
            for (int i = 0; i < complaintList.size(); i++) {
                Complaint c = complaintList.get(i);
        %>
        {
            complaintId: "<%= c.getComplaintId() %>",
            title: "<%= c.getTitle() %>",
            description: "<%= c.getDescription() %>",
            status: "<%= c.getStatus() %>",
            remarks: "<%= c.getRemarks() %>",
            createdAt: "<%= c.getCreatedAt() %>"
        }<%= (i < complaintList.size() - 1) ? "," : "" %>
        <%  }
           } %>
    ];
</script>
</body>
</html>

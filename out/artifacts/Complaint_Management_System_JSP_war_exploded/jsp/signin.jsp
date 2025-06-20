<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Complaint Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signin.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<header style="background-color: #0f3d3e; color: white; padding: 15px; text-align: center;">
    <h1>Complaint Management System</h1>
    <p>Municipal IT Division</p>
</header>
<div class="login-container">
    <h2>Sign In</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required placeholder="Enter your username">
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required placeholder="Enter your password">
        </div>

        <button id="btn-login" type="submit">Login</button>

        <p>Don't have an account?
            <a href="${pageContext.request.contextPath}/jsp/signup.jsp">Register here</a>
        </p>
    </form>
</div>


<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<script>
    Swal.fire({
        icon: 'error',
        title: 'Login Failed',
        text: '<%= error %>',
        confirmButtonColor: '#d33'
    });
</script>
<%
    }
%>


<%
    String signupStatus = request.getParameter("signup");
    if ("success".equals(signupStatus)) {
%>
<script>
    Swal.fire("Account Created!", "You can now log in.", "success");
</script>
<%
    }
%>
</body>
</html>

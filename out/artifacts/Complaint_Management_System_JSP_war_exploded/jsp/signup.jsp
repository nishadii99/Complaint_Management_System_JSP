<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%
    List<String> usernames = (List<String>) request.getAttribute("existingUsernames");
    if (usernames == null) usernames = new ArrayList<>();
    String jsonUsernames = new Gson().toJson(usernames);
%>
<html>
<head>
    <title>SignUp - Complaint Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signup.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="signup-container">
    <div class="signup-header">
        <h2>Sign Up</h2>
    </div>

    <div class="signup-form">
        <form id="signupForm" action="${pageContext.request.contextPath}/signup" method="post">
            <div class="form-row">
                <div class="form-group">
                    <label for="firstName">First Name</label>
                    <input type="text" id="firstName" name="firstName" required placeholder="Enter your first name">
                </div>
                <div class="form-group">
                    <label for="lastName">Last Name</label>
                    <input type="text" id="lastName" name="lastName" required placeholder="Enter your last name">
                </div>
            </div>

            <div class="form-group">
                <label for="address">Address</label>
                <input type="text" id="address" name="address" required placeholder="Enter your address">
            </div>

            <div class="form-group">
                <label for="mobile">Mobile Number</label>
                <input type="tel" id="mobile" name="mobile" required placeholder="Enter your mobile number">
            </div>

            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" required placeholder="Enter your email">
            </div>

            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required placeholder="Choose a username">
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <div class="password-container">
                    <input type="password" id="password" name="password" required placeholder="Create a password">
                    <span class="toggle-password" onclick="togglePassword()">
                        <i class="fas fa-eye"></i>
                    </span>
                </div>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <div class="password-container">
                    <input type="password" id="confirmPassword" name="confirmPassword" required placeholder="Confirm your password">
                    <span class="toggle-password" onclick="toggleConfirmPassword()">
                        <i class="fas fa-eye"></i>
                    </span>
                </div>
            </div>



            <div class="form-group">
                <label for="jobRole">Job Role</label>
                <select id="jobRole" name="jobRole" required>
                    <option value="" disabled selected>Select your job role</option>
                    <option value="admin">Admin</option>
                    <option value="employee">Employee</option>
                </select>
            </div>

            <div class="terms">
                <input type="checkbox" id="terms" name="terms" required>
                <label for="terms">I agree to the <a href="#">Terms of Service</a> and <a href="#">Privacy Policy</a></label>
            </div>

            <button id="btn-signup" type="submit">Create Account</button>

            <div class="error" id="error-message">Passwords do not match. Please try again.</div>

            <div class="login-link">
                Already have an account? <a href="${pageContext.request.contextPath}/jsp/signin.jsp">Log in</a>
            </div>
        </form>
    </div>
</div>

<input type="hidden" id="existingUsernames" value='<%= jsonUsernames %>'>

<script>

    <%if ("username_taken".equals(request.getParameter("signup"))) { %>
    Swal.fire({
        icon: "warning",
        title: "Username Taken!",
        text: "Please choose a different username."
    });
    <% } else if ("pass_mismatch".equals(request.getParameter("signup"))) { %>
    Swal.fire({ icon: "error", title: "Passwords do not match!", text: "Please try again." });
    <% } else if ("fail".equals(request.getParameter("signup"))) { %>
    Swal.fire({ icon: "error", title: "Oops!", text: "Something went wrong. Please try again." });
    <% } else if ("success".equals(request.getParameter("signup"))) { %>
    Swal.fire({ icon: "success", title: "Account Created!", text: "You can now log in." });
    <% } %>
</script>
<script src="${pageContext.request.contextPath}/js/signup.js"></script>
<script src="${pageContext.request.contextPath}/js/validation.js"></script>
</body>
</html>

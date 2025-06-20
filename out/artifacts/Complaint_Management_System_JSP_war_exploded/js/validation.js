$(document).ready(function () {
    $('#signupForm').on('submit', function (e) {
        const firstName = $('#firstName').val().trim();
        const lastName = $('#lastName').val().trim();
        const address = $('#address').val().trim();
        const mobile = $('#mobile').val().trim();
        const email = $('#email').val().trim();
        const username = $('#username').val().trim();
        const password = $('#password').val();
        const confirmPassword = $('#confirmPassword').val();

        const nameRegex = /^[A-Za-z]{3,}$/;
        const addressRegex = /^[A-Za-z0-9\s,.'-]{5,}$/;
        const mobileRegex = /^07[0-9]{8}$/;
        const emailRegex = /^[\w.-]+@[a-zA-Z\d.-]+\.[a-zA-Z]{2,}$/;
        const usernameRegex = /^[A-Za-z0-9]{4,20}$/;
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;

        if (!nameRegex.test(firstName)) {
            e.preventDefault();
            Swal.fire("Invalid First Name", "Only letters, at least 2 characters.");
            return;
        }

        if (!nameRegex.test(lastName)) {
            e.preventDefault();
            Swal.fire("Invalid Last Name", "Only letters, at least 2 characters.");
            return;
        }

        if (!addressRegex.test(address)) {
            e.preventDefault();
            Swal.fire("Invalid Address", "Minimum 5 characters, letters/numbers allowed.");
            return;
        }

        if (!mobileRegex.test(mobile)) {
            e.preventDefault();
            Swal.fire("Invalid Mobile Number", "Must be in the format 07XXXXXXXX.");
            return;
        }

        if (!emailRegex.test(email)) {
            e.preventDefault();
            Swal.fire("Invalid Email", "Please enter a valid email address.");
            return;
        }

        if (!usernameRegex.test(username)) {
            e.preventDefault();
            Swal.fire("Invalid Username", "4–20 characters, letters and numbers only.");
            return;
        }

        if (!passwordRegex.test(password)) {
            e.preventDefault();
            Swal.fire("Weak Password", "Must be at least 8 characters with upper, lower, and digit.");
            return;
        }

        if (password !== confirmPassword) {
            e.preventDefault();
            Swal.fire("Passwords Mismatch", "Please make sure passwords match.");
            return;
        }
    });
});
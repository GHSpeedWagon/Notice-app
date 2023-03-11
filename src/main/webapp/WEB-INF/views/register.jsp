<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>
<div id="login-form" style="display: none;">
    <form id="register-new-user-form">
        <label for="login-input">Login</label>
        <input type="text" id="login-input" name="login"><br>
        <label for="password-input">Password</label>
        <input type="password" id="password-input" name="password"><br>
        <input type="submit" value="Register">
    </form>
</div>
<script>
    const loginForm = document.getElementById("login-form");
    const registerNewForm = document.getElementById("register-new-user-form");

    loginForm.style.display = "block";

    registerNewForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const username = document.getElementById("login-input").value;
        const password = document.getElementById("password-input").value;
        const data = {username: username, password: password}
        const xhr = new XMLHttpRequest();
        xhr.open('POST', '/register', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                loginForm.style.display = "none";
                registerNewForm.reset();
                location.reload();
            }
        };
        xhr.send(JSON.stringify(data));
    });
</script>
<h4><a href="${pageContext.request.contextPath}/login">Login</a></h4>
</body>
</html>
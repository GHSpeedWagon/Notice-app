<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <link rel="stylesheet" href="//cdn.dhtmlx.com/suite/edge/suite.css" type="text/css" />
    <link rel="stylesheet" href="https://snippet.dhtmlx.com/codebase/assets/css/auxiliary_controls.css">
    <script src="//cdn.dhtmlx.com/suite/edge/suite.js"></script>
</head>
<body>
<style>
    <%@include file='../css/design.css'%>
</style>
<section class="dhx_sample-container" style="height: 90%; width: 45.7%">
    <div style="height: 90%; width: 100%" id="grid"></div>
</section>
<button class="dhx_demo-button" onclick="addNewItem()">
    <span class="dhx_demo-button__icon dxi dxi-plus"></span>
    <span class="dhx_demo-button__text">Add new user</span>
</button>
<div id="login-form" style="display: none;">
    <form id="add-user-form">
        <label for="username-input">Username</label>
        <input type="text" id="username-input" name="username"><br>
        <label for="password-input">Password</label>
        <input id="password-input" name="password"><br>
        <input type="submit" value="Save">
    </form>
</div>
<script>
    const loginForm = document.getElementById("login-form");
    const addUserForm = document.getElementById("add-user-form");

    function addNewItem() {
        loginForm.style.display = "block";
    }

    addUserForm.addEventListener("submit", function(event) {
        event.preventDefault();

        const username = document.getElementById("username-input").value;
        const password = document.getElementById("password-input").value;
        const data = {username: username, password: password}
        const xhr = new XMLHttpRequest();
        xhr.open('POST', '/register', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                loginForm.style.display = "none";
                addUserForm.reset();
                location.reload();
            }
        };
        xhr.send(JSON.stringify(data));
    });
    const grid = new dhx.Grid("grid", {
        css: "dhx_demo-grid",
        columns: [
            { width: 50,
                id: "id",
                header: [{ text: "Id" }]
            },
            { width: 200,
                id: "username",
                header: [{ text: "username"}, { content: "inputFilter" }]
            },
            {
                id: "edit", gravity: 1.5, header: [{ text: "edit", align: "center" }],
                width: 60,
                htmlEnable: true, align: "center",
                template: function () {
                    return "<span class='action-buttons'><a class='edit-button'>edit</a></span>"
                }
            },
            {
                id: "delete", gravity: 1.5, header: [{ text: " ", align: "center" }],
                width: 55,
                htmlEnable: true, align: "center",
                template: function () {
                    return "<span class='action-buttons'><a class='remove-button'><span class='icon'></span></a></span>"
                }
            }
        ],
        selection: "row",
        editable: false,
        eventHandlers: {
            onclick: {
                "remove-button": function (e, data) {
                    const xhr = new XMLHttpRequest();
                    xhr.open('DELETE', 'users/delete/' + data.row.id, true);
                    grid.data.remove(data.row.id);
                    xhr.send();
                },
                "edit-button": function (e, data) {
                    openEditor(data.row.id);
                }
            }
        }
    });

    const data = [
        <c:forEach items="${users}" var="user" varStatus="status">
        {
            id: "${user.id}",
            username: "${user.username}"
        }<c:if test="${not status.last}">, </c:if>
        </c:forEach>
    ]
    grid.data.parse(data);


    const editWindow = new dhx.Window({
        width: 440,
        height: 250,
        modal: true
    });

    const editFormConfig = {
        padding: 0,
        rows: [
            {
                id: "id",
                type: "input",
                name: "id",
                hidden: true
            },
            {
                type: "input",
                name: "username",
                label: "Username"
            },
            {
                align: "end",
                cols: [
                    {
                        id: "apply-button",
                        type: "button",
                        text: "Apply",
                        circle: true,
                    }
                ]
            }
        ]
    }
    const editForm = new dhx.Form(null, editFormConfig);

    editForm.getItem("apply-button").events.on("click", function () {
        const newData = editForm.getValue();
        if (newData.id) {
            grid.data.update(newData.id, {...newData})
            grid.scrollTo(newData.id, "username");

            const xhr = new XMLHttpRequest();
            xhr.open("POST", "http://localhost:8080/users/edit");
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.send(JSON.stringify(newData));
        }
        closeEditor();
    });

    function openEditor(id) {
        editWindow.show();
        const item = grid.data.getItem(id);
        if (item) {
            editForm.setValue(item);
        }
    }
    function closeEditor() {
        editForm.clear();
        editWindow.hide();
    }
    editWindow.attach(editForm);
</script>
</body>
</html>
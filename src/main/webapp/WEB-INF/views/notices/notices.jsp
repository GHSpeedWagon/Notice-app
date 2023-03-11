<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Notices</title>
    <link rel="stylesheet" href="//cdn.dhtmlx.com/suite/edge/suite.css" type="text/css" />
    <link rel="stylesheet" href="https://snippet.dhtmlx.com/codebase/assets/css/auxiliary_controls.css">
    <script src="//cdn.dhtmlx.com/suite/edge/suite.js"></script>
</head>
<body>
<style>
    <%@include file='../css/design.css'%>
</style>
<h2> Welcome back ${username}!</h2>
<section class="dhx_sample-container" style="height: 90%; width: 36.9%">
    <div style="height: 90%; width: 100%" id="grid"></div>
</section>
<button class="dhx_demo-button" onclick="addNewItem()">
    <span class="dhx_demo-button__icon dxi dxi-plus"></span>
    <span class="dhx_demo-button__text">Add new user</span>
</button>
<div id="notice-form" style="display: none;">
    <form id="add-notice-form">
        <label for="notice-input">Notice</label>
        <input type="text" id="notice-input" name="username"><br>
        <input type="submit" value="Save">
    </form>
</div>
<script>
    const grid = new dhx.Grid("grid", {
        css: "dhx_demo-grid",
        columns: [
            { width: 50, id: "id", header: [{ text: "Id" }]},
            { width: 200, id: "notice", header: [{ text: "Notice"}] },
            { width: 60, id: "likes", header: [{ text: "likes" }] },
            {
                id: "like", gravity: 1.5, header: [{ text: "like", align: "center" }],
                width: 55,
                htmlEnable: true, align: "center",
                template: function () {
                    return "<span class='action-buttons'><a class='like-button'><span class='icon'></span></a></span>"
                }
            },
            {
                id: "unlike", gravity: 1.5, header: [{ text: "unlike", align: "center"}],
                width: 65,
                htmlEnable: true, align: "center",
                template: function () {
                    return "<span class='action-buttons'><a class='unlike-button'>unlike</a></span>"
                }
            },
            {
                id: "edit", gravity: 1.5, header: [{ text: "edit", align: "center" }],
                width: 60,
                htmlEnable: true, align: "center",
                template: function () {
                    return "<span class='action-buttons'><a class='edit-button'>edit</a></span>"
                }
            },

        ],
        selection: "row",
        eventHandlers: {
            onclick: {
                "like-button": function (e, data) {
                    window.location.href= "http://localhost:8080/notices/like/" + data.row.id;
                },
                "unlike-button": function (e, data) {
                    window.location.href= "http://localhost:8080/notices/unlike/" + data.row.id;
                },
                "edit-button": function (e, data) {
                    openEditor(data.row.id);
                }
            }
        }
    });

    const data = [
        <c:forEach items="${notices}" var="notice" varStatus="status">
        {
            id: "${notice.id}",
            notice: "${notice.notice}",
            likes: "${notice.likesCount}",
        }<c:if test="${not status.last}">, </c:if>
        </c:forEach>
    ]
    grid.data.parse(data);


    const editWindow = new dhx.Window({
        width: 440,
        height: 240,
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
                name: "notice",
                label: "Notice"
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
            grid.scrollTo(newData.id, "notice");

            const xhr = new XMLHttpRequest();
            xhr.open("POST", "http://localhost:8080/notices/edit");
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

    const noticeForm = document.getElementById("notice-form");
    const addNoticeForm = document.getElementById("add-notice-form");

    function addNewItem() {
        noticeForm.style.display = "block";
    }

    addNoticeForm.addEventListener("submit", function(event) {
        event.preventDefault();

        const notice = document.getElementById("notice-input").value;
        const data = {notice: notice}
        const xhr = new XMLHttpRequest();
        xhr.open('POST', '/notices', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onreadystatechange = function() {
        noticeForm.style.display = "none";
        addNoticeForm.reset();
        location.reload();
        };
        xhr.send(JSON.stringify(data));
    });
</script>
</body>
</html>
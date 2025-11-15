<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Добавить книгу</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        nav { background: #333; padding: 10px; margin-bottom: 20px; }
        nav a { color: white; text-decoration: none; margin: 0 15px; }
        nav a:hover { text-decoration: underline; }
        form { max-width: 500px; }
        label { display: block; margin: 10px 0 5px; }
        input, button { padding: 8px; width: 100%; box-sizing: border-box; }
        button { background: #333; color: white; border: none; cursor: pointer; margin-top: 10px; }
        .error { color: red; }
    </style>
</head>
<body>
    <nav>
        <a href="${pageContext.request.contextPath}/">Главная</a>
        <a href="${pageContext.request.contextPath}/books">Все книги</a>
        <a href="${pageContext.request.contextPath}/add-book">Добавить книгу</a>
    </nav>

    <h1>Добавить новую книгу</h1>

    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/add-book">
        <label for="title">Название:</label>
        <input type="text" id="title" name="title" required>

        <label for="author">Автор:</label>
        <input type="text" id="author" name="author" required>

        <label for="year">Год издания:</label>
        <input type="number" id="year" name="year" min="1000" max="2024" required>

        <label for="isbn">ISBN:</label>
        <input type="text" id="isbn" name="isbn">

        <button type="submit">Добавить книгу</button>
    </form>
</body>
</html>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Все книги</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        nav { background: #333; padding: 10px; margin-bottom: 20px; }
        nav a { color: white; text-decoration: none; margin: 0 15px; }
        nav a:hover { text-decoration: underline; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
<nav>
    <a href="${request.contextPath}/">Главная</a>
    <a href="${request.contextPath}/books">Все книги</a>
    <a href="${request.contextPath}/add-book">Добавить книгу</a>
</nav>

<h1>Все книги</h1>

<#if books?size == 0>
<p>Книг пока нет в базе данных.</p>
<#else>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Автор</th>
        <th>Год</th>
        <th>ISBN</th>
    </tr>
    </thead>
    <tbody>
    <#list books as book>
    <tr>
        <td>${book.id}</td>
        <td>${book.title}</td>
        <td>${book.author}</td>
        <td>${book.year}</td>
        <td>${book.isbn!''}</td>
    </tr>
    </#list>
    </tbody>
</table>
</#if>
</body>
</html>
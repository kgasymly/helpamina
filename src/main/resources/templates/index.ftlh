<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Книжный магазин - Главная</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        nav { background: #333; padding: 10px; margin-bottom: 20px; }
        nav a { color: white; text-decoration: none; margin: 0 15px; }
        nav a:hover { text-decoration: underline; }
    </style>
</head>
<body>
<nav>
    <a href="${request.contextPath}/">Главная</a>
    <a href="${request.contextPath}/books">Все книги</a>
    <a href="${request.contextPath}/add-book">Добавить книгу</a>
</nav>

<h1>Добро пожаловать в книжный магазин!</h1>
<p>Здесь вы можете просматривать и добавлять книги в нашу коллекцию.</p>
</body>
</html>
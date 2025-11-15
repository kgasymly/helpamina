package servlet;

import dao.BookDao;
import model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-book")
public class AddBookServlet extends HttpServlet {
    private BookDao bookDao = new BookDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Просто показываем форму
        req.getRequestDispatcher("/add-book.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Устанавливаем кодировку
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Получаем параметры из формы
            String title = req.getParameter("title");
            String author = req.getParameter("author");
            String yearStr = req.getParameter("year");
            String isbn = req.getParameter("isbn");

            // Проверяем обязательные поля
            if (title == null || title.trim().isEmpty()) {
                req.setAttribute("error", "Название книги обязательно");
                req.getRequestDispatcher("/add-book.jsp").forward(req, resp);
                return;
            }

            if (author == null || author.trim().isEmpty()) {
                req.setAttribute("error", "Автор книги обязателен");
                req.getRequestDispatcher("/add-book.jsp").forward(req, resp);
                return;
            }

            // Обрабатываем год
            Integer year = null;
            if (yearStr != null && !yearStr.trim().isEmpty()) {
                try {
                    year = Integer.parseInt(yearStr);
                } catch (NumberFormatException e) {
                    req.setAttribute("error", "Год должен быть числом");
                    req.getRequestDispatcher("/add-book.jsp").forward(req, resp);
                    return;
                }
            }

            // Обрабатываем ISBN (может быть пустым)
            if (isbn != null && isbn.trim().isEmpty()) {
                isbn = null;
            }

            // Создаем книгу и добавляем в БД
            Book book = new Book(title.trim(), author.trim(), year, isbn);
            boolean success = bookDao.addBook(book);

            if (success) {
                // Перенаправляем на страницу со всеми книгами
                resp.sendRedirect(req.getContextPath() + "/books");
            } else {
                req.setAttribute("error", "Не удалось добавить книгу в базу данных");
                req.getRequestDispatcher("/add-book.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            req.setAttribute("error", "Произошла ошибка: " + e.getMessage());
            req.getRequestDispatcher("/add-book.jsp").forward(req, resp);
        }
    }
}
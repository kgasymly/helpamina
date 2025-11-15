package servlet;

import dao.BookDao;
import model.Book;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/test-db")
public class TestDBServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<h1>Тест БД и просмотр книг</h1>");

        try {
            BookDao dao = new BookDao();
            List<Book> books = dao.getAllBooks();

            out.println("<p style='color: green'>✅ Успех! Подключение к БД работает.</p>");
            out.println("<p>Книг в базе: " + books.size() + "</p>");

            if (!books.isEmpty()) {
                out.println("<h3>Список книг:</h3>");
                out.println("<table border='1' style='border-collapse: collapse'>");
                out.println("<tr><th>ID</th><th>Название</th><th>Автор</th><th>Год</th><th>ISBN</th></tr>");
                for (Book book : books) {
                    out.println("<tr>");
                    out.println("<td>" + book.getId() + "</td>");
                    out.println("<td>" + book.getTitle() + "</td>");
                    out.println("<td>" + book.getAuthor() + "</td>");
                    out.println("<td>" + book.getYear() + "</td>");
                    out.println("<td>" + book.getIsbn() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            } else {
                out.println("<p>В базе нет книг</p>");
            }

        } catch (Exception e) {
            out.println("<p style='color: red'>❌ Ошибка: " + e.getMessage() + "</p>");
        }
    }
}
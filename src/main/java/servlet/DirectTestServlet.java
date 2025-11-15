package servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/direct-test")
public class DirectTestServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        
        out.println("<h1>Прямой тест БД</h1>");
        
        String url = "jdbc:postgresql://localhost:5432/bookdb";
        String user = "postgres";
        String password = "kanan123@@";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            out.println("<p style='color: green'>✅ Подключение к БД установлено</p>");
            
            // 1. Проверяем существование таблицы
            out.println("<h3>1. Проверка таблицы:</h3>");
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tables = meta.getTables(null, null, "books", new String[]{"TABLE"});
            if (tables.next()) {
                out.println("<p style='color: green'>✅ Таблица 'books' существует</p>");
            } else {
                out.println("<p style='color: red'>❌ Таблица 'books' НЕ существует</p>");
                return;
            }
            
            // 2. Пробуем вставить запись напрямую
            out.println("<h3>2. Прямая вставка:</h3>");
            String sql = "INSERT INTO books (title, author, year, isbn) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "Прямая тестовая книга");
                stmt.setString(2, "Тестовый автор");
                stmt.setInt(3, 2024);
                stmt.setString(4, "direct-test-123");
                
                int rows = stmt.executeUpdate();
                out.println("<p style='color: green'>✅ Вставлено записей: " + rows + "</p>");
            }
            
            // 3. Проверяем что запись добавилась
            out.println("<h3>3. Проверка данных:</h3>");
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM books")) {
                if (rs.next()) {
                    out.println("<p>Всего книг в базе: " + rs.getInt("count") + "</p>");
                }
            }
            
            // 4. Показываем все книги
            out.println("<h3>4. Список всех книг:</h3>");
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {
                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Название</th><th>Автор</th><th>Год</th><th>ISBN</th></tr>");
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getString("title") + "</td>");
                    out.println("<td>" + rs.getString("author") + "</td>");
                    out.println("<td>" + rs.getInt("year") + "</td>");
                    out.println("<td>" + rs.getString("isbn") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
            
        } catch (SQLException e) {
            out.println("<p style='color: red'>❌ Ошибка БД: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }
    }
}
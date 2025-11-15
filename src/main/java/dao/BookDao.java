package dao;

import model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private static final String URL = "jdbc:postgresql://localhost:5432/bookdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "kanan123@@";

    static {
        initDatabase();
    }

    private static void initDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String sql = """
                CREATE TABLE IF NOT EXISTS books (
                    id SERIAL PRIMARY KEY,
                    title VARCHAR(255) NOT NULL,
                    author VARCHAR(255) NOT NULL,
                    year INTEGER,
                    isbn VARCHAR(20)
                )
                """;
            stmt.execute(sql);
            System.out.println("✅ Таблица books создана/проверена");

        } catch (SQLException e) {
            System.out.println("❌ Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY id";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setYear(rs.getInt("year"));
                book.setIsbn(rs.getString("isbn"));
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("❌ Ошибка при получении книг: " + e.getMessage());
        }
        return books;
    }

    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (title, author, year, isbn) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());

            if (book.getYear() != null) {
                stmt.setInt(3, book.getYear());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.setString(4, book.getIsbn());

            int result = stmt.executeUpdate();
            System.out.println("✅ Книга добавлена: " + book.getTitle());
            return result > 0;

        } catch (SQLException e) {
            System.out.println("❌ Ошибка при добавлении книги: " + e.getMessage());
            return false;
        }
    }
}
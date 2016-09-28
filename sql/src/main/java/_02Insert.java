import java.sql.*;

/**
 * Created by sf on 28.09.16.
 */
public class _02Insert {

    public static final String URL = "jdbc:mysql://localhost:3306/ACP14" +
            "?autoReconnect=true&useSSL=false&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD = "777";
    public static final String INSERT_QUERY = "INSERT INTO students(name, mail, birth, salary) VALUES (?,?,?,?)";

    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        Student student = new Student("Bob", "bob88@gmail.com", 5000, new java.util.Date());

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getMail());
            preparedStatement.setDate(3, new Date(student.getDate().getTime()));
            preparedStatement.setDouble(4, student.getSalary());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

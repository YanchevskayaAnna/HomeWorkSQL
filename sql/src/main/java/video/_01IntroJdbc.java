package video;

import java.sql.*;

/**
 * Created by sf on 28.09.16.
 */
public class _01IntroJdbc {

    public static final String URL = "jdbc:mysql://localhost:3306/ACP14" +
            "?autoReconnect=true&useSSL=false&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD = "777";
    public static final String ALL_USERS_QUERY = "SELECT * FROM students";

    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");


        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(ALL_USERS_QUERY);) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String mail = resultSet.getString("mail");
                double salary = resultSet.getDouble("salary");
                Date date = resultSet.getDate("birth");

                System.out.println(new Student(name, mail, salary, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

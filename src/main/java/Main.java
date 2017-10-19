import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try(Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/simple?useSSL=false", "nameuser", "nameuser")
        ) {

            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS names (\n" +
                    "     id MEDIUMINT NOT NULL AUTO_INCREMENT,\n" +
                    "     name TEXT NOT NULL,\n" +
                    "     PRIMARY KEY (id)\n" +
                    ");");


            PreparedStatement ps = connection.prepareStatement("INSERT INTO names (name) VALUES (?);");

            ps.setString(1, scanner.nextLine());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

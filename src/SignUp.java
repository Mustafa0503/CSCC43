import java.sql.*;
import java.util.Scanner;

public class SignUp {
    public static void performSignUp(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter a new username: ");
        String newUsername = scanner.next();

        System.out.print("Enter a new password: ");
        String newPassword = scanner.next();

        System.out.print("Are you registering as an admin? (y/n): ");
        String isAdminInput = scanner.next();
        boolean isAdmin = isAdminInput.equalsIgnoreCase("y");

        String insertQuery = "INSERT INTO users (username, password, admin) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, newUsername);
        preparedStatement.setString(2, newPassword);
        preparedStatement.setBoolean(3, isAdmin);

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Sign up successful! You can now log in.");
        } else {
            System.out.println("Sign up failed. Please try again.");
        }

        preparedStatement.close();
    }
}

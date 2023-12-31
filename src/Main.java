import javax.swing.text.html.Option;
import java.sql.*;

import java.util.Arrays;
import java.util.InputMismatchException;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    //for laptop: SOURCE C:\Users\musta\Desktop\C43Project\CSCC43\src\data.sql
    //for deskop: SOURCE C:\Users\musta\IdeaProjects\NC43\src\data.sql
    // drop tables users, user_listings, listings, listings_amenities
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1/C43";
        String username = "root";
        String password = "c43project";
        UserContext user = new UserContext();

        Logger.getLogger("edu.stanford.nlp").setLevel(Level.WARNING);

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner scanner = new Scanner(System.in);

            System.out.println("Select an option:");
            System.out.println("1. Log in as user");
            System.out.println("2. Log in as admin");
            System.out.println("3. Sign up");
            System.out.println("4. Extract noun phrases");
            System.out.println("5. Generate Report");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 1 || choice == 2) {
                // Log in
                System.out.print("Enter your username: ");
                String inputUsername = scanner.next();

                System.out.print("Enter your password: ");
                String inputPassword = scanner.next();

                boolean isUserLogin = choice == 1;
                boolean loginSuccessful = LogIn.loginUser(connection, inputUsername, inputPassword, isUserLogin);
                boolean isAdmin = LogIn.isAdminUser(connection, inputUsername);
                if (loginSuccessful) {
                    UserContext.setLoggedInUsername(inputUsername);
                    int userId = LogIn.getUserIdByUsername(connection, inputUsername);
                    UserContext.setLoggedInUserId(userId);
                }
                LogIn.displayLoginMessage(loginSuccessful, isAdmin, connection);

            } else if (choice == 3) {
                // Sign up
                SignUp.performSignUp(connection, scanner);

            } else if (choice == 4) {
                if (!checkIfTableExists(connection, "NounPhrases")) {
                    NounPhraseCount.createNounPhrasesTable(connection);
                }

                if (!checkIfTableExists(connection, "renterNounPhrases")) {
                    NounPhraseCount.createHostCommentsTable(connection);
                }

                NounPhraseCount.runNounPhrasesQuery(connection);
                NounPhraseCount.runRenterNounPhrasesQuery(connection);

            } else if (choice == 5) {
                Report.promptReport(connection);
            } else {
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }

            // Close resources
            scanner.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static boolean checkIfTableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet resultSet = metaData.getTables(null, null, tableName, null)) {
            return resultSet.next();
        }
    }

}

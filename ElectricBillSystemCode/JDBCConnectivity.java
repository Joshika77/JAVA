import java.sql.*;

public class DatabaseConnection {

    public static void main(String[] args) {
        // JDBC URL, username, and password
        String url = "jdbc:mysql://localhost:3306/your_database_name"; // Replace with your database URL
        String username = "root"; // Replace with your database username
        String password = "password"; // Replace with your database password

        // Declare the JDBC objects
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Step 1: Establish the connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully!");
            
            // Step 2: Create a Statement object
            statement = connection.createStatement();
            
            // Step 3: Execute a query
            String query = "SELECT * FROM users"; // Replace with your SQL query
            resultSet = statement.executeQuery(query);
            
            // Step 4: Process the results
            while (resultSet.next()) {
                // Fetch data from the result set
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("username");
                String userPassword = resultSet.getString("password");
                
                // Display the results
                System.out.println("User ID: " + userId);
                System.out.println("Username: " + userName);
                System.out.println("Password: " + userPassword);
            }
            
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
        } finally {
            // Step 5: Close the resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

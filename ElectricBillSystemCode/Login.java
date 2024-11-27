import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {
    
    // Swing components
    private JLabel lblUsername, lblPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    
    // JDBC components
    private Connection connection;
    private PreparedStatement pst;
    private ResultSet rs;
    
    // Constructor to setup the form
    public LoginPage() {
        // JFrame settings
        setTitle("Login Page");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        
        // Initialize components
        lblUsername = new JLabel("Username:");
        lblPassword = new JLabel("Password:");
        
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        
        btnLogin = new JButton("Login");

        // Add components to the frame
        add(lblUsername);
        add(txtUsername);
        add(lblPassword);
        add(txtPassword);
        add(btnLogin);
        
        // Event handling for the login button
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateLogin();
            }
        });
        
        // Initialize database connection
        connectToDatabase();
    }

    // Method to connect to the database
    private void connectToDatabase() {
        try {
            // Database URL, username, password (modify according to your setup)
            String url = "jdbc:mysql://localhost:3306/user_db";
            String username = "root";
            String password = "password";
            
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to validate the login credentials
    private void validateLogin() {
        String username = txtUsername.getText();
        char[] passwordArray = txtPassword.getPassword();
        String password = new String(passwordArray);

        // Validate inputs
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        try {
            // SQL query to validate the login
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            pst = connection.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);

            // Execute the query
            rs = pst.executeQuery();
            
            // Check if a matching record exists
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                // Redirect to another page (optional, here we just close the login window)
                dispose();
                // If you want to open a new window (like a dashboard), you can create another JFrame class and show it here.
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error. Please try again.");
        }
    }

    public static void main(String[] args) {
        // Create and display the login form
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
            }
        });
    }
}

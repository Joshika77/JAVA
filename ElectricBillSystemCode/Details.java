import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class DetailsPage extends JFrame {

    // Swing components
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnLoadData;
    
    // JDBC components
    private Connection connection;
    private PreparedStatement pst;
    private ResultSet rs;

    // Constructor to setup the form
    public DetailsPage() {
        // JFrame settings
        setTitle("Billing Details");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        table = new JTable();
        scrollPane = new JScrollPane(table);
        btnLoadData = new JButton("Load Billing Data");

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(btnLoadData, BorderLayout.SOUTH);

        // Event handling for the button to load data
        btnLoadData.addActionListener(e -> loadBillingData());

        // Initialize database connection
        connectToDatabase();
    }

    // Method to connect to the database
    private void connectToDatabase() {
        try {
            // Database URL, username, and password (modify according to your setup)
            String url = "jdbc:mysql://localhost:3306/billing_db";
            String username = "root";
            String password = "password";

            // Establish the connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to load billing data from the database and display it in the table
    private void loadBillingData() {
        try {
            // SQL query to select all billing details
            String query = "SELECT * FROM billing_details";
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();

            // Set table model to display data in tabular format
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Bill ID");
            model.addColumn("Customer Name");
            model.addColumn("Customer Address");
            model.addColumn("Amount");
            model.addColumn("Date");

            // Iterate through the result set and populate the table rows
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(String.valueOf(rs.getInt("bill_id")));
                row.add(rs.getString("customer_name"));
                row.add(rs.getString("customer_address"));
                row.add(String.valueOf(rs.getDouble("amount")));
                row.add(rs.getString("date"));
                model.addRow(row);
            }

            // Set the model to the table
            table.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading billing data.");
        }
    }

    public static void main(String[] args) {
        // Create and display the details page
        SwingUtilities.invokeLater(() -> {
            DetailsPage detailsPage = new DetailsPage();
            detailsPage.setVisible(true);
        });
    }
}

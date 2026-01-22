import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class HotelManagementSystemGUI extends JFrame implements ActionListener {
    private JComboBox<String> packageComboBox, genderComboBox, ticketTypeComboBox;
    private JTextField dateField, passengerField, nameField, surnameField, ageField, phoneField, emailField, addressField;
    private JButton submitButton;
    private int amountToPay;
    private String PASS;

    public HotelManagementSystemGUI() {
        setTitle("Hotel Management System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(205, 255, 204)); // Light green background

        PASS = JOptionPane.showInputDialog("Enter Password:");

        JLabel titleLabel = new JLabel("Welcome to Hotel Management System");
        titleLabel.setForeground(Color.blue);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(100, 10, 300, 20);
        add(titleLabel);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.black);
        nameLabel.setBounds(50, 50, 50, 20);
        add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 20);
        add(nameField);

        JLabel surnameLabel = new JLabel("Surname:");
        surnameLabel.setForeground(Color.black);
        surnameLabel.setBounds(50, 80, 70, 20);
        add(surnameLabel);
        surnameField = new JTextField();
        surnameField.setBounds(150, 80, 200, 20);
        add(surnameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setForeground(Color.black);
        ageLabel.setBounds(50, 110, 50, 20);
        add(ageLabel);
        ageField = new JTextField();
        ageField.setBounds(150, 110, 50, 20);
        add(ageField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setForeground(Color.black);
        genderLabel.setBounds(50, 140, 70, 20);
        add(genderLabel);
        String[] genders = {"Male", "Female"};
        genderComboBox = new JComboBox<>(genders);
        genderComboBox.setBounds(150, 140, 100, 20);
        add(genderComboBox);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setForeground(Color.black);
        phoneLabel.setBounds(50, 170, 70, 20);
        add(phoneLabel);
        phoneField = new JTextField();
        phoneField.setBounds(150, 170, 200, 20);
        add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.black);
        emailLabel.setBounds(50, 200, 70, 20);
        add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(150, 200, 200, 20);
        add(emailField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setForeground(Color.black);
        addressLabel.setBounds(50, 230, 70, 20);
        add(addressLabel);
        addressField = new JTextField();
        addressField.setBounds(150, 230, 200, 20);
        add(addressField);

        JLabel packageLabel = new JLabel("Select Room Package:");
        packageLabel.setForeground(Color.black);
        packageLabel.setBounds(50, 260, 150, 20);
        add(packageLabel);
        String[] packages = {"Standard Room", "Deluxe Room", "Suite Room", "Presidential Suite"};
        packageComboBox = new JComboBox<>(packages);
        packageComboBox.setBounds(190, 260, 200, 20);
        add(packageComboBox);

        JLabel dateLabel = new JLabel("Enter Date (DD/MM/YY):");
        dateLabel.setForeground(Color.black);
        dateLabel.setBounds(50, 290, 150, 20);
        add(dateLabel);
        dateField = new JTextField();
        dateField.setBounds(210, 290, 150, 20);
        add(dateField);

        JLabel passengerLabel = new JLabel("Enter Number Of Guests:");
        passengerLabel.setForeground(Color.black);
        passengerLabel.setBounds(50, 320, 200, 20);
        add(passengerLabel);
        passengerField = new JTextField();
        passengerField.setBounds(220, 320, 50, 20);
        add(passengerField);

        JLabel ticketTypeLabel = new JLabel("Select Meal Plan:");
        ticketTypeLabel.setForeground(Color.black);
        ticketTypeLabel.setBounds(50, 350, 150, 20);
        add(ticketTypeLabel);
        String[] ticketTypes = {"Standard", "Full Board", "Half Board"};
        ticketTypeComboBox = new JComboBox<>(ticketTypes);
        ticketTypeComboBox.setBounds(160, 350, 150, 20);
        add(ticketTypeComboBox);

        submitButton = new JButton("Submit");
        submitButton.setBounds(180, 390, 100, 30);
        submitButton.addActionListener(this);
        submitButton.setBackground(new Color(0, 153, 51)); // Dark green background
        submitButton.setForeground(Color.white);
        add(submitButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String email = emailField.getText();
            String address = addressField.getText();

            // Input validation
            if (!name.matches("\\S+")) {
                JOptionPane.showMessageDialog(this, "Name must not contain spaces.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!surname.matches("\\S+")) {
                JOptionPane.showMessageDialog(this, "Surname must not contain spaces.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
                JOptionPane.showMessageDialog(this, "Invalid email address.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (address.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Address must not be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String phone = phoneField.getText();
            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 digits.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageField.getText());
                if (age < 0 || age > 150) {
                    JOptionPane.showMessageDialog(this, "Age must be between 0 and 150.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Age must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String date = dateField.getText();
            if (!date.matches("\\d{2}/\\d{2}/\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Date must be in the format DD/MM/YY.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int numberOfGuests;
            try {
                numberOfGuests = Integer.parseInt(passengerField.getText());
                if (numberOfGuests < 1 || numberOfGuests > 50) {
                    JOptionPane.showMessageDialog(this, "Number of guests must be between 1 and 50.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Number of guests must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String gender = (String) genderComboBox.getSelectedItem();
            String selectedPackage = (String) packageComboBox.getSelectedItem();
            String selectedDate = dateField.getText();
            String selectedMealPlan = (String) ticketTypeComboBox.getSelectedItem();

            // Calculate amount to pay based on selected package and meal plan
            if (selectedPackage.equals("Standard Room"))
                amountToPay = 5000;
            else if (selectedPackage.equals("Deluxe Room"))
                amountToPay = 10000;
            else if (selectedPackage.equals("Suite Room"))
                amountToPay = 20000;
            else if (selectedPackage.equals("Presidential Suite"))
                amountToPay = 50000;

            if (selectedMealPlan.equals("Full Board"))
                amountToPay += 3000;
            else if (selectedMealPlan.equals("Half Board"))
                amountToPay += 1500;

            JOptionPane.showMessageDialog(this, "Booking successful! Total amount: " + amountToPay);

            // Generate Receipt
            generateReceipt(name, surname, gender, phone, email, address, selectedPackage, selectedMealPlan, numberOfGuests, selectedDate);
        }
    }

    private void generateReceipt(String name, String surname, String gender, String phone, String email, String address,
                                 String roomPackage, String mealPlan, int guests, String date) {
        JFrame receiptFrame = new JFrame("Booking Receipt");
        receiptFrame.setSize(400, 350);
        receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        receiptFrame.setLayout(new BorderLayout());

        JTextArea receiptText = new JTextArea();
        receiptText.setEditable(false);
        receiptText.setFont(new Font("Arial", Font.PLAIN, 12));
        receiptText.setText("** HOTEL BOOKING RECEIPT **\n\n");
        receiptText.append("Name: " + name + " " + surname + "\n");
        receiptText.append("Gender: " + gender + "\n");
        receiptText.append("Phone: " + phone + "\n");
        receiptText.append("Email: " + email + "\n");
        receiptText.append("Address: " + address + "\n");
        receiptText.append("Date of Stay: " + date + "\n");
        receiptText.append("Room Package: " + roomPackage + "\n");
        receiptText.append("Meal Plan: " + mealPlan + "\n");
        receiptText.append("Number of Guests: " + guests + "\n");
        receiptText.append("Total Amount: " + amountToPay + " units\n");

        receiptFrame.add(new JScrollPane(receiptText), BorderLayout.CENTER);
        receiptFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new HotelManagementSystemGUI();
    }
}
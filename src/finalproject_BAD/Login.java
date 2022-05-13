package finalproject_BAD;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame implements ActionListener{

	JPanel loginLabelp, emailLabelp, passwordLabelp, emailFieldp, passwordFieldp, loginButtonp, signupButtonp, formPanel, panelbutton;
	JLabel loginLabel, emailLabel, passwordLabel;
	JPasswordField passwordField;
	JTextField emailField;
	JButton loginButton, signupButton;
	Vector<User> userVector;
	DatabaseConnection db;

	
	
	public Login(DatabaseConnection db) {
		//populate Vector
		this.db = db;
		
		
		loginLabel = new JLabel();
		loginLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		loginLabelp = new JPanel();
		loginLabelp.setBorder(new EmptyBorder(90,10,30,10));
//		loginLabelp.setBackground(Color.green);
		loginLabel.setText("LOGIN");
		loginLabelp.add(loginLabel);
		emailLabel = new JLabel();
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		emailLabel.setText("Email :");
		emailLabelp = new JPanel();
		emailLabelp.setLayout(new FlowLayout(FlowLayout.LEFT));
		emailLabelp.setBorder(new EmptyBorder(30,30,0,0));
		emailLabelp.add(emailLabel);
		passwordLabel = new JLabel();
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passwordLabel.setText("Password :");
		passwordLabelp = new JPanel();
		passwordLabelp.setLayout(new FlowLayout(FlowLayout.LEFT));
		passwordLabelp.setBorder(new EmptyBorder(0,30,30,0));
		passwordLabelp.add(passwordLabel);
		emailField = new JTextField();
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		emailField.setPreferredSize(new Dimension(150,30));
		emailFieldp = new JPanel();
		emailFieldp.setPreferredSize(new Dimension(150,30));
		emailFieldp.setBorder(new EmptyBorder(30,0,0,0));
		emailFieldp.add(emailField);
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passwordField.setEchoChar('*');
		passwordField.setPreferredSize(new Dimension(150,30));
		passwordFieldp = new JPanel();
		passwordFieldp.setPreferredSize(new Dimension(150,30));
		passwordFieldp.setBorder(new EmptyBorder(0,0,30,0));
		passwordFieldp.add(passwordField);
		
		formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(2,2));
		
		formPanel.add(emailLabelp);
		formPanel.add(emailFieldp);
		formPanel.add(passwordLabelp);
		formPanel.add(passwordFieldp);
		
		loginButton = new JButton();
		loginButton.setFont(new Font("Tahoma", Font.BOLD, 20));
//		loginButton.setFont(fonta);
		loginButton.setText("Login");
		loginButton.setPreferredSize(new Dimension(150,40));
		loginButton.addActionListener(this);
		loginButtonp = new JPanel();
		loginButtonp.setPreferredSize(new Dimension(150,40));
//		loginButton.setBorder(new EmptyBorder(0,10,0,10));
		loginButtonp.add(loginButton);
		JPanel loginPanel = new JPanel();
		loginPanel.add(loginButtonp);
		
		signupButton = new JButton();
		signupButton.setText("Create Account");
		signupButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		signupButton.setPreferredSize(new Dimension(200,40));
		signupButton.addActionListener(this);
		signupButtonp = new JPanel();
		signupButtonp.setPreferredSize(new Dimension(200,40));
//		signupButtonp.setBorder(new EmptyBorder(0,10,0,10));
		signupButtonp.add(signupButton);
		JPanel signPanel = new JPanel();
		signPanel.add(signupButtonp);
		
		panelbutton = new JPanel(new GridLayout(1,2));
		panelbutton.setBorder(new EmptyBorder(20,0,20,0));
		panelbutton.add(loginPanel);
		panelbutton.add(signPanel);
		
		JPanel allFormPanel = new JPanel();
		allFormPanel.add(panelbutton);
		
		getContentPane().add(loginLabelp, BorderLayout.NORTH);
		getContentPane().add(formPanel, BorderLayout.CENTER);
		getContentPane().add(allFormPanel, BorderLayout.SOUTH);
		
//		setLayout(new GridLayout(3,1));
		setTitle("Stophee");
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(500,500);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == loginButton) {
			if (nullValidation()) {
//				System.out.println("Validasi email dan password");
				String email = emailField.getText();
				String password = String.valueOf(passwordField.getPassword());
				
				User getUser = getUser(email, password);
				if (getUser != null) {
					new Mainmenu(getUser, db);
					this.dispose();
					
				} else {						
					JOptionPane.showMessageDialog(this, "wrong email or password", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}			
		} if (e.getSource() == signupButton) {
			new Register(db);
			this.dispose();
		}
		
	}
	
	private boolean nullValidation() {
		// TODO Auto-generated method stub
		if (!emailField.getText().isEmpty()) {
//			System.out.println("Ini password " + String.valueOf(passwordField.getPassword()));
			if (!(passwordField.getPassword().length == 0)){
				return true;
			} else {
//				System.out.println("Masuk");
				JOptionPane.showMessageDialog(this, "Please input your Password", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please input your email", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
		
	}

	private User getUser(String email, String password) {
		// TODO Auto-generated method stub
		
		db.rs = db.getuser();
		userVector = new Vector<User>();
		User newUser = null;
		try {
			while (db.rs.next()) {
				userVector.add(new User(String.valueOf(db.rs.getObject("name")), String.valueOf(db.rs.getObject("email")), String.valueOf(db.rs.getObject("password")), String.valueOf(db.rs.getObject("phone")), String.valueOf(db.rs.getObject("gender")), String.valueOf(db.rs.getObject("Role"))));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (User user : userVector) {
			if (email.equals(user.getUserEmail())) {
				if (password.equals(user.getUserPassword())) {
					newUser = user;
				} 
			}
			
		}
		return newUser;
		
	}
	

}

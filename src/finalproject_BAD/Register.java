package finalproject_BAD;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Register extends JFrame implements ActionListener{

	JLabel titleLabel, nameLabel, emailLabel, passwordLabel, phoneLabel, genderLabel;
	JTextField nameField, emailField, phoneField;
	JPasswordField password;
	JRadioButton radioMale, radioFemale;
	ButtonGroup gender;
	JButton submit;
	JPanel north, center, south, genderPanel, submitPanel;
	DatabaseConnection db;
	ResultSet rs;
	
	public Register(DatabaseConnection db) {
		this.db = db;

		titleLabel = new JLabel("Register");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		emailLabel = new JLabel("Email : ");
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		phoneLabel = new JLabel("Phone Number");
		phoneLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		genderLabel = new JLabel("Gender");
		genderLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		nameField = new JTextField();
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameField.setPreferredSize(new Dimension(150,30));
		emailField = new JTextField();
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		emailField.setPreferredSize(new Dimension(150,30));
		password = new JPasswordField();
		password.setFont(new Font("Tahoma", Font.PLAIN, 18));
		password.setPreferredSize(new Dimension(150,30));
		password.setEchoChar('*');
		phoneField = new JTextField();
		phoneField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		phoneField.setPreferredSize(new Dimension(150,30));
		
		radioMale = new JRadioButton("Male");
		radioMale.setFont(new Font("Tahoma", Font.PLAIN, 18));
		radioFemale	= new JRadioButton("Female");
		radioFemale.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		gender = new ButtonGroup();
		gender.add(radioMale);
		gender.add(radioFemale);
		radioMale.setSelected(true);
		
		genderPanel = new JPanel(new FlowLayout());
		genderPanel.add(radioMale);
		genderPanel.add(radioFemale);
		
		nameLabel = new JLabel("Name : ");
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		north = new JPanel(new FlowLayout());
		north.add(titleLabel);
		north.setBorder(new EmptyBorder(50,0,50,0));
		
		center = new JPanel();
		center.setLayout(new GridLayout(5,2,50,20));
		center.add(nameLabel);
		center.add(nameField);
		center.add(emailLabel);
		center.add(emailField);
		center.add(passwordLabel);
		center.add(password);
		center.add(phoneLabel);
		center.add(phoneField);
		center.add(genderLabel);
		center.add(genderPanel);
		center.setBorder(new EmptyBorder(0,10,0,10));
		
		south = new JPanel(new FlowLayout());
		south.setBorder(new EmptyBorder(20,0,20,0));
		submit = new JButton("Submit");
		submit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		submit.addActionListener(this);
		south.add(submit);
		
		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(center, BorderLayout.CENTER);
		getContentPane().add(south, BorderLayout.SOUTH);
		
		this.pack();
		setTitle("Stophee");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setSize(400,500);
		setResizable(false);	
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == submit) {
			 submit();
		}
	}
	
	public boolean checkEmail(String email) {
		rs = db.getuser();
		try {
			while (rs.next()) {
				if(rs.getObject(3).toString().equals(email)) {
				return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public void submit() {
		boolean valid = true;
		String name = "", email = "", phone="", Password="", gender="", role="";
		if (nameField.getText().length() >= 3 && nameField.getText().length() <= 30) {
			name = nameField.getText();
		} else {
			valid = false;
			JOptionPane.showMessageDialog(this,"Please Input name more than 3 char and less than 30 char", "Error message", JOptionPane.ERROR_MESSAGE);
		}
	if (checkEmail(emailField.getText())) {
		
		if(emailField.getText().indexOf(".") - emailField.getText().indexOf("@") != 1) {
			
			if (countMatches(emailField.getText(), "@") == 1) {
				if (!(emailField.getText().startsWith("@") || emailField.getText().startsWith(".")) && !(emailField.getText().endsWith("@") || emailField.getText().endsWith("."))) {					
					
					String afterAdd1 = emailField.getText().substring(emailField.getText().indexOf("@"));
					if (emailField.getText().substring(emailField.getText().indexOf("@")+1).contains(".") && (afterAdd1.indexOf(".") == afterAdd1.lastIndexOf("."))) {
						email = emailField.getText();
					}else {
						valid = false;
						JOptionPane.showMessageDialog(this, "Must contain exactly one ‘.’ after ‘@’ for separating [provider] and [domain].", "Error", JOptionPane.INFORMATION_MESSAGE);	
					}	
				}else {
					valid = false;
					JOptionPane.showMessageDialog(this, "Must not starts and ends with ‘@’ nor ‘.’", "Error", JOptionPane.INFORMATION_MESSAGE);					
				}
			}else {
				valid = false;
				JOptionPane.showMessageDialog(this, "Must contain exactly one ‘@’", "Error", JOptionPane.INFORMATION_MESSAGE);									
			}
		}else {
			valid = false;
			JOptionPane.showMessageDialog(this, "Character ‘@’ must not be next to ‘.’", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	} else {
		valid = false;
		JOptionPane.showMessageDialog(this, "Your email has been used", "Error", JOptionPane.INFORMATION_MESSAGE);
	}

		
		if (phoneField.getText().length() >= 12 && phoneField.getText().length() <= 15) {
			try { // check phone number is numeric
				Long.parseLong(phoneField.getText());
				phone += Long.parseLong(phoneField.getText());
			} catch (Exception e) {				
				valid = false;
				JOptionPane.showMessageDialog(this, "Phone number must be numeric!", "Error", JOptionPane.INFORMATION_MESSAGE);
			}	
		} else {
			valid = false;
			JOptionPane.showMessageDialog(this, "Phone must consist only 12 - 15 digits only", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if (String.valueOf(password.getPassword()).length() >= 5 && String.valueOf(password.getPassword()).length() <= 20 && checkAlphabetic(password.getPassword()) && checkNumeric(password.getPassword())) {
			Password = String.valueOf(password.getPassword());
			
		} else {
			valid = false;
			JOptionPane.showMessageDialog(this, "Password must 5 - 20 length of character and digit.", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if (radioFemale.isSelected()) {
			gender = "Female";
		}
		else if (radioMale.isSelected()) {
			gender = "Male";
		}
		role = "User";
		if (valid) {			
			JOptionPane.showMessageDialog(this, "Account Created !");
			
		User newUser = new User(name,email,Password,phone,gender,role);
		db.insertuser(newUser);
		new Login(db);
		this.dispose();
		
		
		} 
				
	}

	//referenced from stackoverflow
	public boolean checkAlphabetic(char[] password) {
		boolean alpa = false;
		
		char[] pw = password;
		for (char c : pw) {
			int ascii = (int) c;
			if (ascii >= 'a' && ascii <= 'z') {
				alpa = true;
				break;
			}
		}
		return alpa;
	}
	//referenced from stackoverflow
	public boolean checkNumeric(char[] password) {
		boolean alpa = false;
		char[] pw = password;

		for (char c : pw) {
			int ascii = (int) c;
			if (ascii >= '0' && ascii <= '9') {
				alpa = true;
				break;
			}
		}
		return alpa;
	}
	
	//referenced from stackoverflow
	public static int countMatches(String mainString, String whatToFind){
	    String tempString = mainString.replaceAll(whatToFind, "");
	    //this even work for on letter
	    int times = (mainString.length()-tempString.length())/whatToFind.length();

	    //times should be 4
	    return times;
	}
	


}

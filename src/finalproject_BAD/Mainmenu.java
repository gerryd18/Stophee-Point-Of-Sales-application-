package finalproject_BAD;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Mainmenu implements ActionListener{

	DatabaseConnection db;
	JInternalFrame producttypemanagement, productmanagement, sales, transaction; 
	
	public Mainmenu(User user, DatabaseConnection db) {
		transaction = new Transaction(db);
		transaction.setVisible(true);
		productmanagement = new ProductManagement(db);
		productmanagement.setVisible(true);
		producttypemanagement = new ProductCategoryManagement(db);
		producttypemanagement.setVisible(true);
		sales = new salesTransaction(user,db);
		sales.setVisible(true);
		// TODO Auto-generated constructor stub
		this.db = db;
		
		if (user.getUserRole().equals("User")) {
			JFrame userframe = new JFrame("Stophee");
			userframe.setSize(1000,800);
			userframe.setVisible(true);
			userframe.setResizable(false);
			userframe.setLocationRelativeTo(null);
			userframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			JMenuBar menubar = new JMenuBar();
			JMenu Account = new JMenu("Account");
			JMenu Buy = new JMenu("Buy");
			Buy.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					userframe.remove(transaction);
					userframe.add(sales);
				}
			});
			JMenu Transaction = new JMenu("Transaction");
			Transaction.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					userframe.remove(sales);
					userframe.add(transaction);
				}
			});
			JMenuItem logout = new JMenuItem("Log out");
			logout.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
						new Login(db);
						userframe.dispose();
				}
			});
			Account.add(logout);
			menubar.add(Account);
			menubar.add(Buy);
			menubar.add(Transaction);
			
			userframe.setJMenuBar(menubar);
			
		} else if (user.getUserRole().equals("Admin")) {
			JFrame adminframe = new JFrame("Stophee");
			adminframe.setSize(900,500);
			adminframe.setVisible(true);
			adminframe.setResizable(false);
			adminframe.setLocationRelativeTo(null);
			adminframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			JMenuBar menubar = new JMenuBar();
			JMenu Account = new JMenu("Account");
			JMenu Manage = new JMenu("Manage");
			
			JMenuItem logout = new JMenuItem("Log out");
			logout.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (e.getSource() == logout) {
						new Login(db);
						adminframe.dispose();
					
					}
				}
			});
			JMenuItem product = new JMenuItem("Product");
			product.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					adminframe.remove(producttypemanagement);
					adminframe.add(productmanagement);
					
				}
			});
			
			JMenuItem Producttype = new JMenuItem("Product Type");			
			Producttype.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
//					new ProductCategoryManagement(db);
					adminframe.remove(productmanagement);
					adminframe.add(producttypemanagement);
					
				}
			});
			
			Account.add(logout);
			Manage.add(product);
			Manage.add(Producttype);
			
			menubar.add(Account);
			menubar.add(Manage);
			
			
			adminframe.setJMenuBar(menubar);
			
			
			new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			};
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

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
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ProductCategoryManagement extends JInternalFrame implements ActionListener {

	JLabel titleLabel, typeLabel;
	JTable bodyTable;
	JTextField typeText;
	JButton addButton, updateButton, deleteButton;
	JPanel head, body, foot, pembungkusForm, pembungkus, footerPemb;
	DatabaseConnection db;
	DefaultTableModel dtm;
	Vector<Vector<Object>> productCategoryVector;
	int indexupdate;
	String idupd, cateName;

	public ProductCategoryManagement(DatabaseConnection db) {
		super("ProductCategoryManagement", false, false, false);
		this.db = db;
		this.db.rs = db.getProductCategory();
//		System.out.println("Masuk");
		// TODO Auto-generated constructor stub

		titleLabel = new JLabel("Manage Product Type");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		head = new JPanel(new FlowLayout(FlowLayout.CENTER));
		head.setBorder(new EmptyBorder(10, 10, 20, 10));
		head.add(titleLabel);

		productCategoryVector = new Vector<Vector<Object>>();
		Vector<Object> titleLabel = new Vector<Object>();
		titleLabel.add("Id");
		titleLabel.add("Product Type Name");

		try {
			while (db.rs.next()) {
				Vector<Object> productcate = new Vector<Object>();
				productcate.add(db.rs.getObject(1));
				productcate.add(db.rs.getObject(2));
				productCategoryVector.add(productcate);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error di populate inner vector");
			e.printStackTrace();
		}

		dtm = new DefaultTableModel(productCategoryVector, titleLabel);

		bodyTable = new JTable(dtm) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		bodyTable.addMouseListener(new MouseListener() {

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
				indexupdate = bodyTable.getSelectedRow();
//					System.out.println(bodyTable.getSelectedRow());
//					System.out.println(bodyTable.getValueAt(indexupdate));
				if (indexupdate >= 0) {
//						System.out.println("masuk a");
//						System.out.println(indexupdate);
					// updcat = new Vector<Object>();
					idupd = bodyTable.getValueAt(indexupdate, 0).toString();
					cateName = bodyTable.getValueAt(indexupdate, 1).toString();
					typeText.setText(cateName);
					updateButton.setEnabled(true);
					bodyTable.clearSelection();
				}

				else {
					idupd = null;
					cateName = null;
					bodyTable.clearSelection();
					typeText.setText("");
					updateButton.setEnabled(false);
				}
			}
		});

		bodyTable.setPreferredScrollableViewportSize(new Dimension(480, 150));
		JScrollPane sp = new JScrollPane(bodyTable);
		sp.setVisible(true);
		typeLabel = new JLabel("Type : ");
		typeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

		typeText = new JTextField();

		pembungkus = new JPanel();
		pembungkusForm = new JPanel(new GridLayout(1, 2));
		pembungkusForm.setPreferredSize(new Dimension(400, 40));
		pembungkusForm.add(typeLabel);
		pembungkusForm.add(typeText);
		pembungkus.add(pembungkusForm);

		body = new JPanel(new GridLayout(2, 1, 0, 10));
		body.setBorder(new EmptyBorder(10, 10, 20, 10));
		body.add(sp);
		body.add(pembungkus);

		addButton = new JButton("Add");
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addButton.addActionListener(this);
		updateButton = new JButton("Update");
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		updateButton.setEnabled(false);
		updateButton.addActionListener(this);
		deleteButton = new JButton("Delete");
		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deleteButton.addActionListener(this);

		foot = new JPanel(new FlowLayout());
		foot.setBorder(new EmptyBorder(20, 10, 10, 10));

		footerPemb = new JPanel(new GridLayout(1, 3, 10, 0));
		footerPemb.setPreferredSize(new Dimension(400, 30));
		footerPemb.add(addButton);
		footerPemb.add(updateButton);
		footerPemb.add(deleteButton);

		foot.add(footerPemb);

		getContentPane().add(head, BorderLayout.NORTH);
		getContentPane().add(body, BorderLayout.CENTER);
		getContentPane().add(foot, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addButton) {
			add();
		}
		if (e.getSource() == updateButton) {
			update();
		}

		if (e.getSource() == deleteButton) {
			delete();
		}
	}
	
	public void add() {
		String type = typeText.getText();
		db.insertProductType(type);

		db.rs = db.getProductCategory();
		Object[] newupd = new Object[2];
		try {
			while (db.rs.next()) {
				if (productCategoryVector.isEmpty()) {
					newupd[0] = db.rs.getObject(1);
					newupd[1] = db.rs.getObject(2);
				} else {
					for (Vector<Object> vector : productCategoryVector) {
						if (String.valueOf(vector.get(0)).equals(db.rs.getObject(1))) {
							continue;
						} else {
							newupd[0] = db.rs.getObject(1);
							newupd[1] = db.rs.getObject(2);
						}

					}
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dtm.addRow(newupd);
	}
	
	void update() {
		if (!(idupd == null && cateName == null)) {
			if (cateName.equals(typeText.getText())) {
				JOptionPane.showMessageDialog(this, "you havent change the value", "Message",
						JOptionPane.ERROR_MESSAGE);
			} else {
				db.updateProductCategory(idupd, typeText.getText());
				bodyTable.setValueAt(typeText.getText(), indexupdate, 1);
			}
		}
		idupd = null;
		cateName = null;
	}
	
	void delete() {
		if (!(idupd == null)) {
			db.deleteProductCategory(idupd);
			dtm.removeRow(indexupdate);
		}
	}
}

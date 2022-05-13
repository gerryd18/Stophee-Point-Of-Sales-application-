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
import java.text.ParseException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

public class ProductManagement extends JInternalFrame implements ActionListener {
	Product selectedrow;
	String selectedId;
	int index;

	DatabaseConnection db;
	JPanel header, body, footer, form;
	JTable table;
	JScrollPane scrollpane;
	Vector<Vector<Object>> datatabel = new Vector<Vector<Object>>();
	JButton add, update, delete;
	DefaultTableModel dtm;

	JLabel titleLabel, productNameLabel, productTypeLabel, productPriceLabel, productQuantityLabel;
	JTextField productNameField;
	JComboBox<String> ProducttypeCombobox;
	JSpinner productPriceSpinner, productQuantitySpinner;
	Vector<Vector<Object>> vectorCombo = new Vector<Vector<Object>>();
	Vector<String> listcombo = new Vector<String>();

	public ProductManagement(DatabaseConnection db) {
		super("Product Management", false, false, false);
		this.db = db;

		Vector<Object> tableHeader = new Vector<>();
		tableHeader.add("ProductId");
		tableHeader.add("ProductName");
		tableHeader.add("ProductTypeName");
		tableHeader.add("ProductPrice");
		tableHeader.add("ProductQuantity");

		this.db.rs = db.getproduct();

		try {
			while (db.rs.next()) {
				Vector<Object> record = new Vector<Object>();
				record.add(db.rs.getObject("ID"));
				record.add(db.rs.getObject("productname"));
				record.add(db.rs.getObject("productTypeName"));
				record.add(db.rs.getObject("productPrice"));
				record.add(db.rs.getObject("productQuantity"));
				datatabel.add(record);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Font fontHeader = new Font("SansSerif", Font.BOLD, 20);
		titleLabel = new JLabel("Manage Product");
		titleLabel.setFont(fontHeader);

		header = new JPanel(new FlowLayout(FlowLayout.CENTER));
		header.add(titleLabel);

		dtm = new DefaultTableModel(datatabel, tableHeader);
		table = new JTable(dtm) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		table.getTableHeader().setReorderingAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(480, 150));
		scrollpane = new JScrollPane(table);
		scrollpane.setVisible(true);

		productNameLabel = new JLabel("Product Name");
		productTypeLabel = new JLabel("Product Type");
		productPriceLabel = new JLabel("Product Price");
		productQuantityLabel = new JLabel("Product Quantity");

		productNameField = new JTextField();
		productNameField.setPreferredSize(new Dimension(150, 30));

		this.db.rs = this.db.getProductCategory();

		try {
			while (db.rs.next()) {
				Vector<Object> datarecord = new Vector<Object>();
				datarecord.add(db.rs.getObject(1));
				datarecord.add(db.rs.getObject(2));

				vectorCombo.add(datarecord);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < vectorCombo.size(); i++) {
			listcombo.add(vectorCombo.get(i).get(1).toString());
		}

		ProducttypeCombobox = new JComboBox<>(listcombo);
		ProducttypeCombobox.setPreferredSize(new Dimension(150, 30));
		productPriceSpinner = new JSpinner(new SpinnerNumberModel(new Integer(1000), new Integer(1), null, new Integer(500)));
		productQuantitySpinner = new JSpinner(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		
		
		table.addMouseListener(new MouseListener() {

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
				int row = table.getSelectedRow();
				index = row;

				String productId = table.getValueAt(row, 0).toString();
				String productname = table.getValueAt(row, 1).toString();
				String producttypeId = db.findproducttypeId(productId)[0];
				String productprice = table.getValueAt(row, 3).toString();
				String productquant = table.getValueAt(row, 4).toString();

				productNameField.setText(productname);
				ProducttypeCombobox.setSelectedIndex((Integer.valueOf(producttypeId) - 1));
				productPriceSpinner.setValue(Integer.valueOf(productprice));
				productQuantitySpinner.setValue(Integer.valueOf(productquant));

				selectedId = productId;
			}
		});

		form = new JPanel(new GridLayout(4, 2, 0, 5));
		form.add(productNameLabel);
		form.add(productNameField);
		form.add(productTypeLabel);
		form.add(ProducttypeCombobox);
		form.add(productPriceLabel);
		form.add(productPriceSpinner);
		form.add(productQuantityLabel);
		form.add(productQuantitySpinner);
		
		body = new JPanel(new GridLayout(2, 1, 0, 10));
		body.setBorder(new EmptyBorder(15, 10, 10, 15));
		body.add(scrollpane);
		body.add(form);
		
		add = new JButton("Add");
		add.addActionListener(this);
		update = new JButton("Update");
		update.addActionListener(this);
		delete = new JButton("Delete");
		delete.addActionListener(this);
		footer = new JPanel(new FlowLayout());
		
		footer.add(add);
		footer.add(update);
		footer.add(delete);

		getContentPane().add(header, BorderLayout.NORTH);
		getContentPane().add(body, BorderLayout.CENTER);
		getContentPane().add(footer, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == add) {
			add();
		} else if (e.getSource() == update) {
			update();
		} else if (e.getSource() == delete) {
			db.deleteproduct(selectedId);
			dtm.removeRow(index);
		}
	}

	void update() {
		int ind = ProducttypeCombobox.getSelectedIndex();
		String productTypeId = vectorCombo.get(ind).get(0).toString();
		String productName = productNameField.getText();
		String productPrice = productPriceSpinner.getValue().toString();
		String productQuantity = productQuantitySpinner.getValue().toString();
		selectedrow = new Product(productTypeId, productName, productPrice, Integer.valueOf(productQuantity));
		db.updateproduct(selectedId, selectedrow);

		table.setValueAt(selectedId, index, 0);
		table.setValueAt(productName, index, 1);
		table.setValueAt(db.findproducttypeId(selectedId)[1], index, 2);
		table.setValueAt(productPrice, index, 3);
		table.setValueAt(productQuantity, index, 4);
	}

	void add() {
		if (validation()) {
			int ind = ProducttypeCombobox.getSelectedIndex();
			String productTypeId = vectorCombo.get(ind).get(0).toString();
			String productName = productNameField.getText();
			String productPrice = productPriceSpinner.getValue().toString();
			String productQuantity = productQuantitySpinner.getValue().toString();

			db.insertproduct(new Product(productTypeId, productName, productPrice, Integer.valueOf(productQuantity)));
			JOptionPane.showMessageDialog(this, "Product Added!");
			db.rs = db.getproduct();
			Vector<Object> record = null;
			try {
				while (db.rs.next()) {
					if (datatabel.isEmpty()) {
						record = new Vector<Object>();
						record.add(db.rs.getObject("ID"));
						record.add(db.rs.getObject("productname"));
						record.add(db.rs.getObject("productTypeName"));
						record.add(db.rs.getObject("productPrice"));
						record.add(db.rs.getObject("productQuantity"));
						dtm.addRow(record);
					} else {
						for (Vector<Object> vector : datatabel) {
							if (String.valueOf(vector.get(0)).equals(db.rs.getObject(1))) {
								continue;
							} else {
								record = new Vector<Object>();
								record.add(db.rs.getObject("ID"));
								record.add(db.rs.getObject("productname"));
								record.add(db.rs.getObject("productTypeName"));
								record.add(db.rs.getObject("productPrice"));
								record.add(db.rs.getObject("productQuantity"));
							}
						}
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dtm.addRow(record);
		}
	}

	private boolean validation() {
		if (productNameField.getText().length() > 5 && productNameField.getText().length() < 15) {
			if (Integer.valueOf(productPriceSpinner.getValue().toString()) > 0) {
				if (Integer.valueOf(productQuantitySpinner.getValue().toString()) > 0) {
					return true;
				} else {
					JOptionPane.showMessageDialog(this, "Quantity must be more than 0", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Price must be more than 0", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Name length must be between 5 and 15", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

}

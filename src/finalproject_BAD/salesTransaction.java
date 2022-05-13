package finalproject_BAD;

import java.awt.BorderLayout;
import java.awt.Dialog;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class salesTransaction extends JInternalFrame implements ActionListener {

	JSpinner quantitySpinner;
	JRadioButton cash, debitOrCredit;
	ButtonGroup paymentGroup;
	JPanel north, centre, south, centreup, radioButPanel, centrebody, centredown, radioMerger;
	DatabaseConnection db;
	JTable productTable, cartTable;
	JScrollPane jspProduct, jspCart;
	JButton addToCart, CheckOut;
	Product selectedproduct;
	JLabel headerLabel, quantityLabel, paymentTypeLabel, productListLabel, cartListLabel;
	String selectedProductId;
	DefaultTableModel dtm1, dtm2;
	User user;

	public salesTransaction(User user, DatabaseConnection db) {
		super("Buy Product", false, false, false);

		this.user = user;
		this.db = db;
		// TODO Auto-generated constructor stub

		headerLabel = new JLabel("Buy Product");
		headerLabel.setFont(new Font("Tahoma", Font.BOLD, 30));

		quantityLabel = new JLabel("Quantity : ");
		quantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		quantityLabel.setPreferredSize(new Dimension(150, 30));
		paymentTypeLabel = new JLabel("Payment Type : ");
		paymentTypeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		paymentTypeLabel.setPreferredSize(new Dimension(150, 30));

		quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 0, null, 1));
		quantitySpinner.setFont(new Font("sansserif", Font.BOLD, 15));
		quantitySpinner.setPreferredSize(new Dimension(150, 30));

		cash = new JRadioButton("Cash");
		debitOrCredit = new JRadioButton("Debit/Credit");

		paymentGroup = new ButtonGroup();
		paymentGroup.add(cash);
		paymentGroup.add(debitOrCredit);

		radioButPanel = new JPanel(new FlowLayout());
		radioMerger = new JPanel();
		radioMerger.add(cash);
		radioMerger.add(debitOrCredit);
		radioButPanel.add(radioMerger);

		centreup = new JPanel(new GridLayout(2, 2, 0, 10));
		centreup.add(quantityLabel);
		centreup.add(quantitySpinner);
		centreup.add(paymentTypeLabel);
		centreup.add(radioButPanel);

		productListLabel = new JLabel("Product");
		productListLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cartListLabel = new JLabel("Cart");
		cartListLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

		centrebody = new JPanel(new GridLayout(1, 2));
		centrebody.add(productListLabel);
		centrebody.add(cartListLabel);

		db.rs = db.getproduct();

		Vector<Object> productColumn = new Vector<Object>();
		productColumn.add("ProductId");
		productColumn.add("ProductName");
		productColumn.add("ProductType");
		productColumn.add("ProductPrice");
		productColumn.add("ProductQty");
		Vector<Vector<Object>> productTableData = new Vector<Vector<Object>>();
		try {
			while (db.rs.next()) {
				Vector<Object> record = new Vector<Object>();
				record.add(db.rs.getObject("ID"));
				record.add(db.rs.getObject("productname"));
				record.add(db.rs.getObject("productTypeName"));
				record.add(db.rs.getObject("productPrice"));
				record.add(db.rs.getObject("productQuantity"));
				productTableData.add(record);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dtm1 = new DefaultTableModel(productTableData, productColumn);
		productTable = new JTable(dtm1) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		productTable.addMouseListener(new MouseListener() {

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
				int index = productTable.getSelectedRow();
				selectedProductId = productTable.getValueAt(index, 0).toString();
				selectedproduct = db.getproduct(selectedProductId);
			}
		});
		productTable.getTableHeader().setReorderingAllowed(false);
		productTable.setPreferredScrollableViewportSize(new Dimension(240, 150));
		jspProduct = new JScrollPane(productTable);
		jspProduct.setVisible(true);

		Vector<Vector<Object>> cartTableData = new Vector<Vector<Object>>();

		dtm2 = new DefaultTableModel(cartTableData, productColumn);
		cartTable = new JTable(dtm2);
		cartTable.setEnabled(false);
		cartTable.getTableHeader().setReorderingAllowed(false);
		cartTable.setPreferredScrollableViewportSize(new Dimension(240, 150));
		jspCart = new JScrollPane(cartTable);
		jspCart.setVisible(true);

		addToCart = new JButton("Add To Cart");
		addToCart.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addToCart.addActionListener(this);
		CheckOut = new JButton("Check Out");
		CheckOut.setFont(new Font("Tahoma", Font.PLAIN, 18));
		CheckOut.addActionListener(this);

		north = new JPanel(new FlowLayout());
		north.setBorder(new EmptyBorder(15, 0, 15, 0));
		north.add(headerLabel);

		centredown = new JPanel(new GridLayout(1, 2, 5, 0));
		centredown.add(jspProduct);
		centredown.add(jspCart);

		centre = new JPanel(new GridLayout(3, 1, 5, 5));
		centre.setBorder(new EmptyBorder(15, 10, 15, 10));
		centre.add(centreup);
		centre.add(centrebody);
		centre.add(centredown);

		south = new JPanel(new GridLayout(1, 2, 20, 0));
		south.setBorder(new EmptyBorder(15, 10, 15, 10));
		south.add(addToCart);
		south.add(CheckOut);

		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(centre, BorderLayout.CENTER);
		getContentPane().add(south, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addToCart) {
			int index = productTable.getSelectedRow();
			if (index >= 0) {
				if (Integer.valueOf(quantitySpinner.getValue().toString()) > 0) {
					int latestquant = selectedproduct.getProductQuantity();
					for (int i = 0; i < cartTable.getRowCount(); i++) {
						if (cartTable.getValueAt(i, 0).toString().equals(selectedProductId)) {
							latestquant -= Integer.valueOf(cartTable.getValueAt(i, 4).toString().toString());
						}
					}

					int valid = -1;

					for (int i = 0; i < cartTable.getRowCount(); i++) {
						if (cartTable.getValueAt(i, 0).toString().equals(selectedProductId)) {
							valid = i;
						}
					}

					if (valid != -1) {
						if (quantityvalidation(latestquant)) {
							int ind = uniquecartlist(selectedProductId);
							int startingQuantity = Integer.valueOf(cartTable.getValueAt(ind, 4).toString());
							latestquant -= Integer.valueOf(quantitySpinner.getValue().toString());
							int finalQuantity = startingQuantity + Integer.valueOf(quantitySpinner.getValue().toString());

							cartTable.setValueAt(finalQuantity, ind, 4);
							productTable.setValueAt(latestquant, index, 4);
						}
					} else {
						if (quantityvalidation(latestquant)) {
							Vector<Object> cartdata = new Vector<Object>();
							int quantity = latestquant - Integer.valueOf(quantitySpinner.getValue().toString());
							cartdata.add(selectedProductId);
							cartdata.add(selectedproduct.getProductName());
							cartdata.add(db.findproducttypeId(selectedProductId)[1]);
							cartdata.add(selectedproduct.getProductPrice());
							cartdata.add(quantitySpinner.getValue());

							// productTable.setval
							dtm1.setValueAt(quantity, index, 4);
							dtm2.insertRow(dtm2.getRowCount(), cartdata);
						}
					}
				} else {
					JOptionPane.showMessageDialog(this, "Please Input quantity more than 0", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		if (e.getSource() == CheckOut) {
			int checkoutOption = JOptionPane.showConfirmDialog(this, "Are you sure you want to check Out?",
					"select an option", JOptionPane.YES_NO_CANCEL_OPTION);
			if (checkoutOption == JOptionPane.YES_OPTION) {
				String userId, transactionDate, paymentType;
				if (!(cash.isSelected() || debitOrCredit.isSelected())) {
					JOptionPane.showMessageDialog(this, "Please input your payment type first", "Error",
							JOptionPane.ERROR_MESSAGE);

				} else {
					paymentType = (cash.isSelected()) ? "Cash" : "Debit/Credit";

					userId = db.finduserId(user);

					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDateTime now = LocalDateTime.now();
					transactionDate = dtf.format(now);

					db.insertTransactionHeader(userId, transactionDate, paymentType);

					String transactionId = db.getTransactionId(userId, transactionDate, paymentType);

					for (int i = 0; i < cartTable.getRowCount(); i++) {
						String productId, quantity;
						productId = cartTable.getValueAt(i, 0).toString();
						quantity = cartTable.getValueAt(i, 4).toString();
						db.insertTrDetail(transactionId, productId, quantity);
					}

					for (int i = 0; i < productTable.getRowCount(); i++) {
						Product product;
						String productId;

						String productTypeId, productName, productPrice, productQuantity;

						productId = productTable.getValueAt(i, 0).toString();

						productTypeId = db.findproducttypeId(productId)[0];
						productName = productTable.getValueAt(i, 1).toString();
						productPrice = productTable.getValueAt(i, 3).toString();
						productQuantity = productTable.getValueAt(i, 4).toString();

						product = new Product(productTypeId, productName, productPrice,
								Integer.valueOf(productQuantity));
						db.updateproduct(productId, product);
					}
					// System.out.println(cartTable.getRowCount());
					while (cartTable.getRowCount() != 0) {
						dtm2.removeRow(0);
					}
					JOptionPane.showMessageDialog(this, "Transaction Completed", "Message",
							JOptionPane.INFORMATION_MESSAGE);

				}
			}
		}
	}

	public Integer uniquecartlist(String Id) {
		int valid = -1;

		for (int i = 0; i < cartTable.getRowCount(); i++) {
			if (cartTable.getValueAt(i, 0).toString().equals(Id)) {
				valid = i;
			}
		}

		return valid;
	}

	public boolean quantityvalidation(int latestquant) {
		boolean valid = false;
		if (latestquant - Integer.valueOf(quantitySpinner.getValue().toString()) >= 0) {
			valid = true;
		} else {
			JOptionPane.showMessageDialog(this, "Not enough Item to be bought", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return valid;
	}

}

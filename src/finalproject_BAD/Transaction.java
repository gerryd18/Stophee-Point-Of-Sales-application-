package finalproject_BAD;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Transaction extends JInternalFrame implements ActionListener{

	JTextField totalField;
	JButton checkButton;
	JLabel headerLabel, headerTransactionLabel, detailTransactionLabel, total; 
	JPanel northPanel, centerPanel, southPanel, centerPanelwrap;
	JTable transactionTable, detailTable;
	DefaultTableModel dtmHeader, dtmDetail;
	JScrollPane jsHeader, jsDetail;
	DatabaseConnection db;
	ResultSet headerdata;
	Vector<Object> DTColumnName = new Vector<>();
	Vector<Vector<Object>> DTdata = new Vector<Vector<Object>>();
	public int totalPrice;
	
	public Transaction(DatabaseConnection db) {
		super("Transaction", false, false, false);
		
		
		this.db = db;
		
		headerLabel = new JLabel("Transaction");
		headerLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		northPanel = new JPanel();
		northPanel.setBorder(new EmptyBorder(0,10,0,10));
		northPanel.add(headerLabel);
		
		centerPanel = new JPanel();
		centerPanel.setBorder(new EmptyBorder(0,10,15,10));
		centerPanelwrap = new JPanel(new GridLayout(4,1));
		
		headerTransactionLabel = new JLabel("Header Transaction");
		detailTransactionLabel = new JLabel("Detail Transaction");
		
		Vector<Object> columnName = new Vector<Object>();
		columnName.add("TransactionID");
		columnName.add("Date of transaction");
		columnName.add("Payment Type");
		
		headerdata = db.getTransactionHeader();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		try {
			while (headerdata.next()) {
				Vector<Object> record = new Vector<Object>();
				record.add(headerdata.getObject(1));
				record.add(headerdata.getObject(3));
				record.add(headerdata.getObject(4));
				
				data.add(record);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dtmHeader = new DefaultTableModel(data,columnName);
		transactionTable = new JTable(dtmHeader) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		
		transactionTable.getTableHeader().setReorderingAllowed(false);
		transactionTable.setPreferredScrollableViewportSize(new Dimension(480,80));
		
		
		jsHeader = new JScrollPane(transactionTable);
		jsHeader.setVisible(true);
		
		Vector<Object> columnNameDetail = new Vector<Object>();
		columnNameDetail.add("TransactionID");
		columnNameDetail.add("ProductName");
		columnNameDetail.add("ProductType");
		columnNameDetail.add("Quantity");
		
		Vector<Vector<Object>> dataDetail = new Vector<Vector<Object>>();
		
		dtmDetail = new DefaultTableModel(dataDetail,columnNameDetail);
		detailTable = new JTable(dtmDetail) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		
		
		detailTable.getTableHeader().setReorderingAllowed(false);
		detailTable.setPreferredScrollableViewportSize(new Dimension(480,80));
		jsDetail = new JScrollPane(detailTable);
		jsDetail.setVisible(true);
		centerPanelwrap.add(headerTransactionLabel);
		centerPanelwrap.add(jsHeader);
		centerPanelwrap.add(detailTransactionLabel);
		centerPanelwrap.add(jsDetail);
		
		centerPanel.add(centerPanelwrap);
		
		
		total = new JLabel("Total");
		
		totalField = new JTextField("0");
		totalField.setPreferredSize(new Dimension(100,30));
		totalField.setEditable(false);
		
		checkButton = new JButton("Check");
		checkButton.addActionListener(this);
		
		southPanel = new JPanel();
		southPanel.add(total);
		southPanel.add(totalField);
		southPanel.add(checkButton);
		//pakai settext(); kalo udh keread di hapus aj
		
		
		getContentPane().add(northPanel, BorderLayout.NORTH);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == checkButton) {
			if (transactionTable.getSelectedRowCount() > 0) {
				check();
			}
		}
	}
	
	// check button validation
	void check() {
		int transactionID = (int) transactionTable.getValueAt(transactionTable.getSelectedRow(), 0);
		DefaultTableModel tblModel = (DefaultTableModel)detailTable.getModel(); //get table model
		
		db.rs = db.getTransactionTable(transactionID);
		try {
			while(db.rs.next()) {
				
				Vector<Object> row = new Vector<>();
				String TransactionID = String.valueOf(db.rs.getString("TransactionID"));
				String productName = String.valueOf(db.rs.getString("productName"));
				String productType = String.valueOf(db.rs.getString("productTypeName"));
				int quantity = (int) db.rs.getInt("Quantity");
				
				row.add(TransactionID);
				row.add(productName);
				row.add(productType);
				row.add(quantity);
				DTdata.add(row);
				
				tblModel.addRow(row);
				
				//calculate totalPrice
				int productPrice = (int) db.rs.getInt("ProductPrice");
			
				totalPrice += (productPrice*quantity);
				totalField.setText(String.valueOf(totalPrice));
				
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}

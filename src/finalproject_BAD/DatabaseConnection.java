package finalproject_BAD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.spi.DirStateFactory.Result;

import java.sql.PreparedStatement;

public class DatabaseConnection {

	
	Connection connection;
	ResultSet rs;
	PreparedStatement ps;

	
	public DatabaseConnection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/stophee1","root","");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("message to the programmer : Database tidak berhasil tersambung, coba cek XAMPP, nyala belom?");
		}
		// TODO Auto-generated constructor stub
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Driver class not found");
		}
	}
	
	public void insertuser(User user) {
		// TODO Auto-generated method stub
		try {
			ps = connection.prepareStatement("Insert into users (name, email, password, phone, gender, role) values (?,?,?,?,?,?)");
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getUserEmail());
			ps.setString(3, user.getUserPassword());
			ps.setString(4, user.getUserPhone());
			ps.setString(5, user.getUserGender());
			ps.setString(6, user.getUserRole());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("message to the programmer : error in the insertuser method in dbconnection class");
		}
	}
	
	public ResultSet getuser() {
		// TODO Auto-generated method stub
		try {
			ps = connection.prepareStatement("select * from users");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("message to the programmer : error in the getuser method in dbconnection class");	
		}
		
		return rs;
	}
	
	
	public void insertProductType(String type) {
		try {
			ps = connection.prepareStatement("Insert into producttype (productTypeName) values (?)");
			ps.setString(1, type);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("message to the programmer : error in the insertproducttype method in dbconnect class");
		}
	}
	
	public ResultSet getProductCategory() {
		try {
			ps = connection.prepareStatement("Select * from producttype");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("message to the programmer : error in the getproducttype method in dbconnection class");				
		}
		
		return rs;
	}
	
	public void updateProductCategory(String id, String productTypeName) {
		try {
			ps = connection.prepareStatement("UPDATE producttype SET productTypeName = ? WHERE ID = ? ");
			ps.setString(1, productTypeName);
			ps.setString(2, id);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("message to the programmer : error in the updateproducttype method in dbconnection class");
		}
		
		
	}
	
	public void deleteProductCategory(String id) {
		try {
			ps = connection.prepareStatement("DELETE FROM producttype WHERE ID = ?");
			ps.setString(1, id);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("message to the programmer : error in the removeproducttype method in dbconnection class");
		}
	}
	
	public void insertproduct(Product product) {
		try {
			ps = connection.prepareStatement("insert into product (producttypeId, productname, productprice, productquantity) values (?,?,?,?)");
			ps.setString(1, product.getProductTypeId());
			ps.setString(2, product.getProductName());
			ps.setString(3, product.getProductPrice());
			ps.setString(4, String.valueOf(product.getProductQuantity()));
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("message to the programmer : error in the insertproduct method in dbconnection class");	
		}
	}

	public ResultSet getproduct() {
		try {
			ps = connection.prepareStatement("SELECT * from product p join producttype pt where pt.ID = p.productTypeId order by p.ID ");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("message to the programmer : error in the getproduct method in dbconnection class");	
		}
		
		return rs;
	}
	
	public Product getproduct(String productId) {
		Product product = null;
		try {
			ps = connection.prepareStatement("SELECT * from product p join producttype pt where pt.ID = p.productTypeId and p.ID in(?)order by p.ID ");
			ps.setString(1, productId);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("message to the programmer : error in the getproduct method in dbconnection class");	
		}
		
		try {
			while (rs.next()) {
				product = new Product(rs.getObject(1).toString(), rs.getObject(3).toString(), rs.getObject(4).toString(), Integer.valueOf(rs.getObject(5).toString()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return product;
	}
	
	public String[] findproducttypeId (String productId) {
		String[] type = new String[2];
		try {
			ps = connection.prepareStatement("SELECT pt.ID, producttypename from product p join producttype pt where pt.ID = p.productTypeId AND p.ID in(?) order by p.ID ");
			ps.setString(1, productId);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while (rs.next()) {
				type[0] = rs.getObject(1).toString();
				type[1] = rs.getObject(2).toString();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return type;
	}
	
	public void updateproduct (String productId, Product product) {
		try {
			ps = connection.prepareStatement("UPDATE `product` SET `productTypeId` = ?, `productname` = ?, `productPrice` = ?, `productQuantity` = ? WHERE `product`.`ID` = ? ");
			ps.setString(1, product.getProductTypeId());
			ps.setString(2, product.getProductName());
			ps.setString(3, product.getProductPrice());
			ps.setString(4, String.valueOf(product.getProductQuantity()));
			ps.setString(5, productId);
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteproduct(String productId) {
		try {
			ps = connection.prepareStatement("DELETE FROM `product` WHERE `product`.`ID` = ?");
			ps.setString(1, productId);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String finduserId(User user) {
		String email = user.getUserEmail();
		String userId = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM `users` WHERE Email = ?" );
			ps.setString(1, email);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while (rs.next()) {
				userId = rs.getObject(1).toString();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userId;
		
	}
	
	public void insertTransactionHeader(String userId, String transactionDate, String paymentType) {
		try {
			ps = connection.prepareStatement("INSERT INTO `headertransaction` (`UserID`, `TransactionDate`, `PaymentType`) VALUES (?, ?, ?)");
			ps.setString(1, userId);
			ps.setString(2, transactionDate);
			ps.setString(3, paymentType);
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getTransactionId(String UserId, String transactionDate, String paymentType) {
		String transactionId = null;
		
		try {
			ps = connection.prepareStatement("SELECT ID FROM headertransaction WHERE UserID = ? AND TransactionDate = ? AND PaymentType = ? ORDER BY ID");
			ps.setString(1, UserId);
			ps.setString(2, transactionDate);
			ps.setString(3, paymentType);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while (rs.next()) {
				if (rs.isLast()) {
					transactionId = rs.getObject(1).toString();
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return transactionId;
	}
	
	public void insertTrDetail(String transactionId, String productId, String Quantity) {
		try {
			ps = connection.prepareStatement("INSERT INTO `detailtransaction` (`TransactionID`, `ProductID`, `Quantity`) VALUES (?, ?, ?) ");
			ps.setString(1, transactionId);
			ps.setString(2, productId);
			ps.setString(3, Quantity);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet getTransactionHeader() {
		try {
			ps = connection.prepareStatement("SELECT * FROM `headertransaction`");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getTransactionTable(int transactionID) {
		
		try {
			ps = connection.prepareStatement("select `TransactionID`, `ProductName`, pt.ProductTypeName, `Quantity` , p.ProductPrice FROM detailtransaction dt join Product p on p.ID = dt.ProductID join producttype pt on pt.ID = p.ProductTypeID WHERE dt.transactionID = " + transactionID);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		return rs;
	}

}


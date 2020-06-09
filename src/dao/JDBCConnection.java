package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JDBCConnection {

//	public static void main(String[] args) {
//		System.out.println(JDBCConnection.uploadFile("Rohan", "Maharjan", null));
//	}
	public static Connection getConnection(){
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet2","root","Rapper9841@!");
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Error connecting to database");
		}
		
		return connection;
	}
	
	public static String uploadFile(String firstName, String lastName, InputStream photo) {
		Connection con = JDBCConnection.getConnection();
		
		if(con == null) {
			return "Cannot connect to the database";
		}
		
		String sql = "INSERT INTO student (sid, first_name, last_name, photo) values (?,?,?,?)";
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, 3);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);
			preparedStatement.setBlob(4, photo);
			
			int row = preparedStatement.executeUpdate();
			
			con.close();
			
			return (row>0)?"File successfully uploaded":"Error uploading file";
		}catch(Exception ex) {
			System.out.println("Error while executing sql or creating statement");
			ex.printStackTrace();
			return "Something went wrong during preparestatment";
		}
	}
	
}

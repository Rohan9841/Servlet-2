package dao;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletResponse;

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
		
		String sql = "INSERT INTO student (first_name, last_name, photo) values (?,?,?)";
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			//preparedStatement.setInt(1, 10);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setBlob(3, photo);
			
			int row = preparedStatement.executeUpdate();
			
			return (row>0)?"File successfully uploaded":"Error uploading file";
		}catch(Exception ex) {
			System.out.println("Error while executing sql or creating statement");
			ex.printStackTrace();
			return "Something went wrong during preparestatment";
		}finally {
			if(con != null) {
				try {
					con.close();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
			}
		}
	}
	
	public static String downloadFile(int sId, HttpServletResponse response) {
		Connection con = JDBCConnection.getConnection();
		
		if(con == null) {
			return "Cannot connect to the database";
		}
		
		String sql = "SELECT * FROM student WHERE sid = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sId);
			
			ResultSet result = pstmt.executeQuery();
			if(result.next()) {
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				Blob blob = result.getBlob("photo");
				
				byte[] byteArray = blob.getBytes(1, (int)blob.length());
				
//				response.setContentType("image/gif");
				
				OutputStream outStream = response.getOutputStream();
				outStream.write(byteArray);
//				outStream.flush();
				outStream.close();
				
			}
			
			return "file successfully downloaded";
		}catch(Exception ex) {
			System.out.println("Error while executing sql or creating statement");
			ex.printStackTrace();
			return "Something went wrong during preparestatment";
		}finally {
			if(con != null) {
				try {
					con.close();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
			}
		}
	}
	
}

package example;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.JDBCConnection;

@WebServlet(description = "This controller gets the upload request and processes it.", urlPatterns = { "/uploadServlet" })
@MultipartConfig
public class UploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pWriter = response.getWriter();
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		Part filePart = request.getPart("photo");
		
		InputStream photo = null;
		
		if(filePart != null) {
            // obtains input stream of the upload file
            photo = filePart.getInputStream();
           
            pWriter.println(JDBCConnection.uploadFile(firstName,lastName,photo));
            
            response.sendRedirect("download.html");
		}else {
			pWriter.println("filepart is null");
		}
	}

}

package com.vc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class MyServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String name = request.getParameter("tbName");
	    String password = request.getParameter("tbPass");

	    Connection con = null;
	    PreparedStatement st1 = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");

	        // Insert new user into GmailUser table
	        st1 = con.prepareStatement("INSERT INTO GmailUser(name, password) VALUES (?, ?)");
	        st1.setString(1, name);
	        st1.setString(2, password);

	        int rowsInserted = st1.executeUpdate();

	        if (rowsInserted > 0) {
	            System.out.println("User registered successfully!");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("login.html");
	            dispatcher.forward(request, response);
	        } else {
	            response.getWriter().println("Registration failed. Please try again.");
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try { if (st1 != null) st1.close(); } catch (Exception e) {}
	        try { if (con != null) con.close(); } catch (Exception e) {}
	    }
	}
}

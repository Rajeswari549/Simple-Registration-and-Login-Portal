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

/**
 * Servlet implementation class MyServelt
 */
@WebServlet("/Login")
public class MyServelt2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServelt2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("===dopost called ===");
		String name = request.getParameter("tbName");
		String password = request.getParameter("tbPass");
		Connection con =null;
		
		ResultSet rs =null;
		
		PreparedStatement st1 = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
			st1 = con.prepareStatement("select* from GmailUser where name=? and password=?");
			st1.setString(1, name);
			st1.setString(2, password);
			rs =st1.executeQuery();
			if(rs.next()) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
				dispatcher.forward(request, response);
				rs=st1.executeQuery();
			}
			else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
					dispatcher.forward(request, response);
				}
				System.out.println("table is created successfully!,check in Db");
			
			} catch (ClassNotFoundException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally { 
			try { if(rs != null) rs.close(); } catch (Exception e) {}
			try { if(st1 != null) st1.close(); } catch (Exception e) {}
			try { if(con != null) con.close(); } catch (Exception e) {}
		}
	}

}
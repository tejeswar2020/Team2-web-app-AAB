package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserDetails
 */
public class UserDetails extends HttpServlet {
	String JDBC_DRIVER = "";
	String DB_URL = "";
	String USER = "root";
	String PASS = "abiramroot";
	Connection conn = null;
	Statement stmt = null;
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		DB_URL = "jdbc:mysql://localhost/COMPANY";
		try {
		Class.forName(JDBC_DRIVER);
		//Open a connection
	    System.out.println("Connecting to database...");
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);
	    stmt = conn.createStatement();
	    System.out.println("Connected to database.");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		

		String user_name = request.getParameter("user_name");
		String check = "Select * from USERS where username = \""+user_name+"\";";
		try {
			ResultSet r = stmt.executeQuery(check);
			if(r.next()) {
			if (r.getString(1).equals(user_name)) {
				System.out.println("User Name already exists.. please use a different name");
				request.getRequestDispatcher("/index.html").forward(request, response);
				return;
			}
			}
		} catch (SQLException e1) {
			System.out.println("Check failed..");
			e1.printStackTrace();
		}
		String f_name = request.getParameter("f_name");
		String l_name =request.getParameter("l_name");
		int age = Integer.parseInt(request.getParameter("age"));
		String emailaddr =request.getParameter("emailaddr");
		String pass = request.getParameter("pass");
		String gender = request.getParameter("gender");
		System.out.println(user_name+" "+f_name+" "+l_name + " " + age+" "+emailaddr+" "+pass+" "+gender);
		ResultSet rs;
		String query1 = "INSERT INTO USERS VALUES(\""+user_name+"\",\""+f_name+"\",\""+l_name+"\","+age+",\""+emailaddr+"\",\""+pass+"\",\""+gender+"\");";
		
		try
		{
			stmt.executeUpdate(query1);
			rs = stmt.executeQuery("Select * from USERS");
			while(rs.next())
				System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getInt(4)+"  "+rs.getString(5)+"  "+rs.getString(6)+"  "+rs.getString(7));
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

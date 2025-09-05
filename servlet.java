import javax.servlet.*;

import java.sql.*;
import java.io.*;
import javax.servlet.*;

public class servlet implements Servlet{

	public void service(ServletRequest req, ServletResponse res)throws ServletException, IOException{

		String name = req.getParameter("name");
		String pass = req.getParameter("pass");
		String email = req.getParameter("email");
		String contact = req.getParameter("contact");
		
		PrintWriter out = res.getWriter();
	
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","123456");
			
			PreparedStatement ps = con.prepareStatement("insert into employee values(?,?,?,?)");
			ps.setString(1,name);
			ps.setString(2,pass);
			ps.setString(3,email);
			ps.setString(4,contact);
			ps.executeUpdate();

			RequestDispatcher rd = req.getRequestDispatcher("login.html");
			rd.include(req,res);
			System.out.println(name +" Registered Successfullyyy");
		}
		catch(Exception e){
			out.println(e);
		}
	
	}
		public void init(ServletConfig h){ System.out.println("initialize");}
		public void destroy(){}
		public String getServletInfo(){ return (null);}
		public ServletConfig getServletConfig(){return (null);}
}
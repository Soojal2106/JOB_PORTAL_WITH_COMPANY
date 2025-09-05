import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class CheckBalance extends HttpServlet{

	public void service(HttpServletRequest req , HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		String accountno = req.getParameter("accountno");

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","123456");
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("select name,balance from accountdata where accountno='"+accountno+"'");

			while(rs.next())
			{	
				out.println("<b style='color:green;background:yellow;'>Account Holder Name : </b>"+(rs.getString(1)));

				out.println("</br><b style='color:green;background:yellow;'>Total Balance is : </b>"+(rs.getString(2)));
			}
			RequestDispatcher rd = req.getRequestDispatcher("home.html");
			rd.include(req,res);
		}
		catch(Exception e){
			out.println(e);
		}

	}
}
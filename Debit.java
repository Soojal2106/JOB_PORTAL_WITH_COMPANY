import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.Date;

public class Debit extends HttpServlet{

	public void service(HttpServletRequest req , HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		
		String accountno = req.getParameter("accountno");
		long debit =Long.parseLong(req.getParameter("ammount"));
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","123456");
			
			Date udate = new Date();
			java.sql.Date sqlDate = new java.sql.Date(udate.getTime());
			
			PreparedStatement ps0 = con.prepareStatement("insert into debit values(?,?,?)");
			ps0.setString(1,accountno);
			ps0.setLong(2,debit);
			ps0.setDate(3,sqlDate);
			ps0.executeUpdate();
			
			ps0.close();





			Statement s = con.createStatement();	
			ResultSet rs = s.executeQuery("select balance from accountdata where accountno='"+accountno+"'");
			while(rs.next())
			{
				long ammount = Long.parseLong(rs.getString(1));
				long newBalance = ammount-debit;
				Statement s1 = con.createStatement();
				s1.executeUpdate("update accountdata set balance="+newBalance+" where accountno='"+accountno+"'");
				out.println("<br><b style='color:green;background:yellow;'>Debited ammount</b> : " +debit);
				out.println("<br><b style='color:red;background:yellow;'>Total Balance is</b> : " +newBalance);
			}

			RequestDispatcher rd1 = req.getRequestDispatcher("home.html");
			rd1.include(req,res);
		}
		catch(Exception e){
			out.println(e);
		}


	}

}
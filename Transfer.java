import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.Date;


public class Transfer extends HttpServlet{

	public void service(HttpServletRequest req , HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		String sAccount =req.getParameter("sAccount");
		String rAccount =req.getParameter("rAccount");
		String ammount1  = req.getParameter("ammount");
		
		


		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","123456");
			
			Date udate = new Date();
			java.sql.Date sqlDate = new java.sql.Date(udate.getTime());

			
			PreparedStatement ps0 = con.prepareStatement("insert into transaction values (?,?,?,?)");
			ps0.setString(1,sAccount);
			ps0.setString(2,rAccount);
			ps0.setString(3,ammount1);
			ps0.setDate(4,sqlDate);
			ps0.executeUpdate();
			
	
		
			Statement s1 = con.createStatement();	
			ResultSet rs1 = s1.executeQuery("select balance from accountdata where accountno='"+sAccount+"'");
			while(rs1.next())
			{
				int ammount = Integer.parseInt(rs1.getString(1));
				int newBalance = ammount-(Integer.parseInt(ammount1));
				
				s1.executeUpdate("update accountdata set balance="+newBalance+" where accountno='"+sAccount+"'");
			}



			Statement s2 = con.createStatement();	
			ResultSet rs2 = s2.executeQuery("select balance from accountdata where accountno='"+rAccount+"'");
			while(rs2.next())
			{
				int ammount = Integer.parseInt(rs2.getString(1));
				int newBalance = ammount+(Integer.parseInt(ammount1));

				
				s2.executeUpdate("update accountdata set balance="+newBalance+" where accountno='"+rAccount+"'");
			}

			s1.close();s2.close();
			rs1.close();rs2.close();
			con.close();
			out.println("Successfully Sent");
			RequestDispatcher rd1 = req.getRequestDispatcher("tr_His.html");
			rd1.include(req,res);

			
			

		}
		catch(Exception e){
			out.println(e);
		}

	}
}
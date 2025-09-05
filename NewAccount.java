import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.Random;
import javax.swing.*;

public class NewAccount extends HttpServlet{

	public void service(HttpServletRequest req , HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		String user = req.getParameter("name");
		String adhar = req.getParameter("adhar");
		String email = req.getParameter("email");
		String mobile = req.getParameter("mobile");
		String Fname = req.getParameter("Fname");
		String Atype = req.getParameter("Atype");
		String Sbalance = req.getParameter("Sbalance");
		String gender = req.getParameter("gender");

		String update="update";
		Random random = new Random();
		String s= "123456789";
		char[] otp  = new char[11];
		for(int i=0;i<11;i++)
		{
			otp[i]=s.charAt(random.nextInt(s.length()));
		}
		String strArray[] = new String[otp.length];
		for(int i=0;i<otp.length;i++)
		{
			strArray[i] = String.valueOf(otp[i]);
		}
		String s1 = Arrays.toString(strArray);
		String res1 = "";
		for(String num:strArray)
		{
			res1+=num;
		}

		
		
		try
		{
			int flag =0;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","123456");
			
			PreparedStatement ps = con.prepareStatement("insert into accountdata values(?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1,user);
			ps.setString(2,res1);
			ps.setString(3,adhar);
			ps.setString(4,mobile);
			ps.setString(5,email);
			ps.setString(6,Fname);
			ps.setString(7,Atype);
			ps.setString(8,Sbalance);
			ps.setString(9,gender);
			ps.setString(10,update);
			ps.executeUpdate();

			PreparedStatement ps1 = con.prepareStatement("select * from accountdata where accountno ='"+res1+"'");
			ResultSet rs = ps1.executeQuery();
			while(rs.next())
			{
				out.println("<br><mark>Name 		: </mark>"+rs.getString(1));
				out.println("<br><mark style='color:green'>Accountno	: </mark>"+rs.getString(2));
				out.println("<br><mark>Adharno 		: </mark>"+rs.getString(3));
				out.println("<br><mark>Mobile 		: </mark>"+rs.getString(4));
				out.println("<br><mark>Email 		: </mark>"+rs.getString(5));
				out.println("<br><mark>FatherName 	: </mark>"+rs.getString(6));
				out.println("<br><mark>Accounttype	: </mark>"+rs.getString(7));
				out.println("<br><mark>Balance  	: </mark>"+rs.getString(8));
				out.println("<br><mark>Gender 		: </mark>"+rs.getString(9));
				
			}

			

			
			RequestDispatcher rd1 = req.getRequestDispatcher("home.html");
			rd1.include(req,res);
		
		
		}
		catch(Exception e){
			out.println(e);
		}

	}
}
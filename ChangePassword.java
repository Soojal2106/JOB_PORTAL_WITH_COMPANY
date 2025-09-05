import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import javax.swing.*;

public class ChangePassword extends HttpServlet{

	public void service(HttpServletRequest req , HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		String oldpass = req.getParameter("oldpass");
		String newpass = req.getParameter("newpass");
		String cnfpass = req.getParameter("cnfpass");

		
		try
		{
			int flag =0;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","123456");
			
			Statement s = con.createStatement();
			if(newpass.equals(cnfpass))
			{
				s.executeUpdate("update employee set pass='"+newpass+"' where pass='"+oldpass+"'");
				out.println("Password Updated Successfully");
				RequestDispatcher rd = req.getRequestDispatcher("index.html");
				rd.include(req,res);
			}
			else
			{
				JOptionPane.showMessageDialog(null,"password do not match","Alert message",JOptionPane.WARNING_MESSAGE);
				RequestDispatcher rd1 = req.getRequestDispatcher("ch_pass.html");
				rd1.include(req,res);

			}
		}
		catch(Exception e){
			out.println(e);
		}

	}

}
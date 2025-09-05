import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import javax.swing.*;

public class LoginServlet extends HttpServlet{

	public void service(HttpServletRequest req , HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		String type = req.getParameter("type");
		String user = req.getParameter("name");
		String pass = req.getParameter("pass");

		HttpSession session = req.getSession();
		session.setAttribute("name",user);

		try
		{
			int flag =0;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","123456");
			
			Statement s = con.createStatement();
		
			if(type.equals("user"))
		  		{
					ResultSet rs = s.executeQuery("select NAME,PASS from employee");

					while(rs.next())
					{
					if(user.equals(rs.getString(1)) && pass.equals(rs.getString(2)))
						{
						out.println("Welcome "+user);
						RequestDispatcher rd1 = req.getRequestDispatcher("home.html");
						rd1.include(req,res);
						flag=1;break;
						}
					}
					if(flag==0)
						{
						JOptionPane.showMessageDialog(null,"invalid username or password","Alert Message",JOptionPane.WARNING_MESSAGE);
						RequestDispatcher rd2 = req.getRequestDispatcher("login.html");
						rd2.include(req,res);

						}	
					
		   		}
			else
		 		{
					if(user.equals("atul")&& pass.equals("admin"))
					{
					out.println("Welcome "+user);
					RequestDispatcher rd2 = req.getRequestDispatcher("account_det.html");
					rd2.forward(req,res);
					}z

					else
					{
					RequestDispatcher rd3 = req.getRequestDispatcher("login.html");
					rd3.forward(req,res);

					out.println("none");
					}	
		   		}

		}
		catch(Exception e){
			out.println(e);
		}
	}
}
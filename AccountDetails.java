import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class AccountDetails extends HttpServlet{

	public void service(HttpServletRequest req , HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
	

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","123456");
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("select * from accountdata");
			out.print("<html><style>body{widht:99vw;background:url(https://blog.quickwork.co/content/images/size/w2000/2021/10/Banking-as-a-Service.jpg);display:flex;justify-content:center;align-items:center;}table{font-family:sans-serif; box-shadow: 0 0 500px .5px rgb(85, 96, 210);}th{background:#1E90FF;color:white;}td,th{border:2px solid black;padding:10px;text-align:left;}tr{background:white;}tr:nth-child(even){background:#dddddd;}</style><body>");
			

out.print("<table border='0' style='border-collapse:collapse'>");
                        			out.print("<thead><tr><th>S.no.</th><th>Name</th><th>Account No.</th><th>Adhar No.</th><th>Mobile No.</th><th>Email_ID</th><th>Father_Name</th><th>Account Type</th><th>Balance</th><th>Gender</th><th>Update</th></tr></thead>");
                        			
                    	

			int sno= 1;


			while(rs.next())
			{	
				
     				out.print("<tr><td>"+sno+"</td><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td><td>"+rs.getString(6)+"</td><td>"+rs.getString(7)+"</td><td>"+rs.getString(8)+"</td><td>"+rs.getString(9)+"</td><td><a href='update.html'>Update</a></td></tr>");
                    						
               					

				out.print("</html></body>");
				sno++;	
			}
			
		}
		catch(Exception e){
			out.println(e);
		}

	}
}
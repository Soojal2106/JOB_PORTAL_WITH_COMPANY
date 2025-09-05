import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class TrHistory extends HttpServlet{

	public void service(HttpServletRequest req , HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
	

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","123456");
			
			
			out.print("<html><style>body{widht:99vw;background:url(https://blog.quickwork.co/content/images/size/w2000/2021/10/Banking-as-a-Service.jpg);display:flex;justify-content:center;align-items:center;}table{font-family:sans-serif; box-shadow: 0 0 500px 12px rgb(85, 96, 210);}th{background:#1E90FF;color:white;}td,th{border:2px solid black;padding:15px;text-align:left;}tr{background:white;}tr:nth-child(even){background:#dddddd;}</style><body>");
			

out.print("<table border='0' style='border-collapse:collapse'>");
                        			out.print("<thead><tr><th>S.no.</th><th>Sender Account</th><th>Receiver Account</th><th>Ammount</th><th>Date</th></tr></thead>");
                        			
                    	

			int sno= 1;

			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("select * from transaction");
			while(rs.next())
			{	
				
     				out.print("<tr><td>"+sno+"</td><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getDate(4)+"</td></tr>");
                    						
               					

				
				sno++;	
			}
			out.print("</html></body>");
			
		}
		catch(Exception e){
			out.println(e);
		}

	}
}
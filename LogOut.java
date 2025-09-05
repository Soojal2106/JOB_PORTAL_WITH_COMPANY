import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class LogOut extends HttpServlet{

	public void doGet(HttpServletRequest req , HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		out.println("<b style='color:red;background-color:yellow;'>Log out successfully</b>");
		RequestDispatcher rd = req.getRequestDispatcher("index.html");
		rd.include(req,res);
		HttpSession session = req.getSession(false);
		session.invalidate();
		

	}
}

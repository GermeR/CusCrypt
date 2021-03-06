package web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.struct.MyBDD;
import web.struct.Personne;

@WebServlet("/servlet/singup")
public class ServletSingUp extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 42;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		MyBDD con = MyBDD.getInstance();
		HttpSession session = req.getSession();
		
		
		try {
			if (con.addUser(req.getParameter("login"), req.getParameter("nom"), req.getParameter("prenom"),
					req.getParameter("password"), req.getParameter("repassword"))) {
				
				session.setAttribute("user", new Personne(req.getParameter("nom"),req.getParameter("prenom"),req.getParameter("login")));
				res.sendRedirect("Menu");
			} else
				res.sendRedirect("../new.html");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.struct.MyBDD;
import web.struct.Personne;

@WebServlet("/servlet/log")
public class ServletLogin extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		MyBDD con = MyBDD.getInstance();
		if (con.authorize(req.getParameter("login"), req.getParameter("password"))) {
			System.out.println("bien ouej maggle");
			Personne p = con.get(req.getParameter("login"));
			if (p == null)
				res.sendRedirect("../new.html");
			else
				session.setAttribute("personne", p);
		}
	}
}

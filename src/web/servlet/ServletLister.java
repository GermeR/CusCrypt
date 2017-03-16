package web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.struct.MyBDD;
import web.struct.Personne;

@WebServlet("/servlet/lister")
public class ServletLister extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 42;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		PrintWriter out = res.getWriter();
		MyBDD con = MyBDD.getInstance();
		ArrayList<Personne> list = con.lister();
		if (session.getAttribute("personne") == null) {
			res.sendRedirect("../login.html");
		} else {
			list = con.lister();
		}
		out.println("<!DOCTYPE html><html lang=\"fr\"><head><meta charset=\"utf-8\"><meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"><meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"><title>Liste Des utilisateurs</title><link rel=\"stylesheet\"href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\"></head><body>");
		out.println("<table class=\"table-bordered\">");
		out.println("<th> Login </th><th> Nom </th><th> Prenom </th>");
		for (Personne per : list)
			out.println(per.toStringTD());
		out.println("</table>");
		out.println("<a href=/CusCrypt/servlet/lister?deco=1> Deconnexion </a>");
		out.println("</body></html>");
	}
}
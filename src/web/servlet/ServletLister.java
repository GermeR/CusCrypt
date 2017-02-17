package web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.struct.Connexion;
import web.struct.Personne;

@WebServlet("/servlet/lister")

public class ServletLister extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();
		PrintWriter out = res.getWriter();
		ArrayList<Personne> list = null;
		if (session.getAttribute("personne") != null) {
			Connexion con = Connexion.getInstance();
			try {
				list = con.lister();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		out.println(
				"<!DOCTYPE html><html lang=\"fr\"><head><meta charset=\"utf-8\"><meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"><meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"><title>Liste Des utilisateurs</title><link rel=\"stylesheet\"href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\"></head><body>");
		out.println("<table>");
		for (Personne per : list)
			out.println(per.toStringTD());
		out.println("</table>");
	}
}

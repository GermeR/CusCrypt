package web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.struct.Personne;

@WebServlet("/servlet/profil")
public class ServletProfil extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession();

		if (session == null)
			System.out.println("session = null");
		if (session.getAttribute("personne") == null)
			System.out.println("personne = null");
		if (session == null || session.getAttribute("personne") == null) {
			res.sendRedirect("../login.html");
		} else {

			Personne p = ((Personne) session.getAttribute("personne"));

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			String sql = null;

			out.println("<!DOCTYPE html>"
					+ "<html lang=\"fr\">"
					+ "<head><meta charset=\"utf-8\">"
					+ "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\">"
					+ "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">"
					+ "<title>Profil</title>"
					+ "<link rel=\"stylesheet\"href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">"
					+ "<link rel=\"stylesheet\"href=\"/CusCrypt/css/style.css\">"
					+ "</head>"
					+ "<body>"
					+ "<div class=\"container\">"
					+ "<div class=\"page-header\">"
					+ "<center><h1 class=\"display-1\">Meet'N'Roll : Profil</h1></center>"
					+ "</div>");

			out.println("<div class=\"menu\">");
			out.println("<ul class=\"onglets\">");
			out.println("<li><a href=\"Menu\"> Menu </a></li>");
			out.println("<li><a class=\"active\" href=\"profil\"> Profil </a></li>");
			out.println("<li><a href=\"log?delog=true\"> Deconnexion </a></li>");
			out.println("</ul>");
			out.println("</div>");

			// + "<div class=\"row\"><div class=\"col-xs-6 col-xs-offset-3\">"
			// +
			// "<a href=\"/Meet-N-Roll/menu.html\" class=\"btn btn-primary\"role=\"button\">Menu</a>"
			// +
			// "<a href=\"log?delog=true\" class=\"btn btn-primary\"role=\"button\">Deconnexion</a>"
			// + "</div>"
			out.println("</div>" + "<div class=\"row\">"
					+ "<div class=\"col-xs-6 col-xs-offset-3\">");

			out.print("</div></div>");
			out.println("</body></html>");
		}
	}

	private String fromMatch(String str) {
		String rez = "";
		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) == '_')
				rez += " ";
			else
				rez += str.charAt(i);
		return rez;
	}
}

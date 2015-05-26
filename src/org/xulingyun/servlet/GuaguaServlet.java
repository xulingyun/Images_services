package org.xulingyun.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.xulingyun.service.dao.Guagua;
import org.xulingyun.util.HibernateUtil;

@WebServlet(name = "guaguaServlet", urlPatterns = "/guaguaServlet")
public class GuaguaServlet extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(
				response.getOutputStream(), "UTF-8"));
//		BufferedReader br =  new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
//		String str = br.readLine();
		String s = request.getParameter("rank");
		if(s!=null){
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String sql = "from Guagua where rank=3 and handle!=0";
			Query query = session.createQuery(sql);
			List<Guagua> list =  query.list();
			session.getTransaction().commit();
			System.out.println(list.get(0).getSN());
			out.print(list.get(0).getSN());
		}else{
			String SN = request.getParameter("SN");
			String telphone = request.getParameter("telphone");
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String sql = "from Guagua where SN="+SN;
			Query query = session.createQuery(sql);
			List<Guagua> list =  query.list();
			list.get(0).setTelphone(telphone);
			session.save(list.get(0));
			session.getTransaction().commit();
			out.print("true");
		}
		out.flush();
		out.close();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("false");
		out.close();
		out = null;
	}
}

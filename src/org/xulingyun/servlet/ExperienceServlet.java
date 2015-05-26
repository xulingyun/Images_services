package org.xulingyun.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.xulingyun.service.dao.Experience;
import org.xulingyun.service.dao.PicText;
import org.xulingyun.util.HibernateUtil;
import com.alibaba.fastjson.JSONObject;

@WebServlet(name = "experienceServlet", urlPatterns = "/experienceServlet")
public class ExperienceServlet extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(
				resp.getOutputStream(), "UTF-8"));
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		String method = req.getParameter("method");
		if(method.equals("del")){
			PicText pt = new PicText();
			String params = req.getParameter("params");
			pt.setId(Integer.parseInt(params));
			s.delete(pt);
			s.getTransaction().commit();
		}else{
			@SuppressWarnings("unchecked")
			List<Experience> list =(List<Experience>)s.createQuery("from Experience").list();
			s.getTransaction().commit();
			Map map=new HashMap();
			map.put("total", list.size());
			map.put("rows", list);
			String ss =JSONObject.toJSONString(map);
			out.print(ss);
			out.flush();
			out.close();
			out= null;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String content = request.getParameter("content");
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		Experience  ex = new Experience();
		ex.setContent(content);
		ex.setName(name);
		ex.setTitle(title);
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		s.save(ex);
		s.getTransaction().commit();
		response.sendRedirect("success.jsp");
		return;
//		PrintWriter out = new PrintWriter(new OutputStreamWriter(
//				response.getOutputStream(), "UTF-8"));
//		out.print("心得提交成功！");
//		out.flush();
//		out.close();
	}
}

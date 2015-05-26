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
import org.xulingyun.service.dao.Question;
import org.xulingyun.util.HibernateUtil;
import org.xulingyun.util.SignUtil;

import com.alibaba.fastjson.JSONObject;

@WebServlet(name = "questionServlet", urlPatterns = "/questionServlet")
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;
	private static String[] statusdis = {"未回答","已回答"};
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
			List<Question> list =(List<Question>)s.createQuery("from Question").list();
			for(Question qu : list){
				qu.setStatusDis(statusdis[qu.getStatus()]);
			}
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
		String question = request.getParameter("question");
		Question  qu = new Question();
		qu.setQuestion(question);
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		s.save(qu);
		s.getTransaction().commit();
		response.sendRedirect("success.jsp");
		return;
//		PrintWriter out = new PrintWriter(new OutputStreamWriter(
//				response.getOutputStream(), "UTF-8"));
//		out.print("");
//		out.flush();
//		out.close();
	}
}

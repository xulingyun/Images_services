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

import org.hibernate.Query;
import org.hibernate.Session;
import org.xulingyun.service.dao.Photo;
import org.xulingyun.util.HibernateUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * 获取图片的接口，用get
 * @author Administrator
 *
 */

@WebServlet(name = "ImageServlet", urlPatterns = "/ImageServlet")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** 获取图片*/
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String kind = req.getParameter("kind");
		int page = Integer.parseInt(req.getParameter("page"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		String hql="from Photo as photo where photo.kind=:kind";
		session.beginTransaction();
		Query query= session.createQuery(hql);
		query.setFirstResult(page*pageNum);
		query.setMaxResults(pageNum);
		query.setString("kind",kind);
		List<Photo> list = query.list();
		session.getTransaction().commit();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", list.size());
		map.put("content", list);
		String ss = JSONObject.toJSONString(map);
		out.print(ss);
		out.flush();
		out.close();
		out = null;
	}

}

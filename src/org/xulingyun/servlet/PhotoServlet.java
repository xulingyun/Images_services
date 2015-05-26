package org.xulingyun.servlet;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.xulingyun.service.dao.Photo;
import org.xulingyun.service.dao.User;
import org.xulingyun.util.HibernateUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户相关
 * @author Administrator
 *
 */

@WebServlet(name = "PhotoServlet", urlPatterns = "/PhotoServlet")
public class PhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null;
		try {
			out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String method = request.getParameter("method");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		String kind = request.getParameter("kind");
//		if(kind.equals("")||kind==null){
			if(method.equals("query")){
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
				Map<String, Object> map = new HashMap<String, Object>();
				String hql="from Photo";
				session.beginTransaction();
				Query query= session.createQuery(hql);
				query.setFirstResult(page*pageNum);
				query.setMaxResults(pageNum);
				List<Photo> list = query.list();
				System.out.println("查询到的个数："+list.size());
				map.put("status", 1);
				map.put("content", list);
				session.getTransaction().commit();
				out.print(JSONObject.toJSONString(map));
				out.flush();
				out.close();
				out = null;
//			}
		}
		
	}

}

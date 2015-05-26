package org.xulingyun.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Query;
import org.hibernate.Session;
import org.xulingyun.service.dao.PicText;
import org.xulingyun.util.HibernateUtil;
import org.xulingyun.util.UseLog4j;
import org.xulingyun.util.Util;

import com.alibaba.fastjson.JSONObject;

@WebServlet(name = "imageTextServlet", urlPatterns = "/imageTextServlet")
public class ImageTextServlet extends HttpServlet {
	// private static String[] kinddis =
	// {"育儿课程","每天一问","学员分享","最萌宝贝","会员赢iPad","微信活动","线下活动介绍","使用指南","产品介绍"};
	private static String[] kinddis = { "育儿课堂", "育儿课堂", "育儿课堂", "育儿课堂", "育儿课堂", "育儿课堂", "育儿课堂", "育儿课堂", "育儿课堂", "悦己生活", "育儿课堂", "亲子互动" };
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		String method = req.getParameter("method");
		if (method.equals("del")) {
			PicText pt = new PicText();
			String params = req.getParameter("params");
			pt.setId(Integer.parseInt(params));
			s.delete(pt);
			s.getTransaction().commit();
			
			resp.sendRedirect("./pictext.jsp");
		} else {
			@SuppressWarnings("unchecked")
			List<PicText> list = (List<PicText>) s.createQuery("from PicText").list();
			// System.out.println("条数："+list.size());
			for (PicText pt : list) {
				pt.setKindDis(kinddis[pt.getKind() - 1]);
			}
			s.getTransaction().commit();
			Map map = new HashMap();
			map.put("total", list.size());
			map.put("rows", list);
			String ss = JSONObject.toJSONString(map);
			out.print(ss);
			out.flush();
			out.close();
			out = null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		String pageS = req.getParameter("page");
		String rowsS = req.getParameter("rows");
		int page = 1;
		int rows = 50;
		if (pageS != null && rowsS != null && pageS.equals("") && rowsS.equals("")) {
			page = Integer.parseInt(pageS);
			rows = Integer.parseInt(rowsS);
		}
		PrintWriter out = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
		if (method != null && method.equals("getdata")) {
			Session s = HibernateUtil.getSessionFactory().getCurrentSession();
			s.beginTransaction();
			Query countQuery = s.createQuery("select count(*) from PicText");
			int total = ((Number) countQuery.uniqueResult()).intValue();
			Query q = s.createQuery("from PicText order by id desc");
			q.setFirstResult((page - 1) * rows);
			q.setMaxResults(rows);
			List<PicText> list = (List<PicText>) q.list();
			s.getTransaction().commit();
			for (PicText pt : list) {
				if (pt.getKind() > 0) {
					pt.setKindDis(kinddis[pt.getKind() - 1]);
				}
			}
			Map map = new HashMap();
			map.put("total", total);
			map.put("rows", list);
			String ss = JSONObject.toJSONString(map);
			out.print(ss);
			out.flush();
			out.close();
			// out = null;
			// UseLog4j.info(getClass(), "---------结束---------");
		} else {
			req.setCharacterEncoding("UTF-8");
			Class<PicText> clszz = null;
			PicText pt = null;
			try {
				clszz = (Class<PicText>) Class.forName("org.xulingyun.service.dao.PicText");
				pt = (PicText) clszz.newInstance();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			Method m = null;
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// factory.setSizeThreshold(yourMaxMemorySize);
			// factory.setRepository(yourTempDirectory);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(1024 * 1024);
			try {
				List<?> items = upload.parseRequest(req);
				Iterator iter = items.iterator();

				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField()) {
						String name = item.getFieldName();
						String value = item.getString("UTF-8");
						if (name.equals("status") || name.equals("kind")) {
							m = clszz.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), int.class);
							m.invoke(pt, Integer.parseInt(value));
						} else if (name.equals("date")) {
							m = clszz.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), java.util.Date.class);
							m.invoke(pt, new Date());
						} else {
							m = clszz.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), java.lang.String.class);
							m.invoke(pt, value);
						}
					} else {
						String temp = item.getName();
						if (temp != null && temp != "") {
							String fieldName = item.getFieldName();
							String fileNamesuffix = temp.substring(temp.length() - 4);
							long fileName = System.currentTimeMillis();
							File uploadedFile = new File(Util.path + "/" + fileName + fileNamesuffix);
							m = clszz.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), java.lang.String.class);
							m.invoke(pt, fileName + fileNamesuffix);
							item.write(uploadedFile);
						}
					}
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Session s = HibernateUtil.getSessionFactory().getCurrentSession();
			s.beginTransaction();
			s.save(pt);
			s.getTransaction().commit();
			resp.sendRedirect("./pictext.jsp");
		}
	}
}

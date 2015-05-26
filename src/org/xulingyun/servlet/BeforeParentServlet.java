package org.xulingyun.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;
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
import org.hibernate.Session;
import org.xulingyun.service.dao.BeforeParent;
import org.xulingyun.service.dao.PicText;
import org.xulingyun.util.HibernateUtil;
import org.xulingyun.util.UseLog4j;
import org.xulingyun.util.Util;

import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class BeforeParentServlet
 */
@WebServlet(name = "beforeParentServlet", urlPatterns = "/beforeParentServlet")
public class BeforeParentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BeforeParentServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String method = req.getParameter("method");
		if (method.equals("del")) {
			BeforeParent bp = new BeforeParent();
			String params = req.getParameter("params");
			bp.setId(Integer.parseInt(params));
			s.beginTransaction();
			s.delete(bp);
			s.getTransaction().commit();
			resp.sendRedirect("./weekly.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String method = req.getParameter("method");
		if(method!=null&&method.equals("add")){
			req.setCharacterEncoding("UTF-8");
			Class<BeforeParent> clszz = null;
			BeforeParent bp = null;
			try {
				clszz = (Class<BeforeParent>) Class.forName("org.xulingyun.service.dao.BeforeParent");
				bp = (BeforeParent) clszz.newInstance();
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
				Iterator<?> iter = items.iterator();

				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField()) {
						String name = item.getFieldName();
						String value = item.getString("UTF-8");
						m = clszz.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), java.lang.String.class);
						m.invoke(bp, value);
					} else {
						String temp = item.getName();
						if (temp != null && temp != "") {
							String fieldName = item.getFieldName();
							String fileNamesuffix = temp.substring(temp.length() - 4);
							long fileName = System.currentTimeMillis();
							File uploadedFile = new File(Util.path + "/beforeparent/" + fileName + fileNamesuffix);
							m = clszz.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), java.lang.String.class);
							m.invoke(bp, fileName + fileNamesuffix);
							item.write(uploadedFile);
						}
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			s.beginTransaction();
			s.save(bp);
			s.getTransaction().commit();
			resp.sendRedirect("./weekly.jsp");
		}else{
			String sql = "from BeforeParent";
			s.beginTransaction();
			List<BeforeParent> list = (List<BeforeParent>)s.createQuery(sql).list();
			s.getTransaction().commit();
			Map<String, Object> map = new HashMap<String, Object>();
			for(int i=0;i<list.size();i++){
				list.get(i).setCookbook(list.get(i).getCookbook().substring(0, 3));
				list.get(i).setFocus(list.get(i).getFocus().substring(0, 3));
				list.get(i).setMamiRead(list.get(i).getMamiRead().substring(0, 3));
				list.get(i).setOutline(list.get(i).getOutline().substring(0, 3));
				list.get(i).setParentKnow(list.get(i).getParentKnow().substring(0, 3));
				list.get(i).setTaegyo(list.get(i).getTaegyo().substring(0, 3));
			}
			map.put("total", list.size());
			map.put("rows", list);
			String ss = JSONObject.toJSONString(map);
			out.print(ss);
			out.flush();
			out.close();
			out = null;
		}
	}

}

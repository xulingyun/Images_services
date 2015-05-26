package org.xulingyun.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.xulingyun.service.dao.Data;
import org.xulingyun.util.HibernateUtil;

import com.alibaba.fastjson.JSONObject;

@WebServlet(name = "passiveServlet", urlPatterns = "/passiveServlet")
public class PassiveServlet extends HttpServlet {
	private static final long serialVersionUID = 100864L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String area = request.getParameter("areaCode");
		System.out.println(area);
		if(area!=null&&!area.equals("")){
			Session s = HibernateUtil.getSessionFactory().getCurrentSession();
			s.beginTransaction();
//			Query sq = s.createSQLQuery("select * from Data where status=0 and area="+area);
			Query sq = s.createQuery("from Data where status=? and area=?").setInteger(0, 0).setInteger(1, Integer.parseInt(area));
			List<Data> list = sq.list();
			System.out.println(list.size()+":length");
			s.getTransaction().commit();
			PrintWriter out = new PrintWriter(new OutputStreamWriter(
					response.getOutputStream(), "UTF-8"));
			Map<String, Object> map=new HashMap<String, Object>();
			Class<?> clszz = null;
			Field[] f = null;
			Map<String, Object> map1 = null;
			for(int i=0;i<list.size();i++){
				map1 = new HashMap<String, Object>();
				try {
					clszz = Class.forName("org.xulingyun.service.dao.Data");
					f = clszz.getDeclaredFields();
					for(int j=0;j<f.length;j++){
						Method m = clszz.getMethod("get"+f[j].getName().substring(0, 1).toUpperCase()+f[j].getName().substring(1));
						map1.put(f[j].getName(), m.invoke(list.get(i)));
					}
					map.put(i+"", map1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String ss=JSONObject.toJSONString(map);
			System.out.println("ss:"+ss);
			out.print(ss);
			out.flush();
			out.close();
//			out= null;
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletInputStream sis = request.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		byte[] b = new byte[1024];
		int oop=-1;
		if((oop = sis.read(b))!=-1)
		{
			System.out.println(oop);
			baos.write(b);
		}
		System.out.println(baos.toString());
//		String ids = request.getParameter("id");
//		List list = Arrays.asList(ids.split(","));  
//		String sql = "delete from Data where id in(:ids)";
//		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
//		s.beginTransaction();
//		Query sq = s.createSQLQuery(sql);
//		sq.setParameterList("ids", list);
//		sq.executeUpdate();
//		s.getTransaction().commit();
	}

}

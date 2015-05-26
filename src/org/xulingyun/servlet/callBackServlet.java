package org.xulingyun.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.xulingyun.service.CoreService;
import org.xulingyun.service.dao.Data;
import org.xulingyun.service.dao.ImageInfo;
import org.xulingyun.util.HibernateUtil;
import org.xulingyun.util.UseLog4j;
import org.xulingyun.util.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class callBackServlet
 */
@WebServlet(name = "callBackServlet", urlPatterns = "/callBackServlet")
public class callBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public callBackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int status = 0;
		String data = request.getParameter("data");
		List<Data> datas = JSONObject.parseArray(data, Data.class);
		// Data datas = JSONObject.parseObject(data, Data.class);
		String[] idArray = new String[datas.size()];
		String[] areasArray = new String[datas.size()];
		int[] kindsArray = new int[datas.size()];
		String[] contentsArray = new String[datas.size()];
		for (int i = 0; i < datas.size(); i++) {
			idArray[i] = datas.get(i).getId() + "";
			areasArray[i] = datas.get(i).getArea();
			kindsArray[i] = datas.get(i).getKind();
			if(kindsArray[i]==CoreService.IMAGE){
				contentsArray[i] = datas.get(i).getContent();
				String tempUrl = getServletContext().getRealPath("/");
				String path = tempUrl.substring(0,tempUrl.length()-1)+"Image"+contentsArray[i].substring(0,contentsArray[i].length()-4);
				System.out.println(path + "_l.jpg");
				File[] f = new File[3];
				f[0] = new File(path + "_l.jpg");
				f[1] = new File(path + "_m.jpg");
				f[2] = new File(path + "_s.jpg");
				Util.deleteFile(f[0]);
				Util.deleteFile(f[1]);
				Util.deleteFile(f[2]);
			}
		}
		String sql = "delete from Data where id in(:ids) and area in(:areas)";
		Session s = null;
		try {
			s = HibernateUtil.getSessionFactory().getCurrentSession();
			s.beginTransaction();
			Query sq = s.createSQLQuery(sql);
			sq.setParameterList("ids", idArray);
			sq.setParameterList("areas", areasArray);
			sq.executeUpdate();
			s.getTransaction().commit();
			status = 1;
		} catch (Exception e) {
			status = 0;
			s.getTransaction().rollback();
//			s.flush();
		} finally {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
			out.print(status);
			out.flush();
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

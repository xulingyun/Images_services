package org.xulingyun.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.xulingyun.service.CoreService;
import org.xulingyun.service.dao.BeforeParent;
import org.xulingyun.service.dao.Data;
import org.xulingyun.util.HibernateUtil;
import org.xulingyun.util.Util;

import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class BeforeParentServlet
 */
@WebServlet(name = "weekServlet", urlPatterns = "/weekServlet")
public class WeekServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WeekServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Date d;
		long userTime = 0;
		long time = System.currentTimeMillis();
		String timeS = request.getParameter("appDate");
		String type = request.getParameter("type");
		String babyname = request.getParameter("babyname");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(timeS==null||timeS.equals("")){
				userTime = time;
			}else{
				d = sdf.parse(timeS);
				userTime = d.getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int day = (int) Math.floor((time-userTime)/86400000)+1;
		if(type!=null&&!type.equals("")&&type.equals("1")){
			if(day<=365){
				if(day<=0){
					day= 1;
				}
				response.sendRedirect("./showLife.jsp?day="+day+"&name="+babyname);
			}else{
				response.sendRedirect("./showKidLife.jsp?day="+day+"&name="+babyname);
			}
		}else{
			if(day<=0){
				day= 1;
			}
			response.sendRedirect("./showWeekly.jsp?day="+day);
		}
	}

}

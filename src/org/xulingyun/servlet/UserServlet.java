package org.xulingyun.servlet;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.xulingyun.service.dao.User;
import org.xulingyun.util.HibernateUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户相关
 * @author Administrator
 *
 */

@WebServlet(name = "UserServlet", urlPatterns = "/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		try {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
			String method = request.getParameter("method");
			String result = null;
			int userId = Integer.parseInt(request.getParameter("userId"));
			if(method.equals("add")){
				String  userPassword = request.getParameter("userPassword");
				result = addUser(userId,userPassword,out);
			}else if(method.equals("update")){
				String name = request.getParameter("name");
				String headicon = request.getParameter("headicon");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				long time = dateFormat.parse(request.getParameter("birthday")).getTime();
				Date birthday = new Date(time);
				int sex = Integer.parseInt(request.getParameter("sex"));
				String hobby = request.getParameter("hobby");
				updateUsre(userId,name,headicon,birthday,sex,hobby);
			}else if(method.equals("modifyPassword")){
				String  oldPassword = request.getParameter("oldPassword");
				String  newPassword = request.getParameter("newPassword");
				result = modifyPassword(userId,oldPassword,newPassword);
			}
			out.print(result);
			out.flush();
			out.close();
			out = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private String updateUsre(int userId, String name, String headicon, Date birthday, int sex, String hobby) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Map<String, Object> map = new HashMap<String, Object>();
		String hql="from User as user where user.userId=:userId";
		session.beginTransaction();
		Query query= session.createQuery(hql);
		query.setInteger("userId",userId);
		List<User> list =query.list();
		if(list.size()==1){
			User user = list.get(0);
			if(!isEmpty(name)){
				user.setName(name);
			}
			if(!isEmpty(headicon)){
				user.setHeadicon(headicon);
			}
//			if(birthday)){
				user.setBirthday(birthday);
//			}
			if(!isEmpty(sex)){
				user.setSex(sex);
			}
			if(!isEmpty(hobby)){
				user.setHobby(hobby);
			}
			session.saveOrUpdate(user);
			map.put("status", 1);
			map.put("errorInfo", "用户资料更新成功！");
			session.getTransaction().commit();
			return JSONObject.toJSONString(map);
		}else{
			map.put("status", 0);
			map.put("errorInfo", "用户不存在！");
			session.getTransaction().commit();
			return JSONObject.toJSONString(map);
		}
	}

	private String modifyPassword(int userId, String oldPassword, String newPassword) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Map<String, Object> map = new HashMap<String, Object>();
		String hql="from User as user where user.userId=:userId and user.userPassword=:userPassword";
		session.beginTransaction();
		Query query= session.createQuery(hql);
		query.setInteger("userId",userId);
		query.setString("userPassword",oldPassword);
		List<User> list =query.list();
		if(list.size()==1){
			User user = list.get(0);
			user.setUserPassword(newPassword);
			session.saveOrUpdate(user);
			session.getTransaction().commit();
			map.put("status", 1);
			map.put("errorInfo", "密码修改成功！");
			return JSONObject.toJSONString(map);
		}else{
			map.put("status", 0);
			map.put("errorInfo", "用户不存在或者密码不正确！");
			session.getTransaction().commit();
			return JSONObject.toJSONString(map);
		}
	}

	private String addUser(int userId,String userPassword,PrintWriter out) {
		// TODO 检查用户是否存在
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Map<String, Object> map = new HashMap<String, Object>();
		String hql="from User as user where user.userId=:userId";
		session.beginTransaction();
		Query query= session.createQuery(hql);
		query.setInteger("userId",userId);
		List<User> list =query.list();
		if(list.size()>=1){
			map.put("status", 0);
			map.put("errorInfo", "用户已存在！");
			session.getTransaction().commit();
			return JSONObject.toJSONString(map);
		}else{
			User user = new User();
			user.setUserId(userId);
			user.setUserPassword(userPassword);
			session.save(user);
			session.getTransaction().commit();
			map.put("status", 1);
			map.put("errorInfo", "用户创建成功！");
			return JSONObject.toJSONString(map);
		}
	}
	
	public boolean isEmpty(String str){
		if(str.equals("")||str==null){
			return true;
		}
		return false;
	}
	
	public boolean isEmpty(int num){
		if(num<0){
			return true;
		}
		return false;
	}

}

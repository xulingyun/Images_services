package org.xulingyun.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MyConnection {
	
//	static Context ctx = null;
//	static DataSource ds = null;
//	
//	static {
//		try {
//			ctx = new InitialContext();
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//		try {
//			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/weixin");
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@SuppressWarnings("finally")
//	public static Connection getConnection() {
//		Connection conn = null;
//		
////		try {
////			UseLog4j.info(MyConnection.class, "********");
////			Class.forName("com.mysql.jdbc.Driver").newInstance();
////			String url ="jdbc:mysql://183.60.27.111:3306/weixin_iptv?user=eduwx&password=n8qV5Ufd62Y7Wtpy&characterEncoding=UTF-8";
////			//myDB为数据库名 
////			conn= DriverManager.getConnection(url);
////			UseLog4j.info(MyConnection.class, conn.toString()+"********");
////		}catch (Exception e1) {
////			e1.printStackTrace();
////		}
//		
//		try {
//			conn = ds.getConnection();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		finally {
//			return conn;
//		}
//	}
//
//	public static boolean close(Connection conn) {
//		try {
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return true;
//	}
}

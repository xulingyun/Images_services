package org.xulingyun.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xulingyun.service.CoreService;
import org.xulingyun.util.SignUtil;
import org.xulingyun.util.UseLog4j;
import org.xulingyun.util.Util;

@WebServlet(name = "coreServlet", urlPatterns = "/coreServlet")
public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;
	public static String basePath;
	static {
		File f1 = new File(Util.path);
		if (!f1.exists()) {
			f1.mkdir();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		SignUtil su = new SignUtil();
		if (su.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(
				response.getOutputStream(), "UTF-8"));
//		Enumeration<String> eee= request.getParameterNames();
//		while(eee.hasMoreElements()){
//			UseLog4j.info(CoreServlet.class,"参数："+eee.nextElement());
//		}
		String signature = request.getParameter("signature");
		String msg_signature = request.getParameter("msg_signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		CoreService cs = new CoreService(getServletContext(), request,out);
		String respMessage = cs.processRequest(msg_signature,timestamp,nonce);
		out.print(respMessage);
		out.flush();
		out.close();
	}
}

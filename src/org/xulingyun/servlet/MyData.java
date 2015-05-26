package org.xulingyun.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xulingyun.service.CoreService;
import org.xulingyun.util.PhpBackData;
import org.xulingyun.util.UseLog4j;

/**
 * Servlet implementation class MyData
 */
@WebServlet(name ="MyData" ,description = "修改个人资料", urlPatterns = "/MyData")
public class MyData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
//		StringBuffer sb = new StringBuffer();
//		String line=null;
//		while((line=br.readLine())!=null){
//			sb.append(line);
//		}
//		UseLog4j.info(this.getClass(), sb.toString());
//		
//
//		String telephone = request.getParameter("telephone");
//		String qq = request.getParameter("qq");
//		String address = request.getParameter("address");
//		String content = "";
//		if(telephone!=null&&telephone!=""){
//			content ="手机："+telephone+",";
//		}
//		if(qq!=null&&qq!=""){
//			content ="qq："+qq+",";
//		}
//		if(address!=null&&address!=""){
//			content ="address："+address+",";
//		}
//		if(content.length()!=0){
//			content = content.substring(0, content.length()-1);
//		}
//				
//				
//		PrintWriter out = new PrintWriter(new OutputStreamWriter(
//				response.getOutputStream(), "UTF-8"));
//		CoreService cs = new CoreService();
//		String state = cs.httpCon(getServletContext(), splice[0],splice[1], content.substring(1).trim(), "1");
//		PhpBackData backData = cs.parseJson(state);
//		out.print(backData.getInfo());
//		out.flush();
//		out.close();
	}

}

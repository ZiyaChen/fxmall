package com.qianfeng.fxmall.goods.controller;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * �Զ���Servlet�ĸ���
 * @author ZQ
 *
 */
public class BaseServlet extends HttpServlet{


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		//获得方法名
		String m = req.getParameter("m");

		//ͨ通过反射获得方法
		try {
			Method method = getClass().getMethod(m, HttpServletRequest.class,HttpServletResponse.class);
			System.out.println("执行了方法:" +m);
			method.invoke(this,req, resp);
			System.out.println("结束了方法:" +m);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

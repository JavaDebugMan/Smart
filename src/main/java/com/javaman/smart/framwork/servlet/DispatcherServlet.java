package com.javaman.smart.framwork.servlet;

import com.javaman.smart.framwork.controller.Data;
import com.javaman.smart.framwork.controller.Handler;
import com.javaman.smart.framwork.controller.Param;
import com.javaman.smart.framwork.controller.View;
import com.javaman.smart.framwork.helper.BeanHelper;
import com.javaman.smart.framwork.helper.ConfigHelper;
import com.javaman.smart.framwork.helper.ControllerHelper;
import com.javaman.smart.framwork.helper.HelperLoader;
import com.javaman.smart.framwork.util.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pengzhe
 * @date 2018/2/27 11:16
 * @description 请求转发器
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化相关Helper类
        HelperLoader.init();
        //获取ServletContext对象(用于注册Servlet)
        ServletContext servletContext = servletConfig.getServletContext();
        //注册处理JSP的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求方法与请求路径
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();
        //获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            //获取Controller类及其Bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            Map<String, Object> paramMap = new HashMap<String, Object>();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodeUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if (StringUtil.isNotEmpty(body)) {
                String[] params = StringUtils.split(body, "&");
                if (ArrayUtil.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = StringUtils.split(param, "=");
                        if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            //调用Action方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            //处理Action方法返回值
            if (result instanceof View) {
                //返回JSP页面
                View view = (View) result;
                String path = view.getPath();
                if (StringUtil.isNotEmpty(path)) {
                    if (path.startsWith("/")) {
                        response.sendRedirect(request.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                        request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
                    }
                }
            } else if (request instanceof Data) {
                //返回JSON数据
                Data data = (Data) result;
                Object model = data.getModel();
                if (model!=null) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }

    }
}

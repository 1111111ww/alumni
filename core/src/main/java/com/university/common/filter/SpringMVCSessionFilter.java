package com.university.common.filter;

import com.university.alumni.entity.User;
import com.university.common.util.NoCheckUrlsUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;



/*
public class SpringMVCSessionFilter extends OncePerRequestFilter {

    // 未登录跳转页面
    private String redirectURL;

    // 不登陆可访问URL列表
    private List<String> noCheckUrlList;

    // 用户在session中存储的key值
    private String checkSessionKey;

    // 日志
    Logger logger = Logger.getLogger(SpringMVCSessionFilter.class);



    */
/*@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        FilterConfig filterConfig = this.getFilterConfig();

        redirectURL = filterConfig.getInitParameter("redirectURL");
        checkSessionKey = filterConfig.getInitParameter("checkSessionKey");
        String noCheckUrlStr = filterConfig.getInitParameter("noCheckUrl");
        String moduleName = filterConfig.getInitParameter("moduleName");
        String defaultRedirctUrl = "";

        if("admin".equals(moduleName)){
            noCheckUrlStr = NoCheckUrlsUtils.GenerateAdminNoCheckUrls();
            defaultRedirctUrl = "/user/userListPage.do";
        }else if ("client".equals(moduleName)){
            noCheckUrlStr = NoCheckUrlsUtils.GenerateClientNoCheckUrls();
            defaultRedirctUrl = "/match/commonMatchEventPage.do?matchType=1";
        }else if ("race".equals(moduleName)){
            noCheckUrlStr = NoCheckUrlsUtils.GenerateRaceNoCheckUrls();
            defaultRedirctUrl = "/index.jsp";
        }

        // 组装不登陆可访问URL列表
        if (noCheckUrlList == null || noCheckUrlList.size() == 0) {
            noCheckUrlList = new ArrayList<String>();
            if (noCheckUrlStr != null && !"".equals(noCheckUrlStr.trim()) ) {
                // 去除空格
                noCheckUrlStr = noCheckUrlStr.replaceAll(" ","");
                // 分割为数组
                String[] noCheckUrls = noCheckUrlStr.split(";");
                // 组装
                if(noCheckUrls != null && noCheckUrls.length > 0){
                    for(int i = 0; i < noCheckUrls.length; i++){
                        noCheckUrlList.add(noCheckUrls[i]);
                    }
                }
            }
        }

        String uri  = request.getRequestURI();

        //logger.info("uri = "+uri);
        Enumeration params = request.getParameterNames();
        String paramsStr = "";

        // 从session中获取登录者实体
        User userModelTemp = (User) request.getSession().getAttribute(checkSessionKey);
        if (userModelTemp != null) {
            //paramsStr = paramsStr + "[peopleId = "+userModelTemp.getId()+",mobile = "+userModelTemp.getMobile()+"]";
        }

        while (params.hasMoreElements()){
            String paramName = String.valueOf(params.nextElement());
            String paramValue = request.getParameter(paramName);
            //logger.info(paramName + " = " +paramValue);
            paramsStr = paramsStr + "&" + paramName + "=" + paramValue;
        }
        paramsStr = paramsStr.replaceFirst("&","?");

        if(!uri.equals("/server/alive.do")){
            logger.info(uri+paramsStr);
        }


        //System.out.println("");

        if("/".equals(uri)||"".equals(uri)){
            uri = request.getContextPath() + defaultRedirctUrl;
            response.sendRedirect(uri);
            return;
        }

        // 检查URI是否是不需登陆的URI
        boolean bl = isNoCheckRequestUri(uri);


        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        UserService userService = (UserService) webApplicationContext.getBean("userService");

        RedisDao redisDao = (RedisDao) webApplicationContext.getBean("redisDao");

        //appToken
        String authToken = request.getHeader("Authorization");

        if(authToken!=null && !"".equals(authToken.trim())){
            logger.info("authToken = "+authToken);
            AppToken appToken = userService.getAppTokenByToken(authToken);
            if(appToken != null){
                request.getSession().setAttribute(checkSessionKey,null);
                Date now = new Date();
                if(now.getTime() < appToken.getFailureTime().getTime()){
                    UserModel userModel = userService.loadUserModelAll(appToken.getUserId());
                    request.getSession().setAttribute(checkSessionKey, userModel);
                }
            }
        }
        //appToken


        if (bl) {
            // 不执行过滤，继续
            filterChain.doFilter(request, response);
        }else{




            // 从session中获取登录者实体
            UserModel userModel = (UserModel) request.getSession().getAttribute(checkSessionKey);
            if (userModel == null) {
                if(authToken!=null && !"".equals(authToken.trim())){
                    JsonResponseResult result = new JsonResponseResult();
                    result.setCode(999);
                    result.setMsg("用户超时");
                    String resultJson = BaseUtils.toJsonFromObject(result);

                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.print(resultJson);
                    out.flush();
                    out.close();
                }else{
                    String redirectUri = request.getContextPath() + redirectURL;
                    response.sendRedirect(redirectUri);
                    return;
                }
            } else {

                // 判断多终端登陆


                ServletContext servletContext = request.getSession().getServletContext();
                Map<Object,Object> loginMap = new HashMap<Object,Object>();
                loginMap = (Map<Object, Object>) servletContext.getAttribute("loginMap");
                if(loginMap!=null){
                    String sessionId = String.valueOf(loginMap.get(userModel.getId()));
                    String currentSessionId = request.getSession().getId();
                    if(!currentSessionId.equals(sessionId)&&(authToken==null || "".equals(authToken.trim()))){
                        String redirectUri = request.getContextPath() + redirectURL;
                        response.sendRedirect(redirectUri);
                        return;
                    }
                }





                LoginMap loginMap = redisDao.getLoginMap();
                if(loginMap!=null){
                    String sessionId = String.valueOf(loginMap.getLoginMap().get(userModel.getId()));
                    String currentSessionId = request.getSession().getId();
                    if(!currentSessionId.equals(sessionId)&&(authToken==null || "".equals(authToken.trim()))){
                        String redirectUri = request.getContextPath() + redirectURL;
                        response.sendRedirect(redirectUri);
                        return;
                    }
                }



                String currentSessionId = request.getSession().getId();
                String userSessionId = redisDao.getLoginSessionId(userModel.getId());

                if(userSessionId!=null && !"".equals(userSessionId.trim())){
                    if( !userSessionId.equals(currentSessionId) &&(authToken==null || "".equals(authToken.trim())) ){
                        String redirectUri = request.getContextPath() + redirectURL;
                        response.sendRedirect(redirectUri);
                        return;
                    }
                }

                // 权限控制
                List<FunctionModel> functionModels = userModel.getRoleModel().getFunctionModels();
                // 是否有权限
                boolean auth = false;
                if(functionModels != null && functionModels.size() > 0){
                    for(int i = 0; i < functionModels.size(); i++){
                        if(functionModels.get(i) != null) {
                            String functionUri = functionModels.get(i).getUri();
                            if (functionUri != null) {
                                if (uri.indexOf(functionUri) >= 0) {
                                    auth = true;
                                    break;
                                }
                            }
                        }
                    }
                }

                //测试
                auth = true;

                if(auth){
                    // 如果有权限访问，继续
                    filterChain.doFilter(request, response);

                }else{

                    JsonResponseResult result = new JsonResponseResult();
                    result.setCode(1);
                    result.setMsg("权限不足!");
                    String resultJson = BaseUtils.toJsonFromObject(result);

                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.print(resultJson);
                    out.flush();
                    out.close();

                }

            }

        }
    }
*//*

    // 是否是不需登陆的URI
    private boolean isNoCheckRequestUri(String uri) {
        boolean result = false;
        if(noCheckUrlList != null && noCheckUrlList.size() > 0 && uri != null && !"".equals(uri.trim())){
            for(int i = 0; i < noCheckUrlList.size(); i++){
                String noCheckUrl = noCheckUrlList.get(i);
                if(noCheckUrl != null){
                    if(uri.indexOf(noCheckUrl) >= 0){
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }
}
*/

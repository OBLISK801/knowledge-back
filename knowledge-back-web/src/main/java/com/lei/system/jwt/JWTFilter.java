package com.lei.system.jwt;

import com.google.gson.Gson;
import com.lei.error.SystemCodeEnum;
import com.lei.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @Author LEI
 * @Date 2021/2/16 20:49
 * @Description 创建 JWTFilter 实现前端请求统一拦截及处理
 * 1.LOGIN_SIGN 是前端放置在 headers 头文件中的登录标识，
 * 如果用户发起的请求是需要登录才能正常返回的，那么头文件中就必须存在该标识并携带有效值
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {
//    // 登录标识
//    private static String LOGIN_SIGN = "Authorization";
//
//    /**
//     * 检测用户是否登录
//     * 检测header里面是否包含Authorization字段即可
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    @Override
//    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
//        HttpServletRequest req = (HttpServletRequest) request;
//        String authorization = req.getHeader(LOGIN_SIGN);
//        return authorization != null;
//    }
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        if (isLoginAttempt(request, response)) {
//            HttpServletRequest req = (HttpServletRequest) request;
//            String token = req.getHeader(LOGIN_SIGN);
//            JWTToken jwtToken = new JWTToken(token);
//            try {
//                SecurityUtils.getSubject().login(jwtToken);
//            } catch (AuthenticationException e) {
//                log.error(e.getMessage());
//                responseTokenError(response, e.getMessage());
//                return false;
//            }
//        } else {
//            responseTokenError(response, "Token无效，您无权访问该接口");
//            return false;
//        }
//        return true;
//    }

    /**
     * 认证之前执行该方法
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = SecurityUtils.getSubject();
        return  null != subject && subject.isAuthenticated();
    }

    /**
     * 认证未通过执行该方法
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response){
        //完成token登入
        //1.检查请求头中是否含有token
        HttpServletRequest httpServletRequest= (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");
        //2. 如果客户端没有携带token，拦下请求
        if(null==token||"".equals(token)){
            responseTokenError(response,"Token无效，您无权访问该接口");
            return false;
        }
        //3. 如果有，对进行进行token验证
        JWTToken jwtToken = new JWTToken(token);
        try {
            SecurityUtils.getSubject().login(jwtToken);
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            responseTokenError(response,e.getMessage());
            return false;
        }

        return true;
    }


    /**
     * 对跨域提供支持
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 无需转发，直接返回Response信息 Token认证错误
     */
    private void responseTokenError(ServletResponse response, String msg) {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = httpServletResponse.getWriter()) {
            HashMap<String, Object> errorData = new HashMap<>();
            errorData.put("errorCode", SystemCodeEnum.TOKEN_ERROR.getErrorCode());
            errorData.put("errorMsg", SystemCodeEnum.TOKEN_ERROR.getErrorMsg());
            ResponseModel<HashMap<String, Object>> result = ResponseModel.error(errorData);
            String data = new Gson().toJson(result);
            out.append(data);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}

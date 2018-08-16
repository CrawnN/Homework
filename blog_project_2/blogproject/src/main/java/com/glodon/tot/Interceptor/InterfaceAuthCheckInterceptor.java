package com.glodon.tot.Interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glodon.tot.dto.ResponseDate;

import com.glodon.tot.dto.UserProps;
import com.glodon.tot.models.User;
import com.glodon.tot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author Crawn 拦截器，通过token对用户进行验证
 *
 */
class InterfaceAuthCheckInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        System.out.println("Crawn After");
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        System.out.println("Crawn Post");
    }

    @Override
    @CrossOrigin
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj)
            throws Exception {
        StringBuffer sb = new StringBuffer("");
        long userId=0;
        String token=null;
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();
            String params = sb.toString();
            System.out.println(params);
            JSONObject object =JSON.parseObject(params);
            userId= (Integer) object.get("userId");
            token= (String) object.get("token");
            System.out.println(token);
        }
        catch (Exception e){
            }
        //
        if (StringUtils.isEmpty(token)) {
            response.setContentType("application/json;charset=utf-8");
            //response.getWriter().write(JSON.toJSONString(new Result(Error.INCOMPLETE_API_AUTHEN_INFO.getCode(), Error.INCOMPLETE_API_AUTHEN_INFO.getMessage())));
            // 定义返回数据
            ResponseDate responseDate=new ResponseDate();
            responseDate.setCode(402);
            responseDate.setMessage("用户没登录，请登录！");
            responseDate.setData(null);
            try{
                JSONObject res = new JSONObject();
                PrintWriter out = response.getWriter();
                out.print(JSONObject.toJSONString(responseDate));
                out.flush();
                return false;
            }
            catch (Exception e){
                responseDate.setCode(403);
                responseDate.setMessage("程序运行出错，请重新访问！");
                JSONObject res = new JSONObject();
                PrintWriter out = response.getWriter();
                out.print(JSONObject.toJSONString(responseDate));
                out.flush();
                e.printStackTrace();
                return false;
            }
        } else {
//            logger.info("test redis import :" );
            // TODO 验证逻辑

            System.out.println("成功");
            //User user = new UserService().select(userId);
            return true;
        }
    }

}
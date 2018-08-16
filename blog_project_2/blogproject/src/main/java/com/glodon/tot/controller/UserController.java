package com.glodon.tot.controller;

import com.glodon.tot.dto.ResponseDate;
import com.glodon.tot.dto.UserProps;
import com.glodon.tot.models.User;
import com.glodon.tot.service.UserService;
import com.glodon.tot.util.TokenGenerater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * @author Crawn 用户相关请求处理
 */
@RestController
@RequestMapping
public class UserController {
    @Autowired
    private UserService userService;

//    @Transactional
//    @RequestMapping(value = "/api/login",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")

    /**
     * 核对用户登录
     * @param userProps name 用户名 password 密码
     * @return long  402：用户不存在 403：用户密码不正确 200： 用户密码正确，返回用户ID
     */
    @CrossOrigin
    @Transactional
    @PostMapping("/api/login")
    public ResponseDate check(@RequestBody UserProps userProps){
//        JSONObject result = new JSONObject();
        // 定义返回数据
        ResponseDate responseDate=new ResponseDate();
        // 前端数据转成后端数据并查询
        User user=userService.check(userProps.getUserName());
        UserProps responseUserDto=new UserProps();
        if (user==null||!user.getPassword().equals(userProps.getPassword())){
            if (user ==null ) {
                responseDate.setCode(402);
                responseDate.setMessage("无此用户！");
                responseDate.setData(responseUserDto);
            } else {
                responseDate.setCode(403);
                responseDate.setMessage("密码错误！");
                responseDate.setData(responseUserDto);
            }
        }else {
            String content=userProps.getUserId()+","+userProps.getUserName();
            String token= TokenGenerater.makeToken(content);
            System.out.println(TokenGenerater.base64decode(token));
            responseUserDto.setToken(token);
            responseDate.setCode(200);
            responseDate.setMessage("登陆成功！");
            responseUserDto.setUserName(user.getName());
            responseUserDto.setUserId(user.getUserId());
            responseDate.setData(responseUserDto);
        }
        return responseDate;
    }

    /**
     * * 返回用户ID
     * @param userProps UserProps 用户名信息
     * @return ResponseDate  402： 用户不存在 ; 200： 成功 用户ID
     */
    @CrossOrigin
    @Transactional
//    @ResponseBody
//    @RequestMapping(value = "/api/checkuser",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @PostMapping("/api/checkuser")
    public ResponseDate select(@RequestParam UserProps userProps){
        // 前端数据转成后端数据
        User user=userService.check(userProps.getUserName());
        // 定义返回数据
        ResponseDate responseDate=new ResponseDate();
        UserProps userProps1=new UserProps();
        if (user==null) {
            responseDate.setCode(402);
            responseDate.setMessage("无此用户！");
            responseDate.setData(user);
        }else {
            responseDate.setCode(200);
            responseDate.setMessage("查询此用户成功，并返回ID！");
            userProps1.setUserName(user.getName());
            userProps1.setUserId(user.getUserId());
            responseDate.setData(userProps1);
        }
        return responseDate;
    }

    /**
     *用户注册
     * @param userProps 用户信息
     * @return 403 用户已存在 200 用户创建成功 402 用户创建不成功，请重新创建
     */
    @CrossOrigin
    @Transactional
//    @ResponseBody
//    @RequestMapping(value = "/api/register",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @PostMapping("/api/register")
    public ResponseDate add(@RequestBody UserProps userProps){
        // 前端模型转换成后端模型
        User user=userService.check(userProps.getUserName());
        // 定义返回数据
        ResponseDate responseDate=new ResponseDate();
        if (user==null){
            user=new User();
            user.setName(userProps.getUserName());
            user.setPassword(userProps.getPassword());
            user.setCategory(userProps.getCategory());
            if (!userService.insert(user)){
                responseDate.setCode(402);
                responseDate.setMessage("程序出错，用户创建不成功！");
                responseDate.setData(null);
            }else {
                responseDate.setCode(200);
                responseDate.setMessage("用户创建成功！");
                responseDate.setData(userProps);
            }
        }else {
            responseDate.setCode(403);
            responseDate.setMessage("用户已存在，请重新命名！");
            responseDate.setData(null);
        }
        return responseDate;

    }

    /**
     * 删除用户
     * @param userProps UserProps 用户ID
     * @return int 200 删除用户成功 403 无此用户 402 删除操作有问题，请检查后台记录
     */
    @CrossOrigin
    @Transactional
//    @ResponseBody
//    @RequestMapping(value = "/api/deleteuser",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @PostMapping("/api/deleteuser")
    public ResponseDate  delete(@RequestBody UserProps userProps){
        // 定义返回数据
        ResponseDate responseDate = new ResponseDate();
        // 记录删除的用户个数：
        int deleteLine=userService.delete(userProps.getUserId());
        if (deleteLine==1){
            responseDate.setCode(200);
            responseDate.setMessage("用户删除成功！");
            responseDate.setData(userProps);
        }else if (deleteLine==0){
            responseDate.setCode(403);
            responseDate.setMessage("用户ID输错，无此用户！");
            responseDate.setData(userProps);
        }else {
            responseDate.setCode(402);
            responseDate.setMessage("删除操作有问题，请检查后台记录！");
            responseDate.setData(userProps);
        }
        return responseDate;
    }

    /**
     * 更新用户信息
     * @param userProps 用户名 password  密码 catagory 类型
     * @return 403 没有此用户 200 更新成功 201 无任何信息需要更新 402 更新操作有问题，请检查后台记录！
     */
    @CrossOrigin
    @Transactional
//    @ResponseBody
//    @RequestMapping(value = "/api/updateuser",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @PostMapping("/api/updateuser")
    public ResponseDate update(@RequestBody UserProps userProps){
        // 定义返回数据
        ResponseDate responseDate = new ResponseDate();
        User user=userService.check(userProps.getUserName());
        if (user!=null){
            user.setName(userProps.getUserName());
            user.setPassword(userProps.getPassword());
            user.setCategory(userProps.getCategory());
            int updateCount=userService.update(user);
            if (updateCount==1){
                responseDate.setCode(200);
                responseDate.setMessage("用户更新成功！");
                responseDate.setData(userProps);
            }else if(updateCount==0) {
                responseDate.setCode(201);
                responseDate.setMessage("无用户信息需要更新！");
                responseDate.setData(userProps);
            }else {
                responseDate.setCode(402);
                responseDate.setMessage("更新操作有问题，请检查后台记录！");
                responseDate.setData(userProps);
            }
        }else {
            responseDate.setCode(403);
            responseDate.setMessage("用户ID输错，无此用户！");
            responseDate.setData(userProps);
        }
        return responseDate;
    }


}

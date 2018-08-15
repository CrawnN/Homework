package com.glodon.tot.controller;

import com.glodon.tot.dto.ResponseDate;
import com.glodon.tot.dto.UserProps;
import com.glodon.tot.models.User;
import com.glodon.tot.service.UserService;
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

    /**
     * 核对用户登录
     * @param userProps name 用户名 password 密码
     * @return long  -2：用户不存在 -1：用户密码不正确 其他： 用户密码正确，返回用户ID
     */
//    @Transactional
//    @RequestMapping(value = "/api/login",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @PostMapping("/api/login")
    public ResponseDate check(@RequestBody UserProps userProps){
//        JSONObject result = new JSONObject();
        ResponseDate responseDate=new ResponseDate();
        User user=userService.check(userProps.getUserName());
        UserProps responseUserDto=new UserProps();
        if (user==null||!user.getPassword().equals(userProps.getPassword())){
            if (user ==null ) {
                responseDate.setCode(403);
                responseDate.setMessage("无此用户！");
                responseDate.setData(responseUserDto);
            } else {
                responseDate.setCode(403);
                responseDate.setMessage("密码错误！");
                responseDate.setData(responseUserDto);
            }
        }else {
            responseUserDto.setToken(user.getUserId().toString());
            responseDate.setCode(200);
            responseDate.setMessage("登陆成功！");
            responseDate.setData(responseUserDto);
        }
        return responseDate;
    }

    /**
     * * 返回用户ID
     * @param name 用户名
     * @return long  0 用户不存在 其他 用户ID
     */
    @CrossOrigin
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/api/checkuser",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public long select(@RequestParam String name){
        User user=userService.check(name);
        if (user==null) {
            return 0;
        }
        return user.getUserId();
    }

    /**
     *用户注册
     * @param userProps 用户信息
     * @return 0 用户已存在 1 用户创建成功 2 用户创建不成功，请重新创建
     */
    @CrossOrigin
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/api/signinuser",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public int add(@RequestBody UserProps userProps){
        int state=0;
        User user=userService.check(userProps.getUserName());
        if (user==null){
            state=1;
            user.setName(userProps.getUserName());
            user.setPassword(userProps.getPassword());
            user.setCategory(userProps.getCategory());
            if (!userService.insert(user)){
                state = 2;
            }
        }
        return state;

    }

    /**
     * 删除用户
     * @param userid 用户ID
     * @return int 1 删除用户成功 2 没有找到此用户
     */
    @CrossOrigin
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/api/deleteuser",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public int  delete(@RequestParam long userid){
        return userService.delete(userid);
    }

    /**
     * 更新用户信息
     * @param userProps 用户名 password  密码 catagory 类型
     * @return 0 没有此用户 1 更新成功 2 无任何信息需要更新
     */
    @CrossOrigin
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/api/updateuser",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public int update(@RequestBody UserProps userProps){
        int state=0;
        User user=userService.check(userProps.getUserName());
        if (user!=null){
            state=1;
            user.setName(userProps.getUserName());
            user.setPassword(userProps.getPassword());
            user.setCategory(userProps.getCategory());
            if (userService.update(user)!=1){
                state = 2;
            }
        }
        return state;
    }


}

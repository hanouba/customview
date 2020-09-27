package com.mxdl.springboot.controller;

import com.mxdl.springboot.bean.AddUserResponse;
import com.mxdl.springboot.bean.BaseResponse;
import com.mxdl.springboot.bean.User;
import com.mxdl.springboot.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    //1.用户登录
    @GetMapping("/login/get")
    public BaseResponse login(@RequestParam("username") String username, @RequestParam("password") int password) {
        logger.info("login start...");
//        try {
//            Thread.sleep(1000 * 6 );
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        if ("mxdl".equals(username) && password == 123456) {
            return new BaseResponse("登录成功", 0);
        } else {
            return new BaseResponse("登录失败", -1);
        }
    }

    //2.用户注册
    @PostMapping("/addUser/post")
    public AddUserResponse addUser(@RequestBody User user) {
        if (user.getUsername() != null && user.getPassword() > 0) {
            return new AddUserResponse("创建成功", 0, user);
        } else {
            return new AddUserResponse("创建失败", 0);
        }
    }

    //1.用户登录
    @GetMapping("/login/get/map")
    public BaseResponse loginQueryMap(@RequestParam Map<String,String> loginMap) {

        logger.info("login start...");
        if ("mxdl".equals(loginMap.get("username")) && "123456".equals(loginMap.get("password"))) {
            return new BaseResponse("登录成功", 0);
        } else {
            return new BaseResponse("登录失败", -1);
        }
    }
    //1.用户登录
    @PostMapping("/login/form")
    public BaseResponse loginForm(@RequestParam("username") String username, @RequestParam("password") int password) {
        logger.info("loginForm start...");
        if ("mxdl".equals(username) && password == 123456) {
            return new BaseResponse("登录成功", 0);
        } else {
            return new BaseResponse("登录失败", -1);
        }
    }

    //1.用户登录
    @PostMapping("/login/form/map")
    public BaseResponse loginFormMap(@RequestParam Map<String,String> loginMap) {
        logger.info("loginFormMap start...");
        if ("mxdl".equals(loginMap.get("username")) && "123456".equals(loginMap.get("password"))) {
            return new BaseResponse("登录成功", 0);
        } else {
            return new BaseResponse("登录失败", -1);
        }
    }

    //1.用户登录
    @GetMapping("/login/get/url")
    public BaseResponse loginUrl(@RequestParam("username") String username, @RequestParam("password") int password) {
        logger.info("loginUrl start...");
        if ("mxdl".equals(username) && password == 123456) {
            return new BaseResponse("登录成功", 0);
        } else {
            return new BaseResponse("登录失败", -1);
        }
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int id){
        if(id == 1){
            return new User("zhangsan",11111);
        }else{
            return new User("lisi",11111);
        }
    }


    //1.用户登录
    @GetMapping("/login/get/header")
    public BaseResponse loginHeader(@RequestHeader("a") String a,@RequestHeader("b") String b,@RequestHeader("c") String c,@RequestParam("username") String username, @RequestParam("password") int password) {
        logger.info("loginHeader start...");
        logger.info("a:"+a+";b:"+b+";c:"+c);
        if ("mxdl".equals(username) && password == 123456) {
            return new BaseResponse("登录成功", 0);
        } else {
            return new BaseResponse("登录失败", -1);
        }
    }
}

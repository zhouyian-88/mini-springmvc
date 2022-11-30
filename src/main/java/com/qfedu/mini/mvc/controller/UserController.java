package com.qfedu.mini.mvc.controller;

import com.qfedu.mini.mvc.annotation.Controller;
import com.qfedu.mini.mvc.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @athor:zhouhaohui
 * @email:2873642764@qq.com
 * @desc:
 * @datetime:2022-11-30-19:20
 */
@Controller
public class UserController {
    @RequestMapping("/user/login")
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.getWriter().write("hello");

    }

}

package com.ran.ssm.web.controller;


import com.ran.ssm.domain.User;
import com.ran.ssm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Controller
public class UserController {
    //注入userService就是相当于实例化一个UserService对象
    @Resource(name="userService")
    private UserService us ;

    /**
     * 查看全部user
     */
    @RequestMapping("/user/findall")
    public String findAll(Model m ){
        List<User> list = us.selectAll();
        m.addAttribute("allUsers",list);
        return "user/userList" ;
    }

    @RequestMapping("/user/findPage")
    public String findPage(Model m ,@RequestParam("pn") int pn){ //pn是页号
        //查询总记录数
        int counts = us.selectCount();

        //每页记录数
        int recordPerPage = 5 ;

        //结算页数
        int pages = 0 ;
        //刚好整除
        if(counts % recordPerPage == 0){
            pages = counts / recordPerPage ;
        }
        else{
            pages = counts / recordPerPage + 1;
        }

        //偏移量
        int offset = (pn - 1) * recordPerPage ;
        List<User> list = us.selectPage(offset, recordPerPage);
        m.addAttribute("allUsers", list);
        m.addAttribute("pages",pages) ;
        return "user/userList";
    }






    /**
     * 删除一个user
     * @param uid
     * @return
     */
    @RequestMapping("user/deleteUser")
    public String deleteUser(@RequestParam("uid") Integer uid){
        us.delete(uid);
        return "redirect:/user/findall";
    }


    //    面向对象的接收,将传过来的参数都封装到user中
    @RequestMapping(value = "/user/userSave")
    public String saveUser(User user){
        us.save(user);
        //改完之后显示信息
        return "redirect:/user/findall" ;
    }
    @RequestMapping("/user/toAddUserPage")
    public String toAddUserPage(){
        return "user/editUser";
    }
    //    根据浏览器那边提供的uid，将对应的用户信息回显在浏览器上
    @RequestMapping("/user/editOne")
    public String editOne(Model m ,@RequestParam("uid") Integer uid){
        User u = us.selectOne(uid);
        m.addAttribute("user",u);
        return "/user/editUser" ;
    }


    @RequestMapping("/user/hello")
    public String accessHtml(){
        System.out.println("hellohello");
        return "forward:/html/hello.html" ;
    }
}
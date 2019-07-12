package com.shuiyou.myspringboot.controller;

import com.shuiyou.myspringboot.common.CommonPage;
import com.shuiyou.myspringboot.common.CommonResult;
import com.shuiyou.myspringboot.dto.PageCommonDto;
import com.shuiyou.myspringboot.entity.User;
import com.shuiyou.myspringboot.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class MyController {
    @Resource
    private UserService userService;
    @Value("${mall.config.name}")
    private String name;
    @Value("${mall.config.age}")
    private String age;

    @RequestMapping(value = "/out",method = RequestMethod.GET)
    @ResponseBody
    public String out(){
        return "success" + name + age;
    }

    @RequestMapping(value = "/showUser",method = RequestMethod.GET)
    @ResponseBody
    public User toIndex(HttpServletRequest request){
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = this.userService.getUserById(userId);
        return user;
    }

    @RequestMapping(value = "/params/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User toIndex(@PathVariable String id){
        int userId = Integer.parseInt(id);
        User user = this.userService.getUserById(userId);
        return user;
    }

    @ApiOperation(value = "get分页获取用户列表")
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<User>> getUserList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        List<User> userList = userService.selectAllUser(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(userList));
    }

    @ApiOperation(value = "post分页获取用户列表")
    @RequestMapping(value = "/user/list", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CommonResult<CommonPage<User>> getUserList(@RequestBody PageCommonDto pageCommonDto) {
        List<User> userList = userService.selectAllUser(pageCommonDto.getPageNum(), pageCommonDto.getPageSize());
        return CommonResult.success(CommonPage.restPage(userList));
    }

}

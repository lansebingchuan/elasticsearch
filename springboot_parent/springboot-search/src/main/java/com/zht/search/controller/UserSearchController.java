package com.zht.search.controller;

import com.zht.search.api.pojo.UserInfo;
import com.zht.search.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class UserSearchController {

    @Autowired
    private UserService userService;

    /**
     * 导入所有的数据
     * @return 导入成功
     */
    @RequestMapping("/importData")
    public String importData(){
        userService.importData();
        return "ok";
    }

    /**
     * 获取所有的数据
     * @return 所有的用户数据
     */
    @RequestMapping("/listUser")
    public String listUser(){
        List<UserInfo> userInfoList = userService.listUserInfo();
        StringBuffer userStr = new StringBuffer();
        userInfoList.forEach(userInfo -> {
            userStr.append(userInfo.toString());
            userStr.append("\n");
        });
        return userStr.toString();
    }

    /**
     *  通过id获取人员信息
     *
     * @param id 人员id
     * @return 人员信息
     */
    @GetMapping("/getUserInfoById/{id}")
    public String getUserInfoById(@PathVariable long id){
        UserInfo userInfo = userService.getUserInfoById(id);
        return userInfo == null ? "暂无用户数据" : userInfo.toString();
    }


    /**
     * 条件搜索人员信息
     *
     * @param conditions 条件信息
     * @return 人员分页信息
     */
    @GetMapping("/searchUserPage")
    public Map<String, Object> searchUserPage(@RequestBody(required = false) Map<String, String> conditions){
        return userService.searchUserMap(conditions);
    }
}

package com.zht.search.service;

import com.zht.search.api.pojo.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 导入所有的数据
     */
    void importData();

    /**
     * 获取所有的人员信息
     * @return 所有人员信息
     */
    List<UserInfo> listUserInfo();

    /**
     * 通过id获取用户
     * @param id 用户id
     * @return 用户详细信息
     */
    UserInfo getUserInfoById(long id);

    /**
     * 多条件查询
     *
     * @param conditions 条件参数
     * @return 用户数据map
     */
    Map<String, Object> searchUserMap(Map<String, String> conditions);
}

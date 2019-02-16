package com.cc.system.user.service;

import com.cc.common.web.Tree;
import com.cc.system.auth.bean.AuthBean;

import java.util.List;
import java.util.Map;

/**
 * Created by yuanwenshu on 2018/9/7.
 */
public interface UserAuthService {

    /**
     * 获取用户权限树
     * @param userId
     * @return
     */
    Tree<Map<String, Object>> queryUserAuthTree(Long userId);

    /**
     * 获取用户权限集合
     * @param userId
     * @return
     */
    List<AuthBean> queryUserAuthList(Long userId);
}

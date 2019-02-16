package com.cc.system.user.service.impl;

import com.cc.common.Constant;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Tree;
import com.cc.system.auth.bean.AuthBean;
import com.cc.system.auth.service.AuthService;
import com.cc.system.role.bean.RoleAuthBean;
import com.cc.system.role.service.RoleAuthService;
import com.cc.system.user.bean.UserRoleBean;
import com.cc.system.user.service.UserAuthService;
import com.cc.system.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yuanwenshu on 2018/9/7.
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleAuthService roleAuthService;

    @Autowired
    private AuthService authService;

    @Override
    public Tree<Map<String, Object>> queryUserAuthTree(Long userId) {
        Tree<Map<String, Object>> tree = new Tree<Map<String,Object>>();
        List<AuthBean> authBeanList = queryUserAuthList(userId);
        if (ListTools.isEmptyOrNull(authBeanList)) {
            tree.setMessage("没有权限数据");
            return tree;
        }
        List<AuthBean> topNodeList = authBeanList.stream().filter(authBean->(authBean.getParentId()==null)).collect(Collectors.toList());
        if (ListTools.isEmptyOrNull(topNodeList)) {
            tree.setMessage("没有找到顶级权限数据");
            return tree;
        }
        List<Map<String, Object>> topAuthMapList = new ArrayList<Map<String,Object>>();
        for (AuthBean authBean : topNodeList) {
            topAuthMapList.add(addSubAuthBean(authBean, authBeanList));
        }
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", -1);
        root.put("authCode", "-1");
        root.put("authName", "权限树");
        root.put("subAuthList", topAuthMapList);
        tree.setRoot(root);
        int level = -1;
        for (AuthBean authBean : authBeanList) {
            if (level<authBean.getLevel()) {
                level = authBean.getLevel();
            }
        }
        tree.setLevel(level);
        tree.setSuccess(Boolean.TRUE);
        return tree;
    }

    /**
     * 添加子权限
     * @param authBean
     * @param authBeanList
     * @return
     */
    private Map<String, Object> addSubAuthBean(AuthBean authBean, List<AuthBean> authBeanList){
        Map<String, Object> authMap = new HashMap<String, Object>();
        authMap.put("id", authBean.getId());
        authMap.put("authCode", authBean.getAuthCode());
        authMap.put("authName", authBean.getAuthName());
        authMap.put("level", authBean.getLevel());
        if(!ListTools.isEmptyOrNull(authBeanList)){
            List<AuthBean> subAuthBeanList = authBeanList.stream().filter(subAuthBean->authBean.getId().equals(subAuthBean.getParentId())).collect(Collectors.toList());
            if (!ListTools.isEmptyOrNull(subAuthBeanList)) {
                List<Map<String, Object>> subAuthMapList = new ArrayList<Map<String,Object>>();
                for (AuthBean subAuthBean : subAuthBeanList) {
                    subAuthMapList.add(addSubAuthBean(subAuthBean, authBeanList));
                }
                authMap.put("subAuthList", subAuthMapList);
            }
        }
        return authMap;
    }

    @Override
    public List<AuthBean> queryUserAuthList(Long userId){
        List<AuthBean> authBeanList = new ArrayList<AuthBean>();
        if(Constant.SUPERUSER.equals(userId)){
            authBeanList.addAll(AuthBean.findAllByParams(AuthBean.class));
        }else{
            List<UserRoleBean> userRoleBeanList = userRoleService.queryUserRoleBeanList(userId);
            if (!ListTools.isEmptyOrNull(userRoleBeanList)) {
                List<Long> roleList = userRoleBeanList.stream().map(userRoleBean->userRoleBean.getRoleId()).collect(Collectors.toList());
                List<RoleAuthBean> roleAuthBeanList = roleAuthService.queryRoleAuthBeanList(roleList);
                if (!ListTools.isEmptyOrNull(roleAuthBeanList)) {
                    List<Long> authList = roleAuthBeanList.stream().map(roleAuthBean->roleAuthBean.getAuthId()).collect(Collectors.toList());
                    authBeanList.addAll(authService.queryAuthBeanList(authList));
                }
            }
        }
        return authBeanList;
    }
}

package com.cc.seller.service.impl;

import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.seller.form.SellerQueryForm;
import com.cc.seller.service.SellerService;
import com.cc.system.user.bean.UserBean;
import com.cc.system.user.enums.UserTypeEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by yuanwenshu on 2018/10/12.
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Override
    public Page<UserBean> querySellerPage(SellerQueryForm form) {
        Page<UserBean> page  = new Page<UserBean>();
        Example example = new Example(UserBean.class);
        Example.Criteria nameCriteria = example.createCriteria();
        if(!StringTools.isNullOrNone(form.getName())){
            nameCriteria.orLike("nickName", "%"+form.getName()+"%");
            nameCriteria.orLike("realName", "%"+form.getName()+"%");
        }
        Example.Criteria criteria = example.createCriteria();
        if(!StringTools.isNullOrNone(form.getPhone())){
            criteria.andEqualTo("phone", form.getPhone());
        }
        if(!StringTools.isNullOrNone(form.getStatus())){
            criteria.andEqualTo("status", form.getStatus());
        }
        criteria.andEqualTo("userType", UserTypeEnum.SELLER.getCode());
        example.and(criteria);
        PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
        PageHelper.startPage(form.getPage(), form.getPageSize());
        List<UserBean> sellerBeanList = UserBean.findByExample(UserBean.class, example);
        PageInfo<UserBean> pageInfo = new PageInfo<UserBean>(sellerBeanList);
        if (ListTools.isEmptyOrNull(sellerBeanList)) {
            page.setMessage("没有查询到相关卖家数据");
            return page;
        }
        page.setPage(pageInfo.getPageNum());
        page.setPages(pageInfo.getPages());
        page.setPageSize(pageInfo.getPageSize());
        page.setTotal(pageInfo.getTotal());
        page.setData(sellerBeanList);
        page.setSuccess(Boolean.TRUE);
        return page;
    }
}

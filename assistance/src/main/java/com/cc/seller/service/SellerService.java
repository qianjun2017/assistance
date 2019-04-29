package com.cc.seller.service;

import com.cc.common.web.Page;
import com.cc.seller.form.SellerQueryForm;
import com.cc.system.user.bean.UserBean;

/**
 * Created by yuanwenshu on 2018/10/12.
 */
public interface SellerService {

    /**
     * 分页查询卖家信息
     * @param form
     * @return
     */
    Page<UserBean> querySellerPage(SellerQueryForm form);

}

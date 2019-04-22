package com.cc.seller.service;

import com.cc.common.web.Page;
import com.cc.seller.bean.SellerBean;
import com.cc.seller.form.SellerQueryForm;

/**
 * Created by yuanwenshu on 2018/10/12.
 */
public interface SellerService {

    /**
     * 保存卖家信息
     * @param sellerBean
     */
    void saveSeller(SellerBean sellerBean);

    /**
     * 锁定卖家
     * @param id
     */
    void lockSeller(Long id);

    /**
     * 解锁卖家
     * @param id
     */
    void unLockSeller(Long id);

    /**
     * 分页查询卖家信息
     * @param form
     * @return
     */
    Page<SellerBean> querySellerPage(SellerQueryForm form);
    
    /**
	 * 更新用户部分信息
	 * @param sellerBean
	 */
	void updateSeller(SellerBean sellerBean);

}

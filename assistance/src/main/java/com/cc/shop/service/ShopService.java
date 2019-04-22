package com.cc.shop.service;

import com.cc.common.web.Page;
import com.cc.shop.bean.ShopBean;
import com.cc.shop.enums.ShopStatusEnum;
import com.cc.shop.form.ShopQueryForm;
import com.cc.shop.result.ShopResult;

/**
 * Created by yuanwenshu on 2018/10/12.
 */
public interface ShopService {

    /**
     * 保存店铺
     * @param shopBean
     */
    void saveShop(ShopBean shopBean);

    /**
     * 关闭店铺
     * @param id
     */
    void closeShop(Long id);

    /**
     * 审核店铺
     * @param id
     * @param shopStatusEnum
     * @Param remark
     */
    void auditShop(Long id, ShopStatusEnum shopStatusEnum, String remark);

    /**
     * 分页查询店铺
     * @param form
     * @return
     */
    Page<ShopResult> queryShopPage(ShopQueryForm form);
}

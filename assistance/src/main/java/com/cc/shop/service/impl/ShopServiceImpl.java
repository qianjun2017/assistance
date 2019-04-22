package com.cc.shop.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.seller.bean.SellerBean;
import com.cc.shop.bean.ShopBean;
import com.cc.shop.enums.ShopStatusEnum;
import com.cc.shop.form.ShopQueryForm;
import com.cc.shop.result.ShopResult;
import com.cc.shop.service.ShopService;
import com.cc.system.message.service.MessageService;
import com.cc.system.shiro.SecurityContextUtil;
import com.cc.user.bean.TUserBean;
import com.cc.user.enums.UserTypeEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * Created by yuanwenshu on 2018/10/12.
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private MessageService messageService;

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void saveShop(ShopBean shopBean) {
        int row = shopBean.save();
        if(row!=1){
            throw new LogicException("E001", "保存店铺失败");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void closeShop(Long id) {
        ShopBean shopBean = new ShopBean();
        shopBean.setId(id);
        shopBean.setStatus(ShopStatusEnum.CLOSED.getCode());
        int row = shopBean.update();
        if(row!=1){
            throw new LogicException("E001", "关闭店铺失败");
        }
        TUserBean currentUser = SecurityContextUtil.getCurrentUser();
        UserTypeEnum userTypeEnum = UserTypeEnum.getUserTypeEnumByCode(currentUser.getUserType());
        if(!UserTypeEnum.SELLER.equals(userTypeEnum)){
            ShopBean shop = ShopBean.get(ShopBean.class, id);
            SellerBean seller = SellerBean.get(SellerBean.class, shop.getSellerId());
            messageService.releaseSystemMessage(seller.getUid(),"尊敬的卖家【"+seller.getSellerName()+"】，您的店铺【"+shop.getName()+"】已被我方关闭，如有疑问请联系我们！");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void auditShop(Long id, ShopStatusEnum shopStatusEnum, String remark) {
        ShopBean shopBean = ShopBean.get(ShopBean.class, id);
        if(shopBean==null){
            throw new LogicException("E001", "店铺不存在");
        }
        ShopStatusEnum shopStatusEnumByCode = ShopStatusEnum.getShopStatusEnumByCode(shopBean.getStatus());
        if(!ShopStatusEnum.PENDING.equals(shopStatusEnumByCode)){
            throw new LogicException("E002", "店铺状态错误，当前状态为"+shopStatusEnumByCode.getName()+",不能进行审核操作");
        }
        ShopBean shop = new ShopBean();
        shop.setId(id);
        shop.setStatus(shopStatusEnum.getCode());
        int row = shop.update();
        if(row!=1){
            throw new LogicException("E001", "审核店铺失败");
        }
        SellerBean seller = SellerBean.get(SellerBean.class, shopBean.getSellerId());
        messageService.releaseSystemMessage(seller.getUid(), "您的店铺【"+shopBean.getName()+"】已审核，审核结果【"+shopStatusEnum.getName()+"】"+(ShopStatusEnum.FAILURE.equals(shopStatusEnum)?("，审核说明【"+remark+"】"):""), "/shop/"+id);
    }

    @Override
    public Page<ShopResult> queryShopPage(ShopQueryForm form) {
        Page<ShopResult> page  = new Page<ShopResult>();
        Example example = new Example(ShopBean.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringTools.isNullOrNone(form.getName())){
            criteria.andLike("name", "%"+form.getName()+"%");
        }
        if(!StringTools.isNullOrNone(form.getStatus())){
            criteria.andEqualTo("status", form.getStatus());
        }
        if(!StringTools.isNullOrNone(form.getType())){
            criteria.andEqualTo("type", form.getType());
        }
        if(form.getSellerId()!=null){
            criteria.andEqualTo("sellerId", form.getSellerId());
        }
        PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
        PageHelper.startPage(form.getPage(), form.getPageSize());
        List<ShopBean> shopBeanList = ShopBean.findByExample(ShopBean.class, example);
        PageInfo<ShopBean> pageInfo = new PageInfo<ShopBean>(shopBeanList);
        if (ListTools.isEmptyOrNull(shopBeanList)) {
            page.setMessage("没有查询到相关店铺数据");
            return page;
        }
        page.setPage(pageInfo.getPageNum());
        page.setPages(pageInfo.getPages());
        page.setPageSize(pageInfo.getPageSize());
        page.setTotal(pageInfo.getTotal());
        List<ShopResult> shopResultList = new ArrayList<ShopResult>();
        Map<Long, SellerBean> sellerBeanMap = new HashMap<Long, SellerBean>();
        for(ShopBean shopBean: shopBeanList){
        	ShopResult shopResult = JsonTools.toObject(JsonTools.toJsonString(shopBean), ShopResult.class);
        	SellerBean sellerBean = sellerBeanMap.get(shopBean.getSellerId());
        	if(sellerBean==null){
        		sellerBean = SellerBean.get(SellerBean.class, shopBean.getSellerId());
        		sellerBeanMap.put(shopBean.getSellerId(), sellerBean);
        	}
        	if(sellerBean!=null){
        		shopResult.setSellerName(sellerBean.getSellerName());
        	}
        	shopResultList.add(shopResult);
        }
        page.setData(shopResultList);
        page.setSuccess(Boolean.TRUE);
        return page;
    }
}

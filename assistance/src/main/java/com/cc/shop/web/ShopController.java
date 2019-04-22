package com.cc.shop.web;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.seller.bean.SellerBean;
import com.cc.shop.bean.ShopBean;
import com.cc.shop.enums.ShopStatusEnum;
import com.cc.shop.enums.ShopTypeEnum;
import com.cc.shop.form.ShopQueryForm;
import com.cc.shop.result.ShopResult;
import com.cc.shop.service.ShopService;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.shiro.SecurityContextUtil;
import com.cc.user.bean.TUserBean;
import com.cc.user.enums.UserTypeEnum;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by yuanwenshu on 2018/10/12.
 */
@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;
    
    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 新增店铺
     * @param shopMap
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "shop.add" })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.SHOPMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增店铺")
    public Response<String> addShop(@RequestBody Map<String, String> shopMap){
        Response<String> response = new Response<String>();
        TUserBean currentUser = SecurityContextUtil.getCurrentUser();
        UserTypeEnum userTypeEnum = UserTypeEnum.getUserTypeEnumByCode(currentUser.getUserType());
        if(!UserTypeEnum.SELLER.equals(userTypeEnum)){
            response.setMessage("非卖家用户不能创建店铺");
            return response;
        }
        List<SellerBean> sellerBeanList = SellerBean.findAllByParams(SellerBean.class, "uid", currentUser.getId());
        if(ListTools.isEmptyOrNull(sellerBeanList)){
            response.setMessage("卖家不存在");
            return response;
        }
        SellerBean sellerBean = sellerBeanList.get(0);
        SystemConfigBean systemConfigBean = systemConfigService.querySystemConfigBean("seller.shop.limit");
		if (systemConfigBean!=null) {
			try{
				long limit = Long.parseLong(systemConfigBean.getPropertyValue());
				List<ShopBean> shopBeanList = ShopBean.findAllByParams(ShopBean.class, "sellerId", sellerBean.getId());
				if(!ListTools.isEmptyOrNull(shopBeanList) && shopBeanList.stream().filter(shop->!ShopStatusEnum.CLOSED.equals(ShopStatusEnum.getShopStatusEnumByCode(shop.getStatus()))).count()>=limit){
					response.setMessage("您开设的店铺已达到最大限度");
		            return response;
				}
			}catch (Exception e) {
				response.setMessage("系统内部错误");
	            e.printStackTrace();
	            return response;
			}
		}
        ShopBean shopBean = new ShopBean();
        String name = shopMap.get("name");
        if (StringTools.isNullOrNone(name)) {
            response.setMessage("请输入店铺名称");
            return response;
        }
        shopBean.setName(name);
        String type = shopMap.get("type");
        if (StringTools.isNullOrNone(type)) {
            response.setMessage("请选择店铺类型");
            return response;
        }
        ShopTypeEnum shopTypeEnum = ShopTypeEnum.getShopTypeEnumByCode(type);
        if(shopTypeEnum==null){
            response.setMessage("店铺类型不存在，请重新选择");
            return response;
        }
        shopBean.setType(shopTypeEnum.getCode());
        String link = shopMap.get("link");
        if (ShopTypeEnum.TAOBAO.equals(shopTypeEnum)) {
            if(StringTools.isNullOrNone(link)) {
                response.setMessage("请输入店铺链接");
                return response;
            }
            shopBean.setLink(link);
            shopBean.setStatus(ShopStatusEnum.PENDING.getCode());
        }
        shopBean.setImageUrl(shopMap.get("imageUrl"));
        shopBean.setSellerId(sellerBean.getId());
        shopBean.setCreateTime(DateTools.now());
        try{
            shopService.saveShop(shopBean);
            response.setSuccess(Boolean.TRUE);
        } catch (LogicException e) {
            response.setMessage(e.getErrContent());
        } catch (Exception e) {
            response.setMessage("系统内部错误");
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 修改店铺
     * @param shopMap
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "shop.update" })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.SHOPMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改店铺")
    public Response<String> updateShop(@RequestBody Map<String, String> shopMap){
        Response<String> response = new Response<String>();
        String id = shopMap.get("id");
        if(StringTools.isNullOrNone(id)){
            response.setMessage("缺少店铺主键");
            return response;
        }
        if(ShopBean.get(ShopBean.class, Long.valueOf(id))==null){
            response.setMessage("店铺不存在");
            return response;
        }
        String name = shopMap.get("name");
        if (StringTools.isNullOrNone(name)) {
            response.setMessage("请输入店铺名称");
            return response;
        }
        try{
            ShopBean shopBean = new ShopBean();
            shopBean.setId(Long.valueOf(id));
            shopBean.setName(name);
            shopBean.setImageUrl(shopMap.get("imageUrl"));
            shopBean.update();
            response.setSuccess(Boolean.TRUE);
        } catch (LogicException e) {
            response.setMessage(e.getErrContent());
        } catch (Exception e) {
            response.setMessage("系统内部错误");
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 关闭店铺
     * @param id
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "shop.close" })
    @RequestMapping(value = "/close/{id:\\d+}", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.SHOPMANAGEMENT, operType = OperTypeEnum.CLOSE, title = "关闭店铺", paramNames = {"id"})
    public Response<String> lockSeller(@PathVariable Long id){
        Response<String> response = new Response<String>();
        ShopBean shopBean = ShopBean.get(ShopBean.class, id);
        if (shopBean == null) {
            response.setMessage("店铺不存在");
            return response;
        }
        try {
            shopService.closeShop(id);
            response.setSuccess(Boolean.TRUE);
        } catch (LogicException e) {
            response.setMessage(e.getErrContent());
        } catch (Exception e) {
            response.setMessage("系统内部错误");
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 审核店铺
     * @param auditMap
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "shop.audit" })
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.SHOPMANAGEMENT, operType = OperTypeEnum.AUDIT, title = "审核店铺")
    public Response<String> auditShop(@RequestBody Map<String, String> auditMap){
        Response<String> response = new Response<String>();
        String id = auditMap.get("id");
        if(StringTools.isNullOrNone(id)){
            response.setMessage("缺少店铺主键");
            return response;
        }
        String status = auditMap.get("status");
        if(StringTools.isNullOrNone(status)){
            response.setMessage("请选择审核状态");
            return response;
        }
        ShopStatusEnum shopStatusEnum = ShopStatusEnum.getShopStatusEnumByCode(status);
        if(shopStatusEnum==null){
            response.setMessage("审核状态不存在，请重新选择");
            return response;
        }
        if(ShopStatusEnum.FAILURE.equals(shopStatusEnum) && StringTools.isNullOrNone(auditMap.get("remark"))){
            response.setMessage("请输入审批说明");
            return response;
        }
        try {
            shopService.auditShop(Long.valueOf(id), shopStatusEnum, auditMap.get("remark"));
            response.setSuccess(Boolean.TRUE);
        } catch (LogicException e) {
            response.setMessage(e.getErrContent());
        } catch (Exception e) {
            response.setMessage("系统内部错误");
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 查询店铺信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
    public Response<ShopBean> queryShop(@PathVariable Long id){
        Response<ShopBean> response = new Response<ShopBean>();
        try {
            ShopBean shopBean = ShopBean.get(ShopBean.class, id);
            if (shopBean == null) {
                response.setMessage("店铺不存在");
                return response;
            }
            response.setData(shopBean);
            response.setSuccess(Boolean.TRUE);
        } catch (Exception e) {
            response.setMessage("系统内部错误");
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 分页查询店铺
     * @param form
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<ShopResult> querySellerPage(@ModelAttribute ShopQueryForm form){
        TUserBean currentUser = SecurityContextUtil.getCurrentUser();
        UserTypeEnum userTypeEnum = UserTypeEnum.getUserTypeEnumByCode(currentUser.getUserType());
        if(UserTypeEnum.SELLER.equals(userTypeEnum)){
            List<SellerBean> sellerBeanList = SellerBean.findAllByParams(SellerBean.class, "uid", currentUser.getId());
            if(ListTools.isEmptyOrNull(sellerBeanList)){
                Page<ShopResult> page = new Page<ShopResult>();
                page.setMessage("没有查询到相关店铺数据");
                return page;
            }
            form.setSellerId(sellerBeanList.get(0).getId());
        }
        return shopService.queryShopPage(form);
    }
}

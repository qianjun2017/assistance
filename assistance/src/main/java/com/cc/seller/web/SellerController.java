package com.cc.seller.web;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DESTools;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.seller.bean.SellerBean;
import com.cc.seller.enums.SellerStatusEnum;
import com.cc.seller.form.SellerQueryForm;
import com.cc.seller.service.SellerService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.log.utils.LogContextUtil;
import com.cc.system.shiro.SecurityContextUtil;
import com.cc.system.user.utils.PasswordUtil;
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
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    /**
     * 卖家注册
     * @param sellerMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.SELLERMANAGEMENT, operType = OperTypeEnum.ADD, title = "注册卖家")
    public Response<String> addSeller(@RequestBody Map<String, String> sellerMap){
        Response<String> response = new Response<String>();
        SellerBean sellerBean = new SellerBean();
        String token = sellerMap.get("token");
        if (StringTools.isAnyNullOrNone(new String[]{token, SecurityContextUtil.getOpenid()})) {
            response.setMessage("请先进行微信认证");
            return response;
        }
        String openId = DESTools.decrypt(token, SecurityContextUtil.getDESKey());
        if(!openId.equals(SecurityContextUtil.getOpenid())){
        	response.setMessage("微信认证错误");
            return response;
        }
        List<SellerBean> sellerBeanOpenidList = SellerBean.findAllByParams(SellerBean.class, "openId", openId);
        if (!ListTools.isEmptyOrNull(sellerBeanOpenidList)) {
            response.setMessage("微信已注册，请换一个微信");
            return response;
        }
        sellerBean.setOpenId(openId);
        String sellerName = sellerMap.get("sellerName");
        if (StringTools.isNullOrNone(sellerName)) {
            response.setMessage("请输入卖家名称");
            return response;
        }
        List<SellerBean> sellerBeanList = SellerBean.findAllByParams(SellerBean.class, "sellerName", sellerName);
        if (!ListTools.isEmptyOrNull(sellerBeanList)) {
            response.setMessage("卖家名称已存在，请重新输入");
            return response;
        }
        sellerBean.setSellerName(sellerName);
        String realName = sellerMap.get("realName");
        if (StringTools.isNullOrNone(realName)) {
            response.setMessage("请输入真实姓名");
            return response;
        }
        sellerBean.setRealName(realName);
        String password = sellerMap.get("password");
        if (StringTools.isNullOrNone(password)) {
            response.setMessage("请设置登录密码");
            return response;
        }
        String phone = sellerMap.get("phone");
        if (StringTools.isNullOrNone(phone)) {
            response.setMessage("请输入手机号码");
            return response;
        }
        if(!StringTools.matches(phone, "^1[34578]\\d{9}$")){
        	response.setMessage("请输入11位有效手机号码");
            return response;
        }
        sellerBean.setImageUrl(sellerMap.get("imageUrl"));
        sellerBean.setPhone(phone);
        sellerBean.setCreateTime(DateTools.now());
        sellerBean.setStatus(SellerStatusEnum.NORMAL.getCode());
        try{
            TUserBean user = new TUserBean();
            user.setOpenid(sellerBean.getOpenId());
            user.setUserName(sellerBean.getSellerName());
            user.setSalt(PasswordUtil.getSalt(5));
            user.setPassword(PasswordUtil.encrypt(password, user.getSalt()));
            user.setUserType(UserTypeEnum.SELLER.getCode());
            user.save();
            sellerBean.setUid(user.getId());
            sellerService.saveSeller(sellerBean);
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
     * 锁定卖家
     * @param id
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "seller.lock" })
    @RequestMapping(value = "/lock/{id:\\d+}", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.SELLERMANAGEMENT, operType = OperTypeEnum.LOCK, title = "锁定卖家", paramNames = {"id"})
    public Response<String> lockSeller(@PathVariable Long id){
        Response<String> response = new Response<String>();
        SellerBean sellerBean = SellerBean.get(SellerBean.class, id);
        if (sellerBean == null) {
            response.setMessage("卖家不存在");
            return response;
        }
        try {
            sellerService.lockSeller(id);
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
     * 解锁卖家
     * @param id
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "seller.unlock" })
    @RequestMapping(value = "/unlock/{id:\\d+}", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.SELLERMANAGEMENT, operType = OperTypeEnum.UNLOCK, title = "解锁卖家", paramNames = {"id"})
    public Response<String> unlockSeller(@PathVariable Long id){
        Response<String> response = new Response<String>();
        SellerBean sellerBean = SellerBean.get(SellerBean.class, id);
        if (sellerBean == null) {
            response.setMessage("卖家不存在");
            return response;
        }
        try {
            sellerService.unLockSeller(id);
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
     * 查询卖家信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
    public Response<SellerBean> querySeller(@PathVariable Long id){
        Response<SellerBean> response = new Response<SellerBean>();
        try {
            SellerBean sellerBean = SellerBean.get(SellerBean.class, id);
            if (sellerBean == null) {
                response.setMessage("卖家不存在");
                return response;
            }
            response.setData(sellerBean);
            response.setSuccess(Boolean.TRUE);
        } catch (Exception e) {
            response.setMessage("系统内部错误");
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 分页查询卖家
     * @param form
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<SellerBean> querySellerPage(@ModelAttribute SellerQueryForm form){
        return sellerService.querySellerPage(form);
    }
    
    /**
	 * 卖家个人设置
	 * @param sellerMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "seller.setting" })
	@RequestMapping(value = "/setting/save", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.SELLERMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "个人设置")
	public Response<String> updateSellerSetting(@RequestBody Map<String, String> sellerMap){
		Response<String> response = new Response<String>();
		String id = sellerMap.get("id");
		if(StringTools.isNullOrNone(id)){
			response.setMessage("缺少卖家主键");
			return response;
		}
		SellerBean sellerBean = SellerBean.get(SellerBean.class, Long.valueOf(id));
		if (sellerBean==null) {
			response.setMessage("卖家不存在");
			return response;
		}
		SellerBean updateSellerBean = new SellerBean();
		updateSellerBean.setId(sellerBean.getId());
		String nickName = sellerMap.get("nickName");
		if(!StringTools.isNullOrNone(nickName)){
			LogContextUtil.setOperContent("修改卖家名称:从【"+sellerBean.getSellerName()+"】变更为【"+nickName+"】");
			List<SellerBean> sellerBeanList = SellerBean.findAllByParams(SellerBean.class, "sellerName", nickName);
	        if (!ListTools.isEmptyOrNull(sellerBeanList) && (sellerBeanList.stream().filter(seller->!seller.getId().equals(sellerBean.getId())).count()>0)) {
	            response.setMessage("卖家名称已存在，请重新输入");
	            return response;
	        }
		}
		updateSellerBean.setSellerName(nickName);
		String realName = sellerMap.get("realName");
		if(!StringTools.isNullOrNone(realName)){
			LogContextUtil.setOperContent("卖家实名:【"+realName+"】");
		}
		updateSellerBean.setRealName(realName);
		String phone = sellerMap.get("phone");
		if(!StringTools.isNullOrNone(phone)){
			if(!StringTools.matches(phone, "^1[34578]\\d{9}$")){
				response.setMessage("请输入11位有效手机号码");
	            return response;
	        }
			LogContextUtil.setOperContent(StringTools.isNullOrNone(sellerBean.getPhone())?"绑定卖家手机号:【"+phone+"】":"修改卖家手机号:从【"+sellerBean.getPhone()+"】变更为【"+phone+"】");
		}
		updateSellerBean.setPhone(phone);
		String imageUrl = sellerMap.get("imageUrl");
		if(!StringTools.isNullOrNone(imageUrl)){
			LogContextUtil.setOperContent(StringTools.isNullOrNone(sellerBean.getImageUrl())?"上传卖家头像:【"+imageUrl+"】":"修改卖家头像:从【"+sellerBean.getImageUrl()+"】变更为【"+imageUrl+"】");
		}
		updateSellerBean.setImageUrl(imageUrl);
		try {
			String password = sellerMap.get("password");
			if(!StringTools.isAllNullOrNone(new String[]{updateSellerBean.getSellerName(), password})) {
				com.cc.user.bean.TUserBean user = com.cc.user.bean.TUserBean.get(com.cc.user.bean.TUserBean.class, sellerBean.getUid());
				if (!StringTools.isNullOrNone(updateSellerBean.getSellerName())) {
					user.setUserName(updateSellerBean.getSellerName());
				}
				if (!StringTools.isNullOrNone(password)) {
					LogContextUtil.setOperContent("修改卖家密码");
					user.setSalt(PasswordUtil.getSalt(5));
					user.setPassword(PasswordUtil.encrypt(password, user.getSalt()));
				}
				user.update();
			}
			sellerService.updateSeller(updateSellerBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
}

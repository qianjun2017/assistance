package com.cc.seller.web;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DESTools;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.seller.form.SellerQueryForm;
import com.cc.seller.service.SellerService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.log.utils.LogContextUtil;
import com.cc.system.shiro.SecurityContextUtil;
import com.cc.system.user.bean.UserBean;
import com.cc.system.user.enums.UserStatusEnum;
import com.cc.system.user.enums.UserTypeEnum;
import com.cc.system.user.service.UserService;
import com.cc.system.user.utils.PasswordUtil;

/**
 * Created by yuanwenshu on 2018/10/12.
 */
@Controller
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private UserService userService;
    
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
        UserBean sellerBean = new UserBean();
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
        List<UserBean> sellerBeanOpenidList = UserBean.findAllByParams(UserBean.class, "openId", openId, "userType", UserTypeEnum.SELLER.getCode());
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
        List<UserBean> sellerBeanList = UserBean.findAllByParams(UserBean.class, "nickName", sellerName, "userType", UserTypeEnum.SELLER.getCode());
        if (!ListTools.isEmptyOrNull(sellerBeanList)) {
            response.setMessage("卖家名称已存在，请重新输入");
            return response;
        }
        sellerBean.setNickName(sellerName);
        sellerBean.setUserName(sellerName);
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
        if(!StringTools.isPhone(phone)){
        	response.setMessage("请输入11位有效手机号码");
            return response;
        }
        sellerBean.setImageUrl(sellerMap.get("imageUrl"));
        sellerBean.setPhone(phone);
        sellerBean.setCreateTime(DateTools.now());
        sellerBean.setStatus(UserStatusEnum.NORMAL.getCode());
        sellerBean.setUserType(UserTypeEnum.SELLER.getCode());
        try{
        	userService.saveUser(sellerBean);
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
        UserBean sellerBean = UserBean.get(UserBean.class, id);
        if (sellerBean == null) {
            response.setMessage("卖家不存在或已删除");
            return response;
        }
        try {
        	userService.lockUser(id);
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
        UserBean sellerBean = UserBean.get(UserBean.class, id);
        if (sellerBean == null) {
            response.setMessage("卖家不存在或已删除");
            return response;
        }
        try {
        	userService.unLockUser(id);
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
    public Response<UserBean> querySeller(@PathVariable Long id){
        Response<UserBean> response = new Response<UserBean>();
        try {
        	UserBean sellerBean = UserBean.get(UserBean.class, id);
            if (sellerBean == null) {
                response.setMessage("卖家不存在或已删除");
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
    public Page<UserBean> querySellerPage(@ModelAttribute SellerQueryForm form){
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
		UserBean sellerBean = UserBean.get(UserBean.class, Long.valueOf(id));
		if (sellerBean==null) {
			response.setMessage("卖家不存在或已删除");
			return response;
		}
		UserBean updateSellerBean = new UserBean();
		updateSellerBean.setId(sellerBean.getId());
		String nickName = sellerMap.get("nickName");
		if(!StringTools.isNullOrNone(nickName)){
			LogContextUtil.setOperContent("修改卖家名称:从【"+sellerBean.getNickName()+"】变更为【"+nickName+"】");
			List<UserBean> sellerBeanList = UserBean.findAllByParams(UserBean.class, "nickName", nickName, "userType", UserTypeEnum.SELLER.getCode());
	        if (!ListTools.isEmptyOrNull(sellerBeanList) && (sellerBeanList.stream().filter(seller->!seller.getId().equals(sellerBean.getId())).count()>0)) {
	            response.setMessage("卖家名称已存在，请重新输入");
	            return response;
	        }
		}
		updateSellerBean.setNickName(nickName);
		updateSellerBean.setUserName(nickName);
		String realName = sellerMap.get("realName");
		if(!StringTools.isNullOrNone(realName)){
			LogContextUtil.setOperContent("卖家实名:【"+realName+"】");
		}
		updateSellerBean.setRealName(realName);
		String phone = sellerMap.get("phone");
		if(!StringTools.isNullOrNone(phone)){
			if(!StringTools.isPhone(phone)){
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
		String password = sellerMap.get("password");
		if(!StringTools.isNullOrNone(password)){
			updateSellerBean.setSalt(PasswordUtil.getSalt(5));
			updateSellerBean.setPassword(PasswordUtil.encrypt(password, updateSellerBean.getSalt()));
		}
		try {
			userService.updateUser(updateSellerBean);
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

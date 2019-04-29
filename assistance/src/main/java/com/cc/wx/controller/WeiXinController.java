package com.cc.wx.controller;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.AESTools;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.JwtTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.utils.UUIDUtils;
import com.cc.common.web.Response;
import com.cc.customer.enums.CustomerStatusEnum;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.leaguer.enums.LeaguerStatusEnum;
import com.cc.leaguer.service.LeaguerService;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;
import com.cc.wx.form.AuthorizeForm;
import com.cc.wx.form.CodeForm;
import com.cc.wx.form.WXACodeForm;
import com.cc.wx.http.request.MiniOpenidRequest;
import com.cc.wx.http.request.WXACodeRequest;
import com.cc.wx.http.request.model.Color;
import com.cc.wx.http.request.model.Phone;
import com.cc.wx.http.response.MiniOpenidResponse;
import com.cc.wx.http.response.WXACodeResponse;
import com.cc.wx.service.AccessTokenService;
import com.cc.wx.service.WeiXinService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yuanwenshu on 2018/9/20.
 */
@Controller
@RequestMapping("/wx")
public class WeiXinController {
	
	private static Logger logger = LoggerFactory.getLogger(WeiXinController.class);
	
    @Autowired
    private WeiXinService weiXinService;

    @Autowired
    private SystemConfigService systemConfigService;
    
    @Autowired
	private LeaguerService leaguerService;
    
    @Autowired
    private AccessTokenService accessTokenService;
    
    /**
     * 微信公众号签名校验
     * @param request
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value="/check", method = RequestMethod.GET)
    public void checkToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
        PrintWriter writer = response.getWriter();
        try{
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String signature = request.getParameter("signature");// 微信加密签名
            String timestamp = request.getParameter("timestamp");// 时间戳
            String nonce = request.getParameter("nonce");// 随机数
            String echostr = request.getParameter("echostr");//随机字符串
            SystemConfigBean tokenSystemConfigBean = systemConfigService.querySystemConfigBean("token");
            if(tokenSystemConfigBean!=null){
                if(Boolean.TRUE.equals(weiXinService.checkSignature(tokenSystemConfigBean.getPropertyValue(), timestamp, nonce, signature))){
                    writer.write(echostr);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }

    /**
     * 微信公众号用户授权
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/authorize", method = RequestMethod.GET)
    public Response<String> authorize(@ModelAttribute AuthorizeForm form){
        Response<String> response = new Response<String>();
        try {
        	if(StringTools.isNullOrNone(form.getType())){
        		response.setMessage("请输入授权用户类型");
			    return response;
        	}
			SystemConfigBean openidUrlSystemConfigBean = systemConfigService.querySystemConfigBean("wx.gzh."+form.getType()+".openid.url");
			if(openidUrlSystemConfigBean==null){
			    response.setMessage("请设置微信回调地址");
			    return response;
			}
			SystemConfigBean appidSystemConfigBean = systemConfigService.querySystemConfigBean("wx.gzh.appid");
			if(appidSystemConfigBean==null){
			    response.setMessage("请设置微信公众号appid");
			    return response;
			}
			StringBuffer buffer = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?");
			buffer.append("appid="+appidSystemConfigBean.getPropertyValue());
			buffer.append("&redirect_uri="+openidUrlSystemConfigBean.getPropertyValue());
			buffer.append("&response_type=code&scope=snsapi_userinfo");
			buffer.append("&state="+ UUIDUtils.getUuid()+"#wechat_redirect");
			response.setSuccess(Boolean.TRUE);
			response.setData(buffer.substring(0));
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("系统内部错误");
		}
        return response;
    }
    
    /**
     * 微信小程序用户登录
     * @param form
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public Response<Map<String, Object>> login(@RequestBody CodeForm form){
    	Response<Map<String, Object>> response = new Response<Map<String, Object>>();
    	MiniOpenidRequest openidRequest = new MiniOpenidRequest();
		SystemConfigBean appidSystemConfigBean = systemConfigService.querySystemConfigBean("wx.app.appid");
		if(appidSystemConfigBean!=null){
			openidRequest.setAppid(appidSystemConfigBean.getPropertyValue());
		}
		SystemConfigBean secretSystemConfigBean = systemConfigService.querySystemConfigBean("wx.app.secret");
		if(secretSystemConfigBean!=null){
			openidRequest.setSecret(secretSystemConfigBean.getPropertyValue());
		}
		openidRequest.setCode(form.getCode());
		MiniOpenidResponse openidResponse = weiXinService.queryMiniOpenid(openidRequest);
		if(!openidResponse.isSuccess()){
			response.setMessage(openidResponse.getMessage());
			return response;
		}
		List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "openid", openidResponse.getOpenid());
		if(!ListTools.isEmptyOrNull(leaguerBeanList)){
			LeaguerBean leaguerBean = leaguerBeanList.get(0);
			LeaguerStatusEnum leaguerStatusEnum = LeaguerStatusEnum.getLeaguerStatusEnumByCode(leaguerBean.getStatus());
			if(LeaguerStatusEnum.NORMAL.equals(leaguerStatusEnum)){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("token", JwtTools.createToken(leaguerBean, JwtTools.JWTTTLMILLIS));
				response.setData(dataMap);
				response.setSuccess(Boolean.TRUE);
			}else{
				response.setMessage("当前状态为"+leaguerStatusEnum.getName()+"，登录失败，请联系系统管理人员");
			}
		}
		return response;
    }
    
    /**
     * 微信小程序用户注册
     * @param form
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public Response<Map<String, Object>> register(@RequestBody Map<String, Object> registerMap){
    	Response<Map<String, Object>> response = new Response<Map<String, Object>>();
    	Object code = registerMap.get("code");
    	if(code==null){
    		response.setMessage("请先登录微信获取CODE值");
			return response;
    	}
    	MiniOpenidRequest openidRequest = new MiniOpenidRequest();
		SystemConfigBean appidSystemConfigBean = systemConfigService.querySystemConfigBean("wx.app.appid");
		if(appidSystemConfigBean!=null){
			openidRequest.setAppid(appidSystemConfigBean.getPropertyValue());
		}
		SystemConfigBean secretSystemConfigBean = systemConfigService.querySystemConfigBean("wx.app.secret");
		if(secretSystemConfigBean!=null){
			openidRequest.setSecret(secretSystemConfigBean.getPropertyValue());
		}
		openidRequest.setCode(StringTools.toString(code));
		MiniOpenidResponse openidResponse = weiXinService.queryMiniOpenid(openidRequest);
		if(!openidResponse.isSuccess()){
			response.setMessage(openidResponse.getMessage());
			return response;
		}
		List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "openid", openidResponse.getOpenid());
		if(!ListTools.isEmptyOrNull(leaguerBeanList)){
			response.setMessage("您已注册，请直接登录");
			return response;
		}
		LeaguerBean leaguerBean = JsonTools.covertObject(registerMap, LeaguerBean.class);
		leaguerBean.setOpenid(openidResponse.getOpenid());
    	Object encryptedData = registerMap.get("encryptedData");
		Object iv = registerMap.get("iv");
		if(encryptedData!=null && iv!=null){
			String decryptedData = AESTools.decrypt(StringTools.toString(encryptedData), openidResponse.getSessionKey(), StringTools.toString(iv));
			if(!StringTools.isNullOrNone(decryptedData)){
				Phone phone = JsonTools.toObject(decryptedData, Phone.class);
				if(phone!=null){
					leaguerBean.setPhone(phone.getPurePhoneNumber());
				}
			}
		}
		leaguerBean.setStatus(CustomerStatusEnum.NORMAL.getCode());
		leaguerBean.setCreateTime(DateTools.now());
		try {
			leaguerService.saveLeaguer(leaguerBean);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("token", JwtTools.createToken(leaguerBean, JwtTools.JWTTTLMILLIS));
			response.setData(dataMap);
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
	 * 生成小程序码
	 * @param scene
	 * @param page
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/acode", method = RequestMethod.GET)
	public void createWXACode(@ModelAttribute WXACodeForm form, HttpServletResponse response) throws IOException{
		WXACodeRequest wxaCodeRequest = new WXACodeRequest();
		wxaCodeRequest.setAccessToken(accessTokenService.queryAccessToken());
		wxaCodeRequest.setScene(form.getScene());
		wxaCodeRequest.setPage(form.getPage());
		wxaCodeRequest.setAutoColor(form.getAutoColor());
		wxaCodeRequest.setIsHyaline(form.getIsHyaline());
		wxaCodeRequest.setWidth(form.getWidth());
		if(form.getR()!=null && form.getG()!=null && form.getB()!=null){
			Color color = new Color();
			color.setB(form.getB());
			color.setG(form.getB());
			color.setR(form.getR());
			wxaCodeRequest.setLineColor(color);
		}
		WXACodeResponse wxaCodeResponse = weiXinService.createWXACode(wxaCodeRequest);
		if(!wxaCodeResponse.isSuccess()){
			logger.info("获取小程序码错误---"+wxaCodeResponse.getMessage());
			return;
		}
		response.setContentType("image/jpeg");
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(wxaCodeResponse.getAcode());
		outputStream.flush();
		outputStream.close();
	}
}

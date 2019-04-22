/**
 * 
 */
package com.cc.integration.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.integration.form.IntegrationOperationQueryForm;
import com.cc.integration.service.IntegrationOperationService;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.system.shiro.SecurityContextUtil;
import com.cc.user.bean.TUserBean;
import com.cc.user.enums.UserTypeEnum;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/integration/operation")
public class IntegrationOperationController {

	@Autowired
	private IntegrationOperationService integrationOperationService;
	
	/**
	 * 分页查询积分日志
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<Map<String, Object>> queryIntegrationOperationPage(@ModelAttribute IntegrationOperationQueryForm form){
		Page<Map<String, Object>> page = new Page<Map<String,Object>>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			page.setMessage("请先登录");
			return page;
		}
		if (UserTypeEnum.LEAGUER.getCode().equals(user.getUserType())) {
			List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
			if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size()>1) {
				page.setMessage("您还未注册");
				return page;
			}
			form.setLeaguerId(leaguerBeanList.get(0).getId());
		}
		return integrationOperationService.queryIntegrationOperationPage(form);
	}
}

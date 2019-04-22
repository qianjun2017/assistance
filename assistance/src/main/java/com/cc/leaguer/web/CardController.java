/**
 * 
 */
package com.cc.leaguer.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Response;
import com.cc.leaguer.bean.CardBean;
import com.cc.leaguer.bean.CardLevelBean;
import com.cc.leaguer.service.CardService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/card")
public class CardController {
	
	@Autowired
	private CardService cardService;

	/**
	 * 查询会员卡级别
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "card", "card.level" }, logical = Logical.OR)
	@RequestMapping(value = "/levels", method = RequestMethod.GET)
	public Response<Object> queryCardLevelList(){
		Response<Object> response = new Response<Object>();
		List<CardLevelBean> cardLevelBeanList = CardLevelBean.findAllByParams(CardLevelBean.class);
		if(!ListTools.isEmptyOrNull(cardLevelBeanList)){
			response.setData(cardLevelBeanList);
			response.setSuccess(Boolean.TRUE);
		}
		return response;
	}
	
	/**
	 * 查询会员卡
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "card" })
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Response<Object> queryCardInfo(){
		Response<Object> response = new Response<Object>();
		List<CardBean> cardBeanList = CardBean.findAllByParams(CardBean.class);
		if(ListTools.isEmptyOrNull(cardBeanList)){
			response.setMessage("没有查询到会员卡信息");
			return response;
		}
		Map<String, Object> data = new HashMap<String, Object>();
		CardBean cardBean = cardBeanList.get(0);
		data.put("id", cardBean.getId());
		data.put("name", cardBean.getName());
		if(cardBean.getRule()!=null && cardBean.getRule().length>0){
			data.put("rule", new String(cardBean.getRule()));
		}
		response.setData(data);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 增加会员卡
	 * @param cardMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "card.add" })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CARDMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增会员卡", excludeParamNames = {"rule"})
	public Response<Object> addCard(@RequestBody Map<String, Object> cardMap){
		Response<Object> response = new Response<Object>();
		Object name = cardMap.get("name");
		if(name==null){
			response.setMessage("请输入会员卡名称");
			return response;
		}
		Object rule = cardMap.get("rule");
		if(rule==null){
			response.setMessage("请输入会员卡使用规则");
			return response;
		}
		CardBean cardBean = new CardBean();
		cardBean.setName(StringTools.toString(name));
		cardBean.setRule(StringTools.toString(rule).getBytes());
		try {
			cardService.saveCard(cardBean);
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
	 * 修改会员卡
	 * @param cardMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "card.update" })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CARDMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改会员卡", excludeParamNames = {"rule"})
	public Response<Object> updateCard(@RequestBody Map<String, Object> cardMap){
		Response<Object> response = new Response<Object>();
		Object id = cardMap.get("id");
		if(id==null){
			response.setMessage("缺少会员卡主键");
			return response;
		}
		CardBean cardBean = CardBean.get(CardBean.class, Long.valueOf(StringTools.toString(id)));
		if(cardBean==null){
			response.setMessage("会员卡不存在");
			return response;
		}
		Object name = cardMap.get("name");
		if(name==null){
			response.setMessage("请输入会员卡名称");
			return response;
		}
		Object rule = cardMap.get("rule");
		if(rule==null){
			response.setMessage("请输入会员卡使用规则");
			return response;
		}
		cardBean.setName(StringTools.toString(name));
		cardBean.setRule(StringTools.toString(rule).getBytes());
		try {
			cardService.saveCard(cardBean);
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
	 * 增加会员卡级别
	 * @param cardLevelMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "card.level.add" })
	@RequestMapping(value = "/level/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CARDLEVELMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增会员卡级别")
	public Response<Object> addCardLevel(@RequestBody Map<String, Object> cardLevelMap){
		Response<Object> response = new Response<Object>();
		Object level = cardLevelMap.get("level");
		if(level==null){
			response.setMessage("请输入会员卡级别编码");
			return response;
		}
		Object name = cardLevelMap.get("name");
		if(name==null){
			response.setMessage("请输入会员卡级别名称");
			return response;
		}
		Object gradeIntegration = cardLevelMap.get("gradeIntegration");
		if(gradeIntegration==null){
			response.setMessage("请输入会员卡级别最低积分");
			return response;
		}
		if(!StringTools.matches(StringTools.toString(gradeIntegration), "[0-9]+")){
			response.setMessage("会员卡级别最低积分只能输入数字，请重新输入");
			return response;
		}
		Object imageUrl = cardLevelMap.get("imageUrl");
		if(imageUrl==null){
			response.setMessage("请上传会员卡级别图片");
			return response;
		}
		Object cardId = cardLevelMap.get("cardId");
		if(cardId==null){
			response.setMessage("请选择会员卡");
			return response;
		}
		CardBean cardBean = CardBean.get(CardBean.class, Long.valueOf(StringTools.toString(cardId)));
		if(cardBean==null){
			response.setMessage("会员卡不存在或已删除，请重新选择会员卡");
			return response;
		}
		Object color = cardLevelMap.get("color");
		if(color==null){
			response.setMessage("请输入会员卡级别图片中字体颜色");
			return response;
		}
		if(!StringTools.matches(StringTools.toString(color), "^#[0-9A-F]{6}$")){
			response.setMessage("会员卡级别图片中字体颜色使用十六进制表示，如#FFFFFF，请重新输入");
			return response;
		}
		CardLevelBean cardLevelBean = new CardLevelBean();
		cardLevelBean.setLevel(StringTools.toString(level));
		cardLevelBean.setName(StringTools.toString(name));
		cardLevelBean.setImageUrl(StringTools.toString(imageUrl));
		cardLevelBean.setGradeIntegration(Long.valueOf(StringTools.toString(gradeIntegration)));
		cardLevelBean.setCardId(cardBean.getId());
		cardLevelBean.setColor(StringTools.toString(color));
		try {
			cardService.saveCardLevel(cardLevelBean);
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
	 * 修改会员卡级别
	 * @param cardLevelMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "card.level.update" })
	@RequestMapping(value = "/level/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CARDLEVELMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改会员卡级别")
	public Response<Object> updateCardLevel(@RequestBody Map<String, Object> cardLevelMap){
		Response<Object> response = new Response<Object>();
		Object id = cardLevelMap.get("id");
		if(id==null){
			response.setMessage("缺少会员卡级别主键");
			return response;
		}
		CardLevelBean cardLevelBean = CardLevelBean.get(CardLevelBean.class, Long.valueOf(StringTools.toString(id)));
		if(cardLevelBean==null){
			response.setMessage("会员卡级别不存在");
			return response;
		}
		Object level = cardLevelMap.get("level");
		if(level==null){
			response.setMessage("请输入会员卡级别编码");
			return response;
		}
		Object name = cardLevelMap.get("name");
		if(name==null){
			response.setMessage("请输入会员卡级别名称");
			return response;
		}
		Object gradeIntegration = cardLevelMap.get("gradeIntegration");
		if(gradeIntegration==null){
			response.setMessage("请输入会员卡级别最低积分");
			return response;
		}
		if(!StringTools.matches(StringTools.toString(gradeIntegration), "[0-9]+")){
			response.setMessage("会员卡级别最低积分只能输入数字，请重新输入");
			return response;
		}
		Object imageUrl = cardLevelMap.get("imageUrl");
		if(imageUrl==null){
			response.setMessage("请上传会员卡级别图片");
			return response;
		}
		Object cardId = cardLevelMap.get("cardId");
		if(cardId==null){
			response.setMessage("请选择会员卡");
			return response;
		}
		CardBean cardBean = CardBean.get(CardBean.class, Long.valueOf(StringTools.toString(cardId)));
		if(cardBean==null){
			response.setMessage("会员卡不存在或已删除，请重新选择会员卡");
			return response;
		}
		Object color = cardLevelMap.get("color");
		if(color==null){
			response.setMessage("请输入会员卡级别图片中字体颜色");
			return response;
		}
		if(!StringTools.matches(StringTools.toString(color), "^#[0-9A-F]{6}$")){
			response.setMessage("会员卡级别图片中字体颜色使用十六进制表示，如#FFFFFF，请重新输入");
			return response;
		}
		cardLevelBean.setLevel(StringTools.toString(level));
		cardLevelBean.setName(StringTools.toString(name));
		cardLevelBean.setImageUrl(StringTools.toString(imageUrl));
		cardLevelBean.setGradeIntegration(Long.valueOf(StringTools.toString(gradeIntegration)));
		cardLevelBean.setCardId(cardBean.getId());
		cardLevelBean.setColor(StringTools.toString(color));
		try {
			cardService.saveCardLevel(cardLevelBean);
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
	 * 查询会员卡级别
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "card.level.update" })
	@RequestMapping(value = "/level/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<Object> queryCardLevel(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		CardLevelBean cardLevelBean = CardLevelBean.get(CardLevelBean.class, id);
		if(cardLevelBean==null){
			response.setMessage("会员卡级别不存在");
			return response;
		}
		response.setData(cardLevelBean);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 删除会员卡级别
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "card.level.delete" })
	@RequestMapping(value = "/level/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CARDLEVELMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除会员卡级别", paramNames = {"id"})
	public Response<Object> deleteCardLevel(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		CardLevelBean cardLevelBean = CardLevelBean.get(CardLevelBean.class, id);
		if(cardLevelBean==null){
			response.setMessage("会员卡级别不存在");
			return response;
		}
		try {
			cardService.deleteCardLevel(id);
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

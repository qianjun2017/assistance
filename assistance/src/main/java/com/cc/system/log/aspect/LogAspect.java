/**
 * 
 */
package com.cc.system.log.aspect;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cc.system.user.bean.UserBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.web.Page;
import com.cc.common.web.RequestContextUtil;
import com.cc.common.web.Response;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.bean.OperationLogBean;
import com.cc.system.log.utils.LogContextUtil;
import com.cc.system.shiro.SecurityContextUtil;

/**
 * 日志切面
 * @author Administrator
 *
 */
@Aspect
@Component
public class LogAspect {

	private static Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	/**
	 * 操作日志记录
	 * @param joinPoint
	 * @param log
	 * @return
	 * @throws Throwable
	 */
	@Around(value="@annotation(log)")
	public Object operationLogAdvice(ProceedingJoinPoint joinPoint, OperationLog log) throws Throwable{
		OperationLogBean logBean = new OperationLogBean();
		UserBean userBean = SecurityContextUtil.getCurrentUser();
		logBean.setOperateTime(DateTools.now());
		Object object = joinPoint.proceed();
		logBean.setTime(DateTools.getInterval(DateTools.now(), logBean.getOperateTime()));
		if(!LogContextUtil.recordLog()){
			LogContextUtil.setRecordLog(Boolean.TRUE);
			LogContextUtil.setOperContent(null);
			return object;
		}
		if(userBean==null){
			userBean = SecurityContextUtil.getCurrentUser();
		}
		if (userBean!=null) {
			logBean.setUserId(userBean.getId());
			logBean.setUserName(userBean.getUserName());
		}else{
			logger.warn("记录操作日志时，没有查询到当前登录用户信息");
		}
		HttpServletRequest httpServletRequest = RequestContextUtil.httpServletRequest();
		logBean.setClientIp(RequestContextUtil.getIpAddr(httpServletRequest));
		logBean.setModule(log.module().getCode());
		logBean.setOperType(log.operType().getCode());
		logBean.setTitle(log.title());
		Object[] args = joinPoint.getArgs();
		Map<String, Object> params = new HashMap<String, Object>();
		String[] paramNames = log.paramNames();
		String[] excludeParamNames = log.excludeParamNames();
		if(!ArrayTools.isEmptyOrNull(args)){
			for (int i = 0; i < args.length; i ++) {
				Object arg = args[i];
				if(arg instanceof Map){
					params.putAll((Map<? extends String, ? extends Object>) arg);
				}else{
					params.put((i<paramNames.length)?paramNames[i]:("param"+i), arg);
				}
			}
			if(params.containsKey("password")){
				params.remove("password");
			}
			for (String excludeParamName: excludeParamNames) {
				params.remove(excludeParamName);
			}
			logBean.setParams(JsonTools.toJsonString(params));
		}
		if(object instanceof Response){
			Response<?> response = (Response<?>) object;
			if (response.isSuccess()) {
				logBean.setResult("成功");
			}else {
				logBean.setResult("失败:"+response.getMessage());
			}
		}else if(object instanceof Page<?>){
			Page<?> page = (Page<?>) object;
			if(page.isSuccess()){
				logBean.setResult("成功");
			}else{
				logBean.setResult("失败:"+page.getMessage());
			}
		}else {
			String classType = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			logger.warn("无法获取并记录操作结果,方法:"+classType+"."+methodName);
		}
		logBean.setContent(LogContextUtil.getOperContent());
		try {
			logBean.insert();
		} catch (Exception e) {
			logger.info("保存操作日志异常");
			e.printStackTrace();
		}
		LogContextUtil.setOperContent(null);
		return object;
	}
	
}

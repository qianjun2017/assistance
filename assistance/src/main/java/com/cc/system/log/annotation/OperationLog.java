/**
 * 
 */
package com.cc.system.log.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
/**
 * 操作日志注解
 * @author Administrator
 *
 */
public @interface OperationLog {

	/**
	 * 模块
	 * @return
	 */
	ModuleEnum module();
	
	/**
	 * 操作
	 * @return
	 */
	OperTypeEnum operType();
	
	/**
	 * 参数列表
	 * @return
	 */
	String[] paramNames() default {};
	
	/**
	 * 操作简介
	 * @return
	 */
	String title() default "";

	/**
	 * 排除参数列表
	 * @return
	 */
	String[] excludeParamNames() default {};
}

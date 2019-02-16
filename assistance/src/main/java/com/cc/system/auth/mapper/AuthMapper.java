/**
 * 
 */
package com.cc.system.auth.mapper;

import org.springframework.stereotype.Component;

import com.cc.common.orm.mapper.CrudMapper;
import com.cc.system.auth.bean.AuthBean;

/**
 * @author Administrator
 *
 */
@Component
public interface AuthMapper extends CrudMapper<AuthBean> {

}

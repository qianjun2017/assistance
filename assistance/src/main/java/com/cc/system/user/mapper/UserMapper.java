/**
 * 
 */
package com.cc.system.user.mapper;

import org.springframework.stereotype.Component;

import com.cc.common.orm.mapper.CrudMapper;
import com.cc.system.user.bean.UserBean;

/**
 * @author Administrator
 *
 */
@Component
public interface UserMapper extends CrudMapper<UserBean> {

}

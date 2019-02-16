/**
 * 
 */
package com.cc.system.log.mapper;

import org.springframework.stereotype.Component;

import com.cc.common.orm.mapper.CrudMapper;
import com.cc.system.log.bean.OperationLogBean;

/**
 * @author Administrator
 *
 */
@Component
public interface OperationLogMapper extends CrudMapper<OperationLogBean> {

}

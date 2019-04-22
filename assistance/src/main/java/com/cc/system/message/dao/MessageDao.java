package com.cc.system.message.dao;

import com.cc.common.orm.dao.CrudDao;
import com.cc.system.message.bean.MessageBean;
import com.cc.system.message.form.MessageQueryForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by yuanwenshu on 2018/8/20.
 */
@Mapper
public interface MessageDao extends CrudDao {

    /**
     * 查询消息列表
     * @param form
     * @return
     */
    List<MessageBean> queryMessageList(MessageQueryForm form);
}

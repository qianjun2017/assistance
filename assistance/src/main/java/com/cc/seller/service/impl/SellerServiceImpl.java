package com.cc.seller.service.impl;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.seller.bean.SellerBean;
import com.cc.seller.enums.SellerStatusEnum;
import com.cc.seller.form.SellerQueryForm;
import com.cc.seller.service.SellerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by yuanwenshu on 2018/10/12.
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void saveSeller(SellerBean sellerBean) {
        int row =  sellerBean.save();
        if(row!=1){
            throw new LogicException("E001", "保存卖家信息失败");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void lockSeller(Long id) {
        SellerBean sellerBean = new SellerBean();
        sellerBean.setId(id);
        sellerBean.setStatus(SellerStatusEnum.LOCKED.getCode());
        int row =  sellerBean.update();
        if(row!=1){
            throw new LogicException("E001", "锁定卖家失败");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void unLockSeller(Long id) {
        SellerBean sellerBean = new SellerBean();
        sellerBean.setId(id);
        sellerBean.setStatus(SellerStatusEnum.NORMAL.getCode());
        int row =  sellerBean.update();
        if(row!=1){
            throw new LogicException("E001", "解锁卖家失败");
        }
    }

    @Override
    public Page<SellerBean> querySellerPage(SellerQueryForm form) {
        Page<SellerBean> page  = new Page<SellerBean>();
        Example example = new Example(SellerBean.class);
        Example.Criteria nameCriteria = example.createCriteria();
        if(!StringTools.isNullOrNone(form.getName())){
            nameCriteria.orLike("sellerName", "%"+form.getName()+"%");
            nameCriteria.orLike("realName", "%"+form.getName()+"%");
        }
        Example.Criteria criteria = example.createCriteria();
        if(!StringTools.isNullOrNone(form.getPhone())){
            criteria.andEqualTo("phone", form.getPhone());
        }
        if(!StringTools.isNullOrNone(form.getStatus())){
            criteria.andEqualTo("status", form.getStatus());
        }
        example.and(criteria);
        PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
        PageHelper.startPage(form.getPage(), form.getPageSize());
        List<SellerBean> sellerBeanList = SellerBean.findByExample(SellerBean.class, example);
        PageInfo<SellerBean> pageInfo = new PageInfo<SellerBean>(sellerBeanList);
        if (ListTools.isEmptyOrNull(sellerBeanList)) {
            page.setMessage("没有查询到相关卖家数据");
            return page;
        }
        page.setPage(pageInfo.getPageNum());
        page.setPages(pageInfo.getPages());
        page.setPageSize(pageInfo.getPageSize());
        page.setTotal(pageInfo.getTotal());
        page.setData(sellerBeanList);
        page.setSuccess(Boolean.TRUE);
        return page;
    }

	/* (non-Javadoc)
	 * @see com.cc.seller.service.SellerService#updateSeller(com.cc.seller.bean.SellerBean)
	 */
	@Override
	public void updateSeller(SellerBean sellerBean) {
		int row =  sellerBean.update();
        if(row!=1){
            throw new LogicException("E001", "修改卖家信息失败");
        }
	}
}

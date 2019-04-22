package com.cc.goods.web;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Response;
import com.cc.common.web.Tree;
import com.cc.goods.bean.GoodsCategoryBean;
import com.cc.goods.service.GoodsCategoryService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by yuanwenshu on 2018/10/25.
 */
@Controller
@RequestMapping("/goods/category")
public class GoodsCategoryController {

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    /**
     * 新增商品品类
     * @param goodsCategoryMap
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "goods.category.add" })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.GOODSCATEGORYMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增商品品类")
    public Response<String> addGoodsCategory(@RequestBody Map<String, String> goodsCategoryMap) {
        Response<String> response = new Response<String>();
        GoodsCategoryBean goodsCategoryBean = new GoodsCategoryBean();
        String name = goodsCategoryMap.get("name");
        if (StringTools.isNullOrNone(name)) {
            response.setMessage("请输入商品品类名称");
            return response;
        }
        List<GoodsCategoryBean> goodsCategoryBeanList = GoodsCategoryBean.findAllByParams(GoodsCategoryBean.class, "name", name);
        if(!ListTools.isEmptyOrNull(goodsCategoryBeanList)){
            response.setMessage("商品品类名称已存在，请重新输入");
            return response;
        }
        goodsCategoryBean.setName(name);
        String parentId = goodsCategoryMap.get("parentId");
        if (!StringTools.isNullOrNone(parentId)) {
            GoodsCategoryBean parentGoodsCategoryBean = GoodsCategoryBean.get(GoodsCategoryBean.class, Long.valueOf(parentId));
            if (parentGoodsCategoryBean==null) {
                response.setMessage("父级商品品类不存在,请重新选择");
                return response;
            }
            goodsCategoryBean.setParentId(Long.valueOf(parentId));
            goodsCategoryBean.setLevel(parentGoodsCategoryBean.getLevel()+1);
            goodsCategoryBean.setPath(parentGoodsCategoryBean.getPath()+"/"+goodsCategoryBean.getName());
        }else {
            goodsCategoryBean.setLevel(0);
            goodsCategoryBean.setPath(goodsCategoryBean.getName());
        }
        try {
            goodsCategoryService.saveGoodsCategory(goodsCategoryBean);
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
     * 修改商品品类
     * @param goodsCategoryMap
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "goods.category.update" })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.GOODSCATEGORYMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改商品品类")
    public Response<String> updateGoodsCategory(@RequestBody Map<String, String> goodsCategoryMap) {
        Response<String> response = new Response<String>();
        String id = goodsCategoryMap.get("id");
        if(StringTools.isNullOrNone(id)){
            response.setMessage("缺少商品品类主键");
            return response;
        }
        GoodsCategoryBean goodsCategoryBean = GoodsCategoryBean.get(GoodsCategoryBean.class, Long.valueOf(id));
        String name = goodsCategoryMap.get("name");
        if (StringTools.isNullOrNone(name)) {
            response.setMessage("请输入商品品类名称");
            return response;
        }
        List<GoodsCategoryBean> goodsCategoryBeanList = GoodsCategoryBean.findAllByParams(GoodsCategoryBean.class, "name", name);
        if(!ListTools.isEmptyOrNull(goodsCategoryBeanList)){
            for (GoodsCategoryBean categoryBean: goodsCategoryBeanList) {
                if (categoryBean.equals(goodsCategoryBean)) {
                    continue;
                }
                response.setMessage("商品品类名称已存在，请重新输入");
                return response;
            }
        }
        goodsCategoryBean.setName(name);
        String parentId = goodsCategoryMap.get("parentId");
        if (!StringTools.isNullOrNone(parentId)) {
            GoodsCategoryBean parentGoodsCategoryBean = GoodsCategoryBean.get(GoodsCategoryBean.class, Long.valueOf(parentId));
            if (parentGoodsCategoryBean==null) {
                response.setMessage("父级商品品类不存在,请重新选择");
                return response;
            }
            goodsCategoryBean.setParentId(Long.valueOf(parentId));
            goodsCategoryBean.setLevel(parentGoodsCategoryBean.getLevel()+1);
            goodsCategoryBean.setPath(parentGoodsCategoryBean.getPath()+"/"+goodsCategoryBean.getName());
        }else {
            goodsCategoryBean.setLevel(0);
            goodsCategoryBean.setPath(goodsCategoryBean.getName());
        }
        try {
            goodsCategoryService.saveGoodsCategory(goodsCategoryBean);
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
     * 删除商品品类
     * @param id
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "goods.category.delete" })
    @RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.GOODSCATEGORYMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除商品品类", paramNames = {"id"})
    public Response<String> deleteGoodsCategory(@PathVariable Long id){
        Response<String> response = new Response<String>();
        try {
            List<GoodsCategoryBean> subGoodsCategoryBeanList = GoodsCategoryBean.findAllByParams(GoodsCategoryBean.class, "parentId", id);
            if(!ListTools.isEmptyOrNull(subGoodsCategoryBeanList)){
                response.setMessage("该商品品类下存在子商品品类");
                response.setData("more");
                return response;
            }
            goodsCategoryService.deleteGoodsCategory(id);
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
     * 删除商品品类
     * @param id
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "goods.category.delete" })
    @RequestMapping(value = "/cascade/delete/{id:\\d+}", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.GOODSCATEGORYMANAGEMENT, operType = OperTypeEnum.DELETE, title = "强制删除商品品类", paramNames = {"id"})
    public Response<String> deleteGoodsCategoryCascade(@PathVariable Long id){
        Response<String> response = new Response<String>();
        try {
            goodsCategoryService.deleteGoodsCategoryCascade(id);
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
     * 查询商品品类树
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Tree<Map<String, Object>> queryGoodsCategoryTree(){
        Tree<Map<String, Object>> tree = goodsCategoryService.queryGoodsCategoryTree();
        return tree;
    }

    /**
     * 获取商品品类详情
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
    public Response<GoodsCategoryBean> queryGoodsCategory(@PathVariable Long id){
        Response<GoodsCategoryBean> response = new Response<GoodsCategoryBean>();
        GoodsCategoryBean goodsCategoryBean = GoodsCategoryBean.get(GoodsCategoryBean.class, id);
        if(goodsCategoryBean==null){
            response.setMessage("商品品类不存在");
            return response;
        }
        response.setData(goodsCategoryBean);
        response.setSuccess(Boolean.TRUE);
        return response;
    }
}

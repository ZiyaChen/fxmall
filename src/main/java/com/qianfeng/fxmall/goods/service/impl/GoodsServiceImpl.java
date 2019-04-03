package com.qianfeng.fxmall.goods.service.impl;

import com.qianfeng.fxmall.commons.info.SystemConstantsUtils;
import com.qianfeng.fxmall.goods.bean.WxbGood;
import com.qianfeng.fxmall.goods.dao.IGoodDAO;
//import com.qianfeng.fxmall.goods.dao.impl.GoodsDAOImpl;
import com.qianfeng.fxmall.goods.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class GoodsServiceImpl implements IGoodsService {

    //private IGoodDAO goodsDao = new GoodsDAOImpl();
    @Autowired
    private IGoodDAO iGoodDAO;

    @Override
    public List<WxbGood> queryGoodsByPage(Integer page) throws Exception {
        if (page < 1){
            throw new IndexOutOfBoundsException("页码不能小于1");
        }
        //计算起始下标
        int index = (page -1)* SystemConstantsUtils.Page.PAGE_SIZE;

        List<WxbGood> goods = iGoodDAO.queryGoodsByPage(index);
        return goods;
    }

    @Override
    public void insertGoods(WxbGood wxbGood) {
        iGoodDAO.insertGoods(wxbGood);
    }
}

package com.qianfeng.fxmall.goods.dao;

import com.qianfeng.fxmall.goods.bean.WxbGood;


import java.util.List;

/**
 * 商品管理分页
 */

public interface IGoodDAO {

    /**
     * 查询所有商品信息并分页
     * @param page
     * @return
     * @throws Exception
     */
    List<WxbGood> queryGoodsByPage(Integer page) throws Exception;

    void insertGoods(WxbGood wxbGood);
}

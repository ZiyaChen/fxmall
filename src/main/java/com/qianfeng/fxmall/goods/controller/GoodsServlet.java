package com.qianfeng.fxmall.goods.controller;

import com.qianfeng.fxmall.goods.bean.WxbGood;
import com.qianfeng.fxmall.goods.service.IGoodsService;
import com.qianfeng.fxmall.goods.service.impl.GoodsServiceImpl;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class GoodsServlet extends BaseServlet {
    public static final String UPLOAD_PATH = "D:/ZQ/test/";
    //private IGoodsService goodsService = new GoodsServiceImpl();
    final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
    IGoodsService goodsService = applicationContext.getBean(GoodsServiceImpl.class);

    /**
     * 查找所有商品信息并分页
     * @param req
     * @param resp
     */
    public void queryGoodsByPage(HttpServletRequest req,HttpServletResponse resp){
        String pageStr = req.getParameter("page");
        try {
            pageStr = pageStr ==null?"1":pageStr;
            List<WxbGood> goodList = goodsService.queryGoodsByPage(Integer.parseInt(pageStr));
            req.setAttribute("goodsList",goodList);
            req.getRequestDispatcher("goods_list.jsp").forward(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加商品信息
     * @param req
     * @param resp
     */
    public void insertGoods(HttpServletRequest req,HttpServletResponse resp) throws IOException, FileUploadException {
        WxbGood wxbGood = new WxbGood();
        String goodId = UUID.randomUUID().toString().substring(2,10);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        wxbGood.setGoodId(goodId);
        wxbGood.setCustomerId("aaa");
        wxbGood.setSkuTitle("aaa");
        wxbGood.setSkuCost("aaa");
        wxbGood.setSkuPrice("aaa");
        wxbGood.setSkuPmoney("aaa");
        wxbGood.setCopyIds("aaa");
        wxbGood.setForwardLink("aaaa");
        wxbGood.setState(1);
        wxbGood.setCreateTime(timestamp);
        wxbGood.setToped(1);
        wxbGood.setRecomed(1);
        wxbGood.setTopedTime(timestamp);
        wxbGood.setRecomedTime(timestamp);
        wxbGood.setSpcId("a");
        wxbGood.setZonId("aa");
        wxbGood.setWebsite("a");
        wxbGood.setIswxpay(1);
        wxbGood.setIsfdfk(1);
        wxbGood.setLeixingId(1);
        wxbGood.setKfqq("aaaaa");
        if(ServletFileUpload.isMultipartContent(req)) {
            ServletFileUpload upload = new ServletFileUpload();
            upload.setSizeMax(10 * 1024 *1024);
            FileItemIterator itr =upload.getItemIterator(req);
            int i = 0;
            while(itr.hasNext()) {
                FileItemStream item = itr.next();
                if (item.isFormField()) {
                    String value = Streams.asString(item.openStream(), "utf-8");
                    switch (item.getFieldName()) {
                        case "good_name":
                            wxbGood.setGoodName(value);
                            break;
                        case "type_id":
                            wxbGood.setTypeId(value);
                            break;
                        case "order_no":
                            wxbGood.setOrderNo(Long.parseLong(value));
                            break;
                        case "sell_num":
                            wxbGood.setSellNum(Long.parseLong(value));
                            break;
                        case "promote_desc":
                            wxbGood.setPromoteDesc(value);
                            break;
                        case "tags":
                            wxbGood.setTags(value);
                            break;
                        case "copy_desc":
                            wxbGood.setCopyDesc(value);
                            break;
                    }
                } else {
                    //获得文件名,进行处理
                    String filename = item.getName();
                    if (filename != null) {
                        String filename2 = UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
                        //保存新文件名,用于存入数据库
                        if(i == 0){
                            wxbGood.setGoodPic(filename2);
                        }
                        if(i == 1){
                            wxbGood.setGoodPic1(filename2);
                        }
                        if(i == 2){
                            wxbGood.setGoodPic2(filename2);
                        }
                        filename = UPLOAD_PATH + filename2;
                        //创建文件输出流
                        FileOutputStream out = new FileOutputStream(filename);
                        //读上传文件流,写入文件
                        Streams.copy(item.openStream(), out, true);
                        System.out.println("图片上传成功");
                        i++;
                    }
                }
            }
        }
        IGoodsService goodsService = new GoodsServiceImpl();
        goodsService.insertGoods(wxbGood);
        System.out.println("图片上传成功");
        queryGoodsByPage(req,resp);
    }
}

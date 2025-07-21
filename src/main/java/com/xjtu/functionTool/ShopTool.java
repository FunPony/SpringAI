package com.xjtu.functionTool;

import cn.hutool.json.JSONUtil;
import com.xjtu.entity.Shop;
import com.xjtu.sevice.IShopService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ShopTool {

    @Autowired
    private IShopService shopService;

    @Tool(description = "查询店铺、商家、餐厅等相关内容")
    public String getShops(){
        log.info("调用shoptoll！");
        List<Shop> shops = shopService.query().list();
        return JSONUtil.toJsonStr(shops);
    }
}

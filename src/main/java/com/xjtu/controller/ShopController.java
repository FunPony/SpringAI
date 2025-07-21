package com.xjtu.controller;


import com.xjtu.entity.Result;
import com.xjtu.entity.Shop;
import com.xjtu.sevice.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test/")
public class ShopController {

    @Autowired
    private IShopService shopService;

    @GetMapping("/bean/{id}")
    public Result testbean(@PathVariable("id")Long id){
        Shop byId = shopService.getById(id);
        System.out.println("正在查询...");
        return Result.ok(byId);
    }

}

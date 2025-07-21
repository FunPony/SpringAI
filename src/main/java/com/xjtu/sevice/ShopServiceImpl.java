package com.xjtu.sevice;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjtu.entity.Shop;
import com.xjtu.mapper.ShopMapper;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper,Shop> implements IShopService {

}

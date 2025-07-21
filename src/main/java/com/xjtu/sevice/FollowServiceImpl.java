package com.xjtu.sevice;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjtu.entity.Follow;
import com.xjtu.mapper.FollowMapper;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {
}

package com.xjtu.sevice;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjtu.entity.Blog;
import com.xjtu.mapper.BlogMapper;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService  {
}

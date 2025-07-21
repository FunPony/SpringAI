package com.xjtu.functionTool;

import cn.hutool.json.JSONUtil;
import com.xjtu.entity.Blog;
import com.xjtu.sevice.IBlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BlogTools {

    @Autowired
    private IBlogService blogService;

    @Tool(description = "帮我查询博客、笔记、推文等相关内容")
    public String getBlogs(){
        log.info("调用Blogtool！");
        List<Blog> list = blogService.query().list();
        return JSONUtil.toJsonStr(list);
    }
}

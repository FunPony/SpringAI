package com.xjtu.functionTool;

import cn.hutool.json.JSONUtil;
import com.xjtu.entity.Blog;
import com.xjtu.entity.Follow;
import com.xjtu.entity.User;
// import com.xjtu.sevice.IUserService;
import com.xjtu.sevice.IBlogService;
import com.xjtu.sevice.IFollowService;
import com.xjtu.sevice.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UserTools {

    @Autowired
    private IUserService userService;
    @Autowired
    private IBlogService blogService;
    @Autowired
    private IFollowService followService;

    @Tool(description = "帮我查询某用户的笔记等相关内容")
    public String getUserBlog(@ToolParam(description = "用户名")String nickname){
        Long userId = getId(nickname);
        List<Blog> list = blogService.query()
                .eq("user_id", userId)
                .list();
        return JSONUtil.toJsonStr(list);
    }

    @Tool(description = "帮我查某用户的关注对象、粉丝等相关内容")
    public String getUserLike(@ToolParam(description = "用户名")String nickname){
        log.info("调用UserTool！");
        Long userId = getId(nickname);
        List<Follow> users = followService.query()
                .eq("user_id", userId)
                .list();
        List<Long> ids = new ArrayList<>();
        users.stream()
                .forEach(follow -> ids.add(follow.getFollowUserId()));
        if (ids==null || ids.isEmpty())return "未找到其关注者";
        List<User> user = userService.query()
                .in("id",ids)
                .list();
        return JSONUtil.toJsonStr(user);
    }

    public Long getId(String nickname){
        User user = userService.query()
                .eq("nick_name", nickname)
                .one();
        return user.getId();
    }
}

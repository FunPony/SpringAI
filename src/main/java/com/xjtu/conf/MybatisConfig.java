package com.xjtu.conf;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.xjtu.mapper.BlogMapper;
import com.xjtu.mapper.FollowMapper;
import com.xjtu.mapper.ShopMapper;
import com.xjtu.mapper.UserMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

    @Bean
    public MapperFactoryBean<ShopMapper> shopMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<ShopMapper> factory = new MapperFactoryBean<>(ShopMapper.class);
        factory.setSqlSessionFactory(sqlSessionFactory);
        return factory;
    }

    @Bean
    public MapperFactoryBean<BlogMapper> blogMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<BlogMapper> factory = new MapperFactoryBean<>(BlogMapper.class);
        factory.setSqlSessionFactory(sqlSessionFactory);
        return factory;
    }

    @Bean
    public MapperFactoryBean<UserMapper> userMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<UserMapper> factory = new MapperFactoryBean<>(UserMapper.class);
        factory.setSqlSessionFactory(sqlSessionFactory);
        return factory;
    }

    @Bean
    public MapperFactoryBean<FollowMapper> followMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<FollowMapper> factory = new MapperFactoryBean<>(FollowMapper.class);
        factory.setSqlSessionFactory(sqlSessionFactory);
        return factory;
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}

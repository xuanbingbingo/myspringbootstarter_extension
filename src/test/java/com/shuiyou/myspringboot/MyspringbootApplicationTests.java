package com.shuiyou.myspringboot;

import com.shuiyou.myspringboot.entity.User;
import com.shuiyou.myspringboot.util.RedisUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyspringbootApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void contextLoads() {
    }

    // 测试直接使用redis
    @Test
    public void testStringWithRedis(){
        stringRedisTemplate.opsForValue().set("name", "guanguan");
        String val = stringRedisTemplate.opsForValue().get("name");
        Assert.assertEquals("guanguan", val);
    }
    // 测试使用RedisUtil工具类分别存入字符串和序列化的对象
    @Test
    public void testRedisUtilSetString(){
        // 缓存60秒
        redisUtil.set("bingoString", 222,60L);
    }
    @Test
    public void testRedisUtilSetObject(){
        User user = new User();
        user.setAge(18);
        user.setId(1);
        user.setPassword("123456");
        user.setUserName("bingoObject");
        redisUtil.set("bingoObject",user);
    }


}

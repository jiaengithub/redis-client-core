package net.lbtech.redis;

import net.lbtech.redis.core.RedisObjectClient;
import net.lbtech.redis.core.impl.RedisClientImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试redis 对象缓存
 * @author DF
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-jedis-single.xml"})
public class TestRedisObject {

	@Autowired
	private RedisClientImpl redisClientImpl;
	@Test
	public void t1(){
		redisClientImpl.opsForObject().set("k1", new User("12", 12), User.class, 0);
		User u = redisClientImpl.opsForObject().get("k1", User.class);
		System.out.println("age :" + u.getAge());
	}
}

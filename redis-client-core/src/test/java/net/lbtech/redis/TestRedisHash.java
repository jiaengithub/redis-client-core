package net.lbtech.redis;

import java.util.Map;

import net.lbtech.redis.core.RedisObjectClient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试redis 对象缓存
 * @author DF
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-jedis-single.xml"})
public class TestRedisHash {

	@Autowired
	private RedisObjectClient client;
	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	@Test
	public void t1(){
		client.set("k1", new User("12", 12), User.class, 0);
		User u = client.get("k1", User.class);
		System.out.println("age :" + u.getAge());
	}
	@Test
	public void t2(){
		redisTemplate.opsForValue().set("k2", new User("12", 13));
		redisTemplate.opsForHash().put("1", "2", new User("12", 12));
		User u = redisTemplate.opsForValue().get("k2");
		System.out.println(u.getAge());
	}
}

package net.lbtech.redis.core.impl;

import java.io.Serializable;

import net.lbtech.redis.core.RedisClient;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * 实际调用redis类
 * @author DF
 *
 */
public class RedisClientImpl extends RedisClient {
	
    private RedisKeyClientImpl redisKeyClientImpl;
    private RedisHashClientImpl redisHashClientImpl;
    private RedisListClientImpl redisListClientImpl;
    private RedisObjectClientImpl redisObjectClientImpl;
    private RedisSetClientImpl redisSetClientImpl;
    
	public RedisClientImpl(RedisTemplate<Serializable, Serializable> redisTemplate) {
		super(redisTemplate);
		redisKeyClientImpl = new RedisKeyClientImpl(redisTemplate);
		redisHashClientImpl = new RedisHashClientImpl(redisTemplate);
		redisListClientImpl = new RedisListClientImpl(redisTemplate);
		redisObjectClientImpl = new RedisObjectClientImpl(redisTemplate);
		redisSetClientImpl = new RedisSetClientImpl(redisTemplate);
	}
	public RedisKeyClientImpl opsForKey(){
		return redisKeyClientImpl;
	}
	public RedisHashClientImpl opsForHash(){
		return  redisHashClientImpl;
	}
	public RedisListClientImpl opsForList(){
		return redisListClientImpl;
	}
	public RedisObjectClientImpl opsForObject(){
		return redisObjectClientImpl;
	}
	public RedisSetClientImpl opsForSet(){
		return redisSetClientImpl;
	}
}

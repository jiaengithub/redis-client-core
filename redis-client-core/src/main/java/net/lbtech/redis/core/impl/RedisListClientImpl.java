package net.lbtech.redis.core.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import net.lbtech.redis.core.RedisClient;
import net.lbtech.redis.core.RedisListClient;

/**
 * redis的list操作
 * @author DF
 *
 */
public class RedisListClientImpl extends RedisClient implements RedisListClient {

	public RedisListClientImpl(RedisTemplate<Serializable, Serializable> redisTemplate) {
		super(redisTemplate);
	}

	/* (non-Javadoc)
	 * @see net.lbtech.redis.core.impl.RedisListClient#lpush(java.lang.String, int, T)
	 */
	public <T> long lpush(final String key,final int seconds,final T... values){
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		Assert.notEmpty(values,"values is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				List<byte[]> list = new ArrayList<byte[]>();
				byte[] byteKey = getBytesKey(key);
				long returnValue = 0;
				for (T v :values) {
					list.add(toBytes(v));
				}
				logger.debug("lpush key {} = {} ",key,values);
				returnValue = connection.lPush(byteKey, (byte[][])list.toArray());
				if (seconds != 0) {
					connection.expire(byteKey, seconds);
				}
				return returnValue ;
			}
		});
	}
	/* (non-Javadoc)
	 * @see net.lbtech.redis.core.impl.RedisListClient#lpush(java.lang.String, T)
	 */
	public <T> long lpush(final String key,final T... values){
		return lpush(key, 0, values);
	}
	/* (non-Javadoc)
	 * @see net.lbtech.redis.core.impl.RedisListClient#rpush(java.lang.String, int, T)
	 */
	public <T> long rpush(final String key,final int seconds,final T... values){
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		Assert.notEmpty(values,"values is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				List<byte[]> list = new ArrayList<byte[]>();
				byte[] byteKey = getBytesKey(key);
				long returnValue = 0;
				for (T v :values) {
					list.add(toBytes(v));
				}
				logger.debug("rpush key {} = {} ",key,values);
				returnValue = connection.rPush(byteKey, (byte[][])list.toArray());
				if (seconds != 0) {
					connection.expire(byteKey, seconds);
				}
				return returnValue ;
			}
		});
	}
	/* (non-Javadoc)
	 * @see net.lbtech.redis.core.impl.RedisListClient#rpush(java.lang.String, T)
	 */
	public <T> long rpush(final String key,final T... values){
		return rpush(key, 0, values);
	}
	/* (non-Javadoc)
	 * @see net.lbtech.redis.core.impl.RedisListClient#lrange(java.lang.String, int, int)
	 */
	public <T> List<T> lrange(final String key,final int begin,final int end){
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<List<T>>() {
			public List<T> doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				List<T> returnValue = new ArrayList<T>();
				if (connection.exists(byteKey)) {
					List<byte[]> list =  connection.lRange(byteKey, begin, end);
					for (byte[] bytes :list) {
						returnValue.add((T)toObject(bytes));
					}
					logger.debug("lrange key {} = {} ",key,returnValue);
				}
				return returnValue;
			}
		});
	}
}

package net.lbtech.redis.core.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.lbtech.redis.core.RedisClient;
import net.lbtech.redis.core.RedisSetClient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

/**
 * redis 对set的操作
 * @author DF
 *
 */
public class RedisSetClientImpl extends RedisClient implements RedisSetClient {

	public RedisSetClientImpl(RedisTemplate<Serializable, Serializable> redisTemplate) {
		super(redisTemplate);
	}

	/* (non-Javadoc)
	 * @see net.lbtech.redis.core.impl.RedisSetClient#sadd(java.lang.String, int, T)
	 */
	public <T> long sadd(final String key,final int seconds, final T... values){
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
				returnValue = connection.sAdd(byteKey, (byte[][])list.toArray());
				if (seconds != 0 ) {
					connection.expire(byteKey, seconds);
				}
				logger.debug("lpush key {} = {} ",key,values);
				return returnValue;
			}
			
		});
	}
	/* (non-Javadoc)
	 * @see net.lbtech.redis.core.impl.RedisSetClient#sadd(java.lang.String, T)
	 */
	public <T> long sadd(final String key, final T... values){
		return sadd(key, 0, values);
	}
	/* (non-Javadoc)
	 * @see net.lbtech.redis.core.impl.RedisSetClient#smembers(java.lang.String)
	 */
	public <T> Set<T> smembers(final String key){
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Set<T>>() {
			public Set<T> doInRedis(
					RedisConnection connection) throws DataAccessException {
				byte[] byteKey = getBytesKey(key);
				Set<byte[]> set = connection.sMembers(byteKey);
				Set<T> returnValue = new HashSet<T>();
				for (byte[] bytes :set) {
					returnValue.add((T)toObject(bytes));
				}
				return returnValue;
			}
		});
	}
}

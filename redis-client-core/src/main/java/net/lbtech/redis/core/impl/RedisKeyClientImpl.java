package net.lbtech.redis.core.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.lbtech.redis.core.RedisClient;
import net.lbtech.redis.core.RedisKeyClient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

/**
 * 完成redis key 相关操作 see http://doc.redisfans.com/key/index.html
 * @author DF
 *
 */
public class RedisKeyClientImpl extends RedisClient implements RedisKeyClient{

	public RedisKeyClientImpl(RedisTemplate<Serializable, Serializable> redisTemplate) {
		super(redisTemplate);
	}

	/**
	 * 检查给定 key 是否存在。
	 * @param key
	 * @return 若 key 存在，返回 true ，否则返回 false 。
	 */
	public Boolean exists(final String key){
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				return	connection.exists(getBytesKey(key));
			}
		});
	}
	/**
	 * 删除给定的一个或多个 key 。
	 * 不存在的 key 会被忽略。
	 * @param keys
	 * @return 被删除 key 的数量。
	 */
	public long del(final String... keys){
		Assert.notEmpty(keys,"keys is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				List<byte[]> list = new ArrayList<byte[]>();
				for (String key :keys) {
					list.add(getBytesKey(key));
				}
				long returnValue = connection.del((byte[][])list.toArray());
				logger.debug("del key  {} amount {} ", keys,returnValue);
				return returnValue;
			}
		});
	}
	/**
	 * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
	 * @param key
	 * @param seconds 单位秒
	 * @return 设置成功返回true  ，反之false
	 */
	public Boolean expire(final String key,final int seconds){
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				logger.debug("set key {} expire {} ",key,seconds);
				return connection.expire(getBytesKey(key), seconds);
			}
		});
	} 
	/**
	 * 移除给定 key 的生存时间
	 * @param key
	 * @return 当生存时间移除成功时true  如果 key 不存在或 key 没有设置生存时间，返回 false
	 */
	public Boolean persist(final String key){
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				logger.debug("persist key {} ",key);
				return connection.persist(getBytesKey(key));
			}
		});
	} 
	/**
	 * 以秒为单位，返回给定 key 的剩余生存时间
	 * @param key
	 * @return 当 key 不存在时，返回 -2 。
	 *		        当 key 存在但没有设置剩余生存时间时，返回 -1 。
	 *		      否则，以秒为单位，返回 key 的剩余生存时间。
	 */
	public Long ttl(final String key){
		Assert.isTrue(StringUtils.isNotEmpty(key), "key is not allow empty..");
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				long returnValue = 0;
				byte[] byteKey = getBytesKey(key);
				returnValue = connection.ttl(byteKey);
				logger.debug("ttl  key {} = {} ", key,returnValue);
				return returnValue;
			}
		});
	}
}

package net.lbtech.redis.core;

/**
 * redis key 操作接口 see http://doc.redisfans.com/key/index.html
 * @author DF
 *
 */
public interface RedisKeyClient {

	public Boolean exists(final String key);
	public long del(final String... keys);
	public Boolean expire(final String key,final int seconds);
	public Boolean persist(final String key);
	public Long ttl(final String key);
}

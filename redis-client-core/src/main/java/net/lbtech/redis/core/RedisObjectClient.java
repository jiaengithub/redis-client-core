package net.lbtech.redis.core;

/**
 * redis 对象操作  包含string
 * @author DF
 *
 */
public interface RedisObjectClient {
	public <T> void set(final String key,final T obj,Class<T> clazz,final int seconds);
	public <T> T get(final String key,Class<T> clazz);
	public <T> void set(final String key,final T obj,Class<T> clazz);
	public void setString(final String key,final String value ,final int seconds);
	public void setString(final String key,final String value);
	
}

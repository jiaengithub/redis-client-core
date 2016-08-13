package net.lbtech.redis.core;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import net.lbtech.redis.util.ObjectUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

/**
 * redis client  基类
 * @author DF
 *
 */
public abstract class RedisClient {
	/**
	 * 日志对象
	 */
	protected  Logger logger = LoggerFactory.getLogger(getClass());
    //构造方法注入
	protected RedisTemplate<Serializable, Serializable> redisTemplate;

	public RedisClient(RedisTemplate<Serializable, Serializable> redisTemplate) {
		super();
		Assert.notNull(redisTemplate,"redisTemplate is null");
		this.redisTemplate = redisTemplate;
	}
	/**
	 * 获取byte[]类型Key
	 * @param key
	 * @return
	 */
	protected static byte[] getBytesKey(Object object){
		if(object instanceof String){
    		try {
				return ((String) object).getBytes("CHARSET");
			} catch (UnsupportedEncodingException e) {
				return ((String) object).getBytes();
			}
    	}else{
    		return ObjectUtils.serialize(object);
    	}
	}
	/**
	 * Object转换byte[]类型
	 * @param key
	 * @return
	 */
	protected  static byte[] toBytes(Object object){
    	return ObjectUtils.serialize(object);
	}

	/**
	 * byte[]型转换Object
	 * @param key
	 * @return
	 */
	protected static Object toObject(byte[] bytes){
		return ObjectUtils.unserialize(bytes);
	}
}
